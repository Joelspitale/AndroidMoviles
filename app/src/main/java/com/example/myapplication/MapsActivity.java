package com.example.myapplication;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.myapplication.modelo.ItemMuseo;
import com.example.myapplication.network.RetrofitClientInstance;
import com.example.myapplication.network.ServiceExhibits;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.myapplication.databinding.ActivityMapsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        button = findViewById(R.id.buttonReturnMap);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ServiceExhibits serviceExhibits = RetrofitClientInstance.getRetrofit().create(ServiceExhibits.class);
        System.out.println("por buscar todos los itemsMUseo");

        Call<List<ItemMuseo>> call = serviceExhibits.getAllItemsMuseoComplete(); //hago la llamada
        call.enqueue(new Callback<List<ItemMuseo>>() {
            @Override
            public void onResponse(Call<List<ItemMuseo>> call, Response<List<ItemMuseo>> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Codigo: " + response.code());
                }
                System.out.println("Se obtuvo el json correctamente");
                List<ItemMuseo> list = response.body();
                LatLng latLng = new LatLng(0,0);
                for(int i = 0; i < list.size(); i++){
                    latLng = new LatLng(list.get(i).getItemLat(), list.get(i).getItemLong());
                    mMap.addMarker(new MarkerOptions().position(latLng).title(list.get(i).getItemTitle()));
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }

            @Override
            public void onFailure(Call<List<ItemMuseo>> call, Throwable t) {
                System.out.println("Hubo un error inesperado" + t.getMessage());
            }
        });
    }
}
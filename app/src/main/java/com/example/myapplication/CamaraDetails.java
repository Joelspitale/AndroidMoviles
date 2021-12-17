package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.ItemGallery;
import com.example.myapplication.modelo.ItemMuseo;
import com.example.myapplication.network.RetrofitClientInstance;
import com.example.myapplication.network.ServiceExhibits;
import com.example.myapplication.utils.Tools;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CamaraDetails extends AppCompatActivity {

    private TextView txtTitleDetaills;
    private TextView txtIntroductionDetails;
    private TextView txtContentDetails;
    private ImageView imageDetails;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private CheckBox checkBox;
    private ItemMuseo itemMuseo;
    private Button buttonYoutube;
    private List<ItemGallery> listURLsImage;
    private CarouselView carousel;
    private Button buttonMaps;
    private Tools tools;
    private ExhibitsRepository exhibitsRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_details);

        txtTitleDetaills = findViewById(R.id.titleAtractionDetailsCamara);
        txtIntroductionDetails = findViewById(R.id.introductionAtractionDetailsCamara);
        txtContentDetails = findViewById(R.id.contentAtractionDetailsCamara);
        imageDetails = findViewById(R.id.imageAtractionDetailsCamara);
        progressBar = findViewById(R.id.progress_bar_detailsCamara);
        scrollView = findViewById(R.id.ScrollDetailsCamara);
        checkBox = findViewById(R.id.likeCamara);
        buttonYoutube = findViewById(R.id.botonYoutubeCamara);
        carousel = findViewById(R.id.carouselCamara);
        buttonMaps = findViewById(R.id.botonMapsCamara);
        tools = new Tools();
        exhibitsRepository = tools.getRepositoryItemUseo(this);

        Button bottomVolButton = findViewById(R.id.buttonReturnAtractionDetailsCamara);
        bottomVolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CamaraDetails.this, CameraActivity.class);
                startActivity(myIntent);
            }
        });

        tools.nextActivityNotConnection(this);
        long id = (long) getIntent().getExtras().getSerializable("codigoQR");
        ServiceExhibits serviceExhibits = RetrofitClientInstance.getRetrofit().create(ServiceExhibits.class);
        Call<ItemMuseo> call = serviceExhibits.getOneItemMuseoById(id);
        call.enqueue(new Callback<ItemMuseo>() {
            @Override
            public void onResponse(Call<ItemMuseo> call, Response<ItemMuseo> response) {
                if (!response.isSuccessful()) {
                    System.out.println("Codigo: " + response.code());
                }
                itemMuseo = response.body();
                loadExhibit(itemMuseo);
                bottomVolButton.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<ItemMuseo> call, Throwable t) {
                System.out.println("Hubo un error inesperado" + t.getMessage());
            }
        });

        //obtengo el icono del favorito
        CheckBox bottomFavorites = findViewById(R.id.likeCamara);
        bottomFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    addOrDeleteFavoritesExhibits(itemMuseo);
                } catch (NegocioException e) {
                    e.printStackTrace();
                } catch (NoEncontradoException e) {
                    e.printStackTrace();
                } catch (EncontradoException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(itemMuseo.getItemYoutube())));
            }
        });

        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=" + itemMuseo.getItemLat() + "," + itemMuseo.getItemLong() + "&z=14")));
            }
        });
    }

    private void addOrDeleteFavoritesExhibits(ItemMuseo itemMuseo) throws NegocioException, NoEncontradoException, EncontradoException {
        if (checkBox.isChecked()) {
            Log.i(this.getClass().getName(),"la exhibicion esta activada");
            exhibitsRepository.insert(itemMuseo);
            checkBox.setChecked(true);
        } else {
            Log.i(this.getClass().getName(),"la exhibicion esta desactivada");
            exhibitsRepository.delete(itemMuseo);
            checkBox.setChecked(false);
        }
    }

    private void exhibitsExistInBd(ItemMuseo itemMuseo) {
        try {
            exhibitsRepository.load(itemMuseo.getId());
            checkBox.setChecked(true);
        } catch (Exception e) {
            checkBox.setChecked(false);
        }
    }

    private void loadExhibit(ItemMuseo itemMuseo) {
        txtTitleDetaills.setText(itemMuseo.getItemTitle());
        txtIntroductionDetails.setText(itemMuseo.getItemIntro());
        txtContentDetails.setText(itemMuseo.getItemMainContent());
        cargarCarousel(itemMuseo);
        Picasso.Builder builder = new Picasso.Builder(getBaseContext());
        builder.downloader(new OkHttp3Downloader(getBaseContext()));
        builder.build().load(itemMuseo.getItemMainPicture())
                .into(imageDetails);
        exhibitsExistInBd(itemMuseo);

    }

    private void cargarCarousel(ItemMuseo itemMuseo) {
        listURLsImage = itemMuseo.getItemGallery();
        carousel.setImageListener(imageListener);
        carousel.setPageCount(listURLsImage.size());
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Picasso.Builder builder = new Picasso.Builder(getBaseContext());
            builder.downloader(new OkHttp3Downloader(getBaseContext()));
            builder.build().load(listURLsImage.get(position).getUrl()).into(imageView);
        }
    };
}
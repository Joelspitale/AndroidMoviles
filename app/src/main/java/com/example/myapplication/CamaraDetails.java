package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.ExhibitsBusiness;
import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.ItemMuseo;
import com.example.myapplication.network.RetrofitClientInstance;
import com.example.myapplication.network.ServiceExhibits;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_details);

        txtTitleDetaills= findViewById(R.id.titleAtractionDetailsCamara);
        txtIntroductionDetails = findViewById(R.id.introductionAtractionDetailsCamara);
        txtContentDetails = findViewById(R.id.contentAtractionDetailsCamara);
        imageDetails = findViewById(R.id.imageAtractionDetailsCamara);
        progressBar = findViewById(R.id.progress_bar_detailsCamara);
        scrollView = findViewById(R.id.ScrollDetailsCamara);
        checkBox = findViewById(R.id.likeCamara);

        Button bottomVolButton = findViewById(R.id.buttonReturnAtractionDetailsCamara);
        bottomVolButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CamaraDetails.this, CameraActivity.class);
                startActivity(myIntent);
            }
        });


        long id =  (long) getIntent().getExtras().getSerializable("codigoQR");
        ServiceExhibits serviceExhibits = RetrofitClientInstance.getRetrofit().create(ServiceExhibits.class);
        Call<ItemMuseo> call = serviceExhibits.getOneItemMuseoById(id);
        call.enqueue(new Callback<ItemMuseo>() {
                @Override
                public void onResponse(Call<ItemMuseo> call, Response<ItemMuseo> response) {
                    if(!response.isSuccessful()){
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
    }

    private void addOrDeleteFavoritesExhibits(ItemMuseo itemMuseo) throws NegocioException, NoEncontradoException, EncontradoException {
        AppDatabase db = AppDatabase.getInstance(getBaseContext());
        ExhibitsDAO exhibitsDAO = db.exhibitsDAO();
        ExhibitsRepository exhibitsRepository = new ExhibitsBusiness(exhibitsDAO);
        if(checkBox.isChecked()){
            System.out.println("la exhibicion esta activada");
            exhibitsRepository.insert(itemMuseo);
            checkBox.setChecked(true);
        }else{
            System.out.println("la exhibicion esta desactivada");
            exhibitsRepository.delete(itemMuseo);
            checkBox.setChecked(false);
        }
    }

    private void exhibitsExistInBd(ItemMuseo itemMuseo){
        AppDatabase db = AppDatabase.getInstance(getBaseContext());
        ExhibitsDAO exhibitsDAO = db.exhibitsDAO();
        ExhibitsRepository exhibitsRepository = new ExhibitsBusiness(exhibitsDAO);
        try {
            exhibitsRepository.load(itemMuseo.getId());
            checkBox.setChecked(true);
        }catch (Exception e){
            checkBox.setChecked(false);
        }
    }
        private void loadExhibit(ItemMuseo itemMuseo){

        txtTitleDetaills.setText(itemMuseo.getItemTitle());
        txtIntroductionDetails.setText(itemMuseo.getItemIntro());
        txtContentDetails.setText(itemMuseo.getItemMainContent());

        Picasso.Builder builder = new Picasso.Builder(getBaseContext());
        builder.downloader(new OkHttp3Downloader(getBaseContext()));
        builder.build().load(itemMuseo.getItemMainPicture()) //busco la imagen de la url del json
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)   //en caso que no pueda obtener la foto muestra este icono
                .into(imageDetails);
        //si la obtiene la meto en el layaout de la imagen de la card
        //pinto el corazon o no en funcion si existe en la bd
        exhibitsExistInBd(itemMuseo);

    }
}
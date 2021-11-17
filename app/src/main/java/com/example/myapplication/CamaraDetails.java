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
import com.example.myapplication.modelo.Exhibits;
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


        Exhibits exhibit = (Exhibits) getIntent().getExtras().getSerializable("codigoQR");
        ServiceExhibits serviceExhibits = RetrofitClientInstance.getRetrofit().create(ServiceExhibits.class);
        Call<Exhibits> call = elegirEndPoint(serviceExhibits,exhibit.getId());
        call.enqueue(new Callback<Exhibits>() {
                @Override
                public void onResponse(Call<Exhibits> call, Response<Exhibits> response) {
                    if(!response.isSuccessful()){
                        System.out.println("Codigo: " + response.code());
                    }
                    //cargo los items en mi lista
                    loadExhibit(response.body());
                    bottomVolButton.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    System.out.println("Se obtuvo el json correctamente");
                }

                @Override
                public void onFailure(Call<Exhibits> call, Throwable t) {
                    System.out.println("Hubo un error inesperado" + t.getMessage());
                }
            });

        //obtengo el icono del favorito
        CheckBox bottomFavorites = findViewById(R.id.likeCamara);
        bottomFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                System.out.println("Agregar a favoritos la exibicion con id:" + exhibit.getId()+
                        "titulo" + exhibit.getTitle());
                try {
                    addOrDeleteFavoritesExhibits(exhibit);
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

    private void addOrDeleteFavoritesExhibits(Exhibits exhibits) throws NegocioException, NoEncontradoException, EncontradoException {
        AppDatabase db = AppDatabase.getInstance(getBaseContext());
        ExhibitsDAO exhibitsDAO = db.exhibitsDAO();
        ExhibitsRepository exhibitsRepository = new ExhibitsBusiness(exhibitsDAO);
        if(checkBox.isChecked()){
            System.out.println("la exhibicion esta activada");
            exhibitsRepository.insert(exhibits);
            checkBox.setChecked(false);
        }else{
            System.out.println("la exhibicion esta desactivada");
            exhibitsRepository.delete(exhibits);
            checkBox.setChecked(true);
        }
    }

    private void exhibitsExistInBd(Exhibits exhibits){
        AppDatabase db = AppDatabase.getInstance(getBaseContext());
        ExhibitsDAO exhibitsDAO = db.exhibitsDAO();
        ExhibitsRepository exhibitsRepository = new ExhibitsBusiness(exhibitsDAO);
        try {
            exhibitsRepository.load(exhibits.getId());
            checkBox.setChecked(true);
        }catch (Exception e){
            checkBox.setChecked(false);
        }
    }

    private Call<Exhibits> elegirEndPoint(ServiceExhibits serviceExhibits, long id) {
        switch ((int) id){
            case 1:
                return serviceExhibits.getOneFirstExhibit();
            case 2:
                return serviceExhibits.getOneSecondExhibit();
            case 3:
                return serviceExhibits.getOneThirdExhibit();
            case 4:
                return serviceExhibits.getOneFourthExhibit();
            case 5:
                return serviceExhibits.getOneFifthExhibit();
            case 6:
                return serviceExhibits.getOneSixthExhibit();
            case 7:
                return serviceExhibits.getOneSeventhExhibit();
            case 8:
                return serviceExhibits.getOneEighthExhibit();
            case 9:
                return serviceExhibits.getOneNinthExhibit();
            default:
                return serviceExhibits.getOneTenthExhibit();
        }

    }



    private void loadExhibit(Exhibits exhibits){

        txtTitleDetaills.setText(exhibits.getTitle());
        txtIntroductionDetails.setText(exhibits.getIntroduction());
        txtContentDetails.setText(exhibits.getContent());

        Picasso.Builder builder = new Picasso.Builder(getBaseContext());
        builder.downloader(new OkHttp3Downloader(getBaseContext()));
        builder.build().load(exhibits.getImagenId()) //busco la imagen de la url del json
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)   //en caso que no pueda obtener la foto muestra este icono
                .into(imageDetails);
        //si la obtiene la meto en el layaout de la imagen de la card

        //pinto el corazon o no en funcion si existe en la bd
        exhibitsExistInBd(exhibits);

    }
}
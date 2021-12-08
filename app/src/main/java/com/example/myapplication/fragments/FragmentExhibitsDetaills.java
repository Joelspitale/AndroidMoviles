package com.example.myapplication.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.myapplication.R;
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

public class FragmentExhibitsDetaills extends Fragment{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private TextView txtTitleDetaills;
    private TextView txtIntroductionDetails;
    private TextView txtContentDetails;
    private ImageView imageDetails;
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private CheckBox checkBox;

    public static FragmentExhibitsDetaills newInstance(String param1, String param2) {
        FragmentExhibitsDetaills fragment = new FragmentExhibitsDetaills();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exhibits_detaills, container, false);

        //hago el direccionamiento de los layaout con variables del entorno
        txtTitleDetaills= view.findViewById(R.id.titleAtractionDetaills);
        txtIntroductionDetails = view.findViewById(R.id.introductionAtractionDetaills);
        txtContentDetails = view.findViewById(R.id.contentAtractionDetaills);
        imageDetails = view.findViewById(R.id.imageAtractionDetaills);
        progressBar = view.findViewById(R.id.progress_bar_details);
        scrollView = view.findViewById(R.id.ScrollDetails);
        checkBox = view.findViewById(R.id.like);


        Button bottomVolverExhibits = view.findViewById(R.id.buttonReturnAtractionDetaills);
        bottomVolverExhibits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        //recibo y extraigo el objeto en el que hizo click en la lista
        Bundle objectExhibit = getArguments();
        Exhibits exhibit = null;

        //verifico que el objeto que me enviaron no esta vacio
        if (objectExhibit != null) {
            exhibit = (Exhibits) objectExhibit.getSerializable("objeto");
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
                    bottomVolverExhibits.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    System.out.println("Se obtuvo el json correctamente");
                }

                @Override
                public void onFailure(Call<Exhibits> call, Throwable t) {
                    System.out.println("Hubo un error inesperado" + t.getMessage());
                }
            });
        }

        //obtengo el icono del favorito
        CheckBox bottomFavorites = view.findViewById(R.id.like);
        Exhibits finalExhibit = exhibit;
        bottomFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                System.out.println("Agregar a favoritos la exibicion con id:" + finalExhibit.getId()+
                        "titulo" + finalExhibit.getTitle());
                try {
                    addOrDeleteFavoritesExhibits(finalExhibit);
                    //Recarga fragment
                } catch (NegocioException e) {
                    e.printStackTrace();
                } catch (NoEncontradoException e) {
                    e.printStackTrace();
                } catch (EncontradoException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    //si existe en la bd entonces activo el check box
    private void addOrDeleteFavoritesExhibits(Exhibits exhibits) throws NegocioException, NoEncontradoException, EncontradoException {
        AppDatabase db = AppDatabase.getInstance(getActivity().getBaseContext());
        ExhibitsDAO exhibitsDAO = db.exhibitsDAO();
        ExhibitsRepository exhibitsRepository = new ExhibitsBusiness(exhibitsDAO);
        if(checkBox.isChecked()){
            System.out.println("la exhibicion esta activada");
            exhibitsRepository.insert(exhibits);
            checkBox.setChecked(true);
        }else{
            System.out.println("la exhibicion esta desactivada");
            exhibitsRepository.delete(exhibits);
            checkBox.setChecked(false);
        }
    }

    private void exhibitsExistInBd(Exhibits exhibits){
        AppDatabase db = AppDatabase.getInstance(getActivity().getBaseContext());
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
        Picasso.Builder builder = new Picasso.Builder(getActivity().getBaseContext());
        builder.downloader(new OkHttp3Downloader(getActivity().getBaseContext()));
        builder.build().load(exhibits.getImagenId()) //busco la imagen de la url del json
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)   //en caso que no pueda obtener la foto muestra este icono
                .into(imageDetails);
                 //si la obtiene la meto en el layaout de la imagen de la card

        //pinto el corazon o no en funcion si existe en la bd
        exhibitsExistInBd(exhibits);

    }


}

package com.example.myapplication.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
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
import android.widget.Toast;
import android.widget.VideoView;

import com.example.myapplication.R;
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
    private ItemMuseo exhibit;

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

        View view = inflater.inflate(R.layout.fragment_exhibits_detaills, container, false);

        //hago el direccionamiento de los layaout con variables del entorno
        txtTitleDetaills= view.findViewById(R.id.titleAtractionDetaills);
        txtIntroductionDetails = view.findViewById(R.id.introductionAtractionDetaills);
        txtContentDetails = view.findViewById(R.id.contentAtractionDetaills);
        imageDetails = view.findViewById(R.id.imageAtractionDetaills);
        progressBar = view.findViewById(R.id.progress_bar_item_Museo);
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
        //verifico que el objeto que me enviaron no esta vacio
        if (objectExhibit != null) {

            ItemMuseo exhibitMemoria = (ItemMuseo) objectExhibit.getSerializable("objeto");
            ServiceExhibits serviceExhibits = RetrofitClientInstance.getRetrofit().create(ServiceExhibits.class);
            Call<ItemMuseo> call = serviceExhibits.getOneItemMuseoById(exhibitMemoria.getId());
            call.enqueue(new Callback<ItemMuseo>() {
                @Override
                public void onResponse(Call<ItemMuseo> call, Response<ItemMuseo> response) {
                    if(!response.isSuccessful()){
                        System.out.println("Codigo: " + response.code());
                    }
                    System.out.println("Se obtuvo el json correctamente");
                    exhibit = response.body();
                    loadExhibit(exhibit);
                    for(int i= 0 ; i<100000000;i++);    //para darle mas tiempo a la ap
                    bottomVolverExhibits.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<ItemMuseo> call, Throwable t) {
                    System.out.println("Hubo un error inesperado" + t.getMessage());
                    bottomVolverExhibits.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            });

            //MiTareaAsincrona tarea2 = new MiTareaAsincrona(view,scrollView,progressBar,serviceExhibits,exhibit,bottomVolverExhibits);
            //tarea2.execute();
        }

        //obtengo el icono del favorito
        CheckBox bottomFavorites = view.findViewById(R.id.like);
        bottomFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                System.out.println("Agregar a favoritos la exibicion con id:" + exhibit.getId()+
                        "titulo" + exhibit.getItemTitle());
                try {
                    addOrDeleteFavoritesExhibits(exhibit);
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
    private void addOrDeleteFavoritesExhibits(ItemMuseo itemMuseo) throws NegocioException, NoEncontradoException, EncontradoException {
        AppDatabase db = AppDatabase.getInstance(getActivity().getBaseContext());
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
        AppDatabase db = AppDatabase.getInstance(getActivity().getBaseContext());
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
        Picasso.Builder builder = new Picasso.Builder(getActivity().getBaseContext());
        builder.downloader(new OkHttp3Downloader(getActivity().getBaseContext()));
        builder.build().load(itemMuseo.getItemMainPicture()) //busco la imagen de la url del json
                //.placeholder(R.drawable.ic_launcher_background)
                //.error(R.drawable.ic_launcher_background)   //en caso que no pueda obtener la foto muestra este icono
                .into(imageDetails);
                 //si la obtiene la meto en el layaout de la imagen de la card
        //pinto el corazon o no en funcion si existe en la bd
        exhibitsExistInBd(itemMuseo);

    }


    private class MiTareaAsincrona extends AsyncTask<Void, Void, Boolean> {
        private View view;
        private ScrollView scrollView;
        private ProgressBar progressBar;
        private ServiceExhibits serviceExhibits;
        private ItemMuseo itemMuseo;
        private Button bottomVolverExhibits;


        public MiTareaAsincrona(View view, ScrollView scrollView, ProgressBar progressBar, ServiceExhibits serviceExhibits, ItemMuseo itemMuseo,Button bottomVolverExhibits) {
            this.view = view;
            this.scrollView = scrollView;
            this.progressBar = progressBar;
            this.serviceExhibits = serviceExhibits;
            this.itemMuseo = itemMuseo;
            this.bottomVolverExhibits = bottomVolverExhibits;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Call<ItemMuseo> call = serviceExhibits.getOneItemMuseoById(itemMuseo.getId());
            call.enqueue(new Callback<ItemMuseo>() {
                @Override
                public void onResponse(Call<ItemMuseo> call, Response<ItemMuseo> response) {
                    if(!response.isSuccessful()){
                        System.out.println("Codigo: " + response.code());
                    }
                    System.out.println("Se obtuvo el json correctamente");
                    loadExhibit(response.body());
                    for(int i= 0 ; i<100000000;i++);    //para darle mas tiempo a la ap
                }

                @Override
                public void onFailure(Call<ItemMuseo> call, Throwable t) {
                    System.out.println("Hubo un error inesperado" + t.getMessage());
                }
            });
            return true;
        }


        @Override
        protected void onPostExecute(Boolean result) {
            System.out.println("Se ingreso a onPostExecute");
            bottomVolverExhibits.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getActivity().getBaseContext(), "Tarea cancelada!",
                    Toast.LENGTH_SHORT).show();
        }
    }

}

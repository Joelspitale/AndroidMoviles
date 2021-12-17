package com.example.myapplication.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
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

import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.business.ExhibitsBusiness;
import com.example.myapplication.database.dao.ExhibitsDAO;
import com.example.myapplication.database.repository.ExhibitsRepository;
import com.example.myapplication.excepciones.EncontradoException;
import com.example.myapplication.excepciones.NegocioException;
import com.example.myapplication.excepciones.NoEncontradoException;
import com.example.myapplication.modelo.ItemGallery;
import com.example.myapplication.modelo.ItemMuseo;
import com.example.myapplication.network.RetrofitClientInstance;
import com.example.myapplication.network.ServiceExhibits;
import com.example.myapplication.utils.Tools;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentExhibitsDetaills extends Fragment {

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
    private ItemMuseo itemMuseo;
    private Button buttonYoutube;
    private ChipGroup chipGroup;
    private CarouselView carouselView;
    private List<ItemGallery> listURLsImage;
    private Button buttonMaps;
    private Tools tools= new Tools();
    private ExhibitsRepository exhibitsRepository;

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


        txtTitleDetaills = view.findViewById(R.id.titleAtractionDetaills);
        txtIntroductionDetails = view.findViewById(R.id.introductionAtractionDetaills);
        txtContentDetails = view.findViewById(R.id.contentAtractionDetaills);
        imageDetails = view.findViewById(R.id.imageAtractionDetaills);
        progressBar = view.findViewById(R.id.progress_bar_item_Museo);
        scrollView = view.findViewById(R.id.ScrollDetails);
        checkBox = view.findViewById(R.id.like);
        buttonYoutube = view.findViewById(R.id.botonYoutube);
        chipGroup = view.findViewById(R.id.chipGroup);
        carouselView = view.findViewById(R.id.carouselView);
        buttonMaps = view.findViewById(R.id.botonMaps);
        tools.nextActivityNotConnection(getActivity());
        exhibitsRepository= tools.getRepositoryItemUseo(getActivity());


        Button bottomVolverExhibits = view.findViewById(R.id.buttonReturnAtractionDetaills);
        bottomVolverExhibits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Bundle objectExhibit = getArguments();
        if (objectExhibit != null) {
            ItemMuseo exhibitMemoria = (ItemMuseo) objectExhibit.getSerializable("objeto");
            ServiceExhibits serviceExhibits = RetrofitClientInstance.getRetrofit().create(ServiceExhibits.class);
            Call<ItemMuseo> call = serviceExhibits.getOneItemMuseoById(exhibitMemoria.getId());

            call.enqueue(new Callback<ItemMuseo>() {
                @Override
                public void onResponse(Call<ItemMuseo> call, Response<ItemMuseo> response) {
                    if (!response.isSuccessful()) {
                        System.out.println("Codigo: " + response.code());
                    }
                    System.out.println("Se obtuvo el json correctamente");
                    itemMuseo = response.body();
                    loadExhibit(itemMuseo);
                    for (int i = 0; i < 150000000; i++) ;    //para darle mas tiempo a la ap
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

            //MiTareaAsincrona tarea2 = new MiTareaAsincrona(view,scrollView,progressBar,serviceExhibits,exhibitMemoria,bottomVolverExhibits);
            //tarea2.execute();
        }

        CheckBox bottomFavorites = view.findViewById(R.id.like);
        bottomFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.i(this.getClass().getName(),"Agregar a favoritos la exibicion con id:" + itemMuseo.getId() +
                        "titulo" + itemMuseo.getItemTitle());
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
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com/?q=" + itemMuseo.getItemLat() + ","+ itemMuseo.getItemLong() +"&z=14")));
            }
        });
        return view;
    }

    //si existe en la bd entonces activo el check box
    private void addOrDeleteFavoritesExhibits(ItemMuseo itemMuseo) throws NegocioException, NoEncontradoException, EncontradoException {
        if (checkBox.isChecked()) {
            System.out.println("la exhibicion esta activada");
            exhibitsRepository.insert(itemMuseo);
            checkBox.setChecked(true);
        } else {
            System.out.println("la exhibicion esta desactivada");
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
        addChip(itemMuseo.getItemTags().split(","));
        cargarCarousel(itemMuseo);
        Picasso.Builder builder = new Picasso.Builder(getActivity().getBaseContext());
        builder.downloader(new OkHttp3Downloader(getActivity().getBaseContext()));
        builder.build().load(itemMuseo.getItemMainPicture())
                .into(imageDetails);
        exhibitsExistInBd(itemMuseo);
    }

    private void cargarCarousel(ItemMuseo itemMuseo) {
        listURLsImage = itemMuseo.getItemGallery();
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(listURLsImage.size());
    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Picasso.Builder builder = new Picasso.Builder(getActivity().getBaseContext());
            builder.downloader(new OkHttp3Downloader(getActivity().getBaseContext()));
            builder.build().load(listURLsImage.get(position).getUrl()).into(imageView);
        }
    };

    private void addChip(String[] chips){
        Chip chip;
        for (int i = 0; i < chips.length; i ++){
            chip = new Chip(getContext());
            chip.setText(chips[i]);
            chipGroup.addView(chip);
        }
    }


    private class MiTareaAsincrona extends AsyncTask<Void, Void, Boolean> {
        private View view;
        private ScrollView scrollView;
        private ProgressBar progressBar;
        private ServiceExhibits serviceExhibits;
        private ItemMuseo itemMuseo;
        private Button bottomVolverExhibits;


        public MiTareaAsincrona(View view, ScrollView scrollView, ProgressBar progressBar, ServiceExhibits serviceExhibits, ItemMuseo itemMuseo, Button bottomVolverExhibits) {
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
                    if (!response.isSuccessful()) {
                        System.out.println("Codigo: " + response.code());
                    }
                    System.out.println("Se obtuvo el json correctamente");
                    loadExhibit(response.body());
                    for (int i = 0; i < 100000000; i++) ;    //para darle mas tiempo a la ap
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

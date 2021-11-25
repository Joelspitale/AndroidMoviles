package com.example.myapplication.recyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.modelo.Exhibits;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ExhibitsListViewHolder> implements View.OnClickListener{

    private List<Exhibits> exhibitsList;
    private View.OnClickListener listener;
    private Context context;


    public ListAdapter(Context context,List<Exhibits> exhibitsList) {
        this.exhibitsList = exhibitsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ExhibitsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_exhibits, parent, false);
        row.setOnClickListener(this);
        return new ExhibitsListViewHolder(row);
    }

    //mapeo cada elemento de la lista en el recycler view
    @Override
    public void onBindViewHolder(@NonNull ExhibitsListViewHolder holder, int position) {
        Exhibits exhibit = exhibitsList.get(position);
        ((ExhibitsListViewHolder) holder).getTxtTitle().setText(exhibit.getTitle());
        ((ExhibitsListViewHolder) holder).getTxtIntroducction().setText(exhibit.getIntroduction());
//        ((ExhibitsListViewHolder) holder).getTxtContent().setText(exhibit.getContent());
//
        //extraigo la imagen de la url y la cargo en el layaout o en su defecto traigo una icono por defecto
        //((ExhibitsListViewHolder) holder).getFoto().setImageResource(exhibit.getImagenId());
//        Picasso.Builder builder = new Picasso.Builder(context);
//        builder.downloader(new OkHttp3Downloader(context));
//        builder.build().load(exhibit.getImagenId()) //busco la imagen de la url del json
//                .placeholder(R.drawable.ic_launcher_background)
//                .error(R.drawable.ic_launcher_background)   //en caso que no pueda obtener la foto muestra este icono
//                .into(((ExhibitsListViewHolder) holder).getFoto()); //si la obtiene la meto en el layaout de la imagen de la card
    }

    @Override
    public int getItemCount() {
        return exhibitsList.size();
    }

    public void setOnClickListener( View.OnClickListener listener){
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener!= null){
            listener.onClick(view);
        }
    }

    public  class ExhibitsListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtIntroducction;
        private TextView txtContent;
//        private ImageView foto;

        //direcciono layaout con variables de java
        public ExhibitsListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.idExhibitsTitle);
            txtIntroducction = (TextView) itemView.findViewById(R.id.idExhibitsIntroduction);
//            txtContent = (TextView) itemView.findViewById(R.id.idExhibitsContent);
//            foto = (ImageView) itemView.findViewById(R.id.idImagen);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public void setTxtTitle(TextView txtTitle) {
            this.txtTitle = txtTitle;
        }

        public TextView getTxtIntroducction() {
            return txtIntroducction;
        }

        public void setTxtIntroducction(TextView txtIntroducction) {
            this.txtIntroducction = txtIntroducction;
        }

//        public TextView getTxtContent() {
//            return txtContent;
//        }
//
//        public void setTxtContent(TextView txtContent) {
//            this.txtContent = txtContent;
//        }
//
//        public ImageView getFoto() {
//            return foto;
//        }
//
//        public void setFoto(ImageView foto) {
//            this.foto = foto;
//        }
    }
}

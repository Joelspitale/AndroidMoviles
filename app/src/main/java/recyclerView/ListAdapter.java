package recyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import java.util.List;

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ExhibitsListViewHolder> implements View.OnClickListener{

    private List<Exhibits> exhibitsList;
    private View.OnClickListener listener;

    public ListAdapter(List<Exhibits> exhibitsList) {
        this.exhibitsList = exhibitsList;
    }


    @NonNull
    @Override
    public ExhibitsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_exhibits, parent, false);
        row.setOnClickListener(this);
        return new ExhibitsListViewHolder(row);
    }

    //seteo fila por fila
    @Override
    public void onBindViewHolder(@NonNull ExhibitsListViewHolder holder, int position) {
        Exhibits exhibits = exhibitsList.get(position);
        ((ExhibitsListViewHolder) holder).getTxtTitle().setText(exhibits.getTitle());
        ((ExhibitsListViewHolder) holder).getTxtIntroducction().setText(exhibits.getIntroduction());
        ((ExhibitsListViewHolder) holder).getTxtContent().setText(exhibits.getContent());
        ((ExhibitsListViewHolder) holder).getFoto().setImageResource(exhibits.getImagenId());

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
        private ImageView foto;

        public ExhibitsListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.idExhibitsTitle);
            txtIntroducction = (TextView) itemView.findViewById(R.id.idExhibitsIntroduction);
            txtContent = (TextView) itemView.findViewById(R.id.idExhibitsContent);
            foto = (ImageView) itemView.findViewById(R.id.idImagen);
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

        public TextView getTxtContent() {
            return txtContent;
        }

        public void setTxtContent(TextView txtContent) {
            this.txtContent = txtContent;
        }

        public ImageView getFoto() {
            return foto;
        }

        public void setFoto(ImageView foto) {
            this.foto = foto;
        }
    }
}

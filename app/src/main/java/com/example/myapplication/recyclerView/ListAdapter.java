package com.example.myapplication.recyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.modelo.ItemMuseo;
import java.util.List;

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ItemMuseoListViewHolder> implements View.OnClickListener{

    private List<ItemMuseo> itemMuseoList;
    private View.OnClickListener listener;
    private Context context;

    public ListAdapter(Context context,List<ItemMuseo> itemMuseoList) {
        this.itemMuseoList = itemMuseoList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemMuseoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_exhibits, parent, false);
        row.setOnClickListener(this);
        return new ItemMuseoListViewHolder(row);
    }

    //mapeo cada elemento de la lista en el recycler view
    @Override
    public void onBindViewHolder(@NonNull ItemMuseoListViewHolder holder, int position) {
        ItemMuseo itemMuseo = itemMuseoList.get(position);
        ((ItemMuseoListViewHolder) holder).getTxtTitle().setText(itemMuseo.getItemTitle());
        ((ItemMuseoListViewHolder) holder).getTxtRoomName().setText(itemMuseo.getRoomName());
    }

    @Override
    public int getItemCount() {
        return itemMuseoList.size();
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

    public  class ItemMuseoListViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtRoomName;

        public ItemMuseoListViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.idExhibitsTitle);
            txtRoomName = (TextView) itemView.findViewById(R.id.idExhibitsIntroduction);
        }

        public TextView getTxtTitle() {
            return txtTitle;
        }

        public void setTxtTitle(TextView txtTitle) {
            this.txtTitle = txtTitle;
        }

        public TextView getTxtRoomName() {
            return txtRoomName;
        }

        public void setTxtRoomName(TextView txtRoomName) {
            this.txtRoomName = txtRoomName;
        }

    }
}

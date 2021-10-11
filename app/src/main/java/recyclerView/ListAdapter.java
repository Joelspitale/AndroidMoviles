package recyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ListAdapter extends  RecyclerView.Adapter<ListAdapter.ExhibitsListViewHolder>{

    private List<Exhibits> data;

    public ListAdapter(List<Exhibits> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ExhibitsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_exhibits, parent, false);
        return new ExhibitsListViewHolder(row);
    }

    //seteo fila por fila
    @Override
    public void onBindViewHolder(@NonNull ExhibitsListViewHolder holder, int position) {
        Exhibits exhibits = data.get(position);
        ((ExhibitsListViewHolder) holder).getTitleTextView().setText(exhibits.getTitle());
        ((ExhibitsListViewHolder) holder).getIntroducctionTextView().setText(exhibits.getIntroduction());
        ((ExhibitsListViewHolder) holder).getIntroducctionTextView().setText(exhibits.getContent());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class ExhibitsListViewHolder extends RecyclerView.ViewHolder {
        private View circleView;
        private TextView titleTextView;
        private TextView introducctionTextView;
        private TextView contentTextView;

        public ExhibitsListViewHolder(@NonNull View itemView) {
            super(itemView);
            circleView = itemView.findViewById(R.id.profile_circle_view);
            titleTextView = (TextView) itemView.findViewById(R.id.text_view_exhibits_title);
            introducctionTextView = (TextView) itemView.findViewById(R.id.text_view_exhibits_introduction);
            contentTextView = (TextView) itemView.findViewById(R.id.text_view_exhibits_content);
        }

        public View getCircleView() {
            return circleView;
        }

        public void setCircleView(View circleView) {
            this.circleView = circleView;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public void setTitleTextView(TextView titleTextView) {
            this.titleTextView = titleTextView;
        }

        public TextView getIntroducctionTextView() {
            return introducctionTextView;
        }

        public void setIntroducctionTextView(TextView introducctionTextView) {
            this.introducctionTextView = introducctionTextView;
        }

        public TextView getContentTextView() {
            return contentTextView;
        }

        public void setContentTextView(TextView contentTextView) {
            this.contentTextView = contentTextView;
        }
    }
}

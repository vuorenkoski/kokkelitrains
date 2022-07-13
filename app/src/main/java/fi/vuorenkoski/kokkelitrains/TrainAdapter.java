package fi.vuorenkoski.kokkelitrains;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.ViewHolder> {
    private ArrayList<Train> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    TrainAdapter(Context context, ArrayList<Train> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.lineIdView.setText(""+mData.get(position).getLineId());
        holder.trackView.setText(""+mData.get(position).getTrack());
        holder.departureTimeView.setText(mData.get(position).getDepartureTimeStr());
        holder.notificationView.setText(mData.get(position).getNotification());
        holder.arrivalTimeView.setText(mData.get(position).getArrivalTimeStr());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lineIdView;
        TextView trackView;
        TextView departureTimeView;
        TextView notificationView;
        TextView arrivalTimeView;

        ViewHolder(View itemView) {
            super(itemView);
            lineIdView=itemView.findViewById(R.id.train_line_id);
            trackView=itemView.findViewById(R.id.train_track);
            departureTimeView=itemView.findViewById(R.id.train_departure_time);
            notificationView=itemView.findViewById(R.id.train_notification);
            arrivalTimeView=itemView.findViewById(R.id.train_arrival_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).toString();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

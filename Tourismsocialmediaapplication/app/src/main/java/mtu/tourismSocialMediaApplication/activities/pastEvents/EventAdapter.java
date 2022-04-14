package mtu.tourismSocialMediaApplication.activities.pastEvents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import mtu.tourismSocialMediaApplication.Objects.Event;
import mtu.tourismSocialMediaApplication.Objects.UserEvents;
import mtu.tourismSocialMediaApplication.R;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private UserEvents userEvents = UserEvents.getInstance();
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
//        if (!events.checkReverse()){
//            events.reverseOrder();
//        }
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.line_for_activity_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.timeView.setText(userEvents.getPastEvents().get(position).getStartTime().toString());
        holder.myTextView.setText(userEvents.getPastEvents().get(position).getTitle());
        holder.descriptionView.setText(userEvents.getPastEvents().get(position).getDescription());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return userEvents.getPastEvents().size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView timeView;
        TextView descriptionView;
        ViewHolder(View itemView) {
            super(itemView);
            descriptionView = itemView.findViewById(R.id.eventDescription);
            timeView = itemView.findViewById(R.id.eventTime);
            myTextView = itemView.findViewById(R.id.eventTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    Event getItem(int id) {
        return userEvents.getPastEvents().get(id);
    }

    // allows clicks events to be caught

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}


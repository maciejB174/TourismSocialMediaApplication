package mtu.tourismSocialMediaApplication.activities.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDateTime;

import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.database.Message;
import mtu.tourismSocialMediaApplication.database.MessageList;

class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private MessageList messages = MessageList.getInstance();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
//        if (!messages.checkReverse()){
//            messages.reverseOrder();
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
        holder.timeView.setText(LocalDateTime.parse(messages.getMessageArrayList().get(position).getDate()).toString());
        holder.myTextView.setText(messages.getMessageArrayList().get(position).getSender());
        holder.descriptionView.setText(messages.getMessageArrayList().get(position).getContent());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return messages.getMessageArrayList().size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        TextView timeView;
        TextView descriptionView;
        ViewHolder(View itemView) {
            super(itemView);
            descriptionView = itemView.findViewById(R.id.description);
            timeView = itemView.findViewById(R.id.time);
            myTextView = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    Message getItem(int id) {
        return messages.getMessageArrayList().get(id);
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


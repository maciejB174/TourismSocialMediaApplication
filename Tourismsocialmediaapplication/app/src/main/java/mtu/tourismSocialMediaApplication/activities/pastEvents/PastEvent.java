package mtu.tourismSocialMediaApplication.activities.pastEvents;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mtu.tourismSocialMediaApplication.Objects.Event;
import mtu.tourismSocialMediaApplication.Objects.UserEvents;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.activities.eventDisplay.EventDisplay;
//import mtu.tourismSocialMediaApplication.activities.rateEvent.EventRating;
import mtu.tourismSocialMediaApplication.database.EventDetails;

public class PastEvent extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
    UserEvents userEvents = UserEvents.getInstance();

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserEvents userEvents = UserEvents.getInstance();
        userEvents.setUpcomingAndPastEvents();
        setContentView(R.layout.activity_past_event);
        RecyclerView recyclerView = findViewById(R.id.item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        backButton = (Button) findViewById(R.id.backMessageLog);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        OpenEvent(position);
    }

    public void OpenEvent(int position){
        Event currentEvent = userEvents.getPastEvents().get(position);
        Intent intent = new Intent(this, EventDisplay.class);
        intent.putExtra("title", currentEvent.getTitle());
        startActivity(intent);
    }

}
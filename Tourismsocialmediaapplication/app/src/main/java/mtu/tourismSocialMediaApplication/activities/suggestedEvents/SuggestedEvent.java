package mtu.tourismSocialMediaApplication.activities.suggestedEvents;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import mtu.tourismSocialMediaApplication.Objects.Event;
import mtu.tourismSocialMediaApplication.Objects.UserEvents;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.activities.eventDisplay.EventDisplay;

public class SuggestedEvent extends AppCompatActivity implements mtu.tourismSocialMediaApplication.activities.suggestedEvents.MyRecyclerViewAdapter.ItemClickListener {
    mtu.tourismSocialMediaApplication.activities.suggestedEvents.MyRecyclerViewAdapter adapter;
    UserEvents userEvents = UserEvents.getInstance();

    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserEvents userEvents = UserEvents.getInstance();
        userEvents.setUpcomingAndPastEvents();
        setContentView(R.layout.suggested_event);
        RecyclerView recyclerView = findViewById(R.id.item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new mtu.tourismSocialMediaApplication.activities.suggestedEvents.MyRecyclerViewAdapter(this);
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
        Event currentEvent = userEvents.returnSuggestedEvents().get(position);
        Intent intent = new Intent(this, EventDisplay.class);
        intent.putExtra("title", currentEvent.getTitle());
        startActivity(intent);
    }

}
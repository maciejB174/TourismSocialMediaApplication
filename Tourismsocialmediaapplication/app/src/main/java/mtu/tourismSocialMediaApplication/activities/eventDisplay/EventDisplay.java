package mtu.tourismSocialMediaApplication.activities.eventDisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import mtu.tourismSocialMediaApplication.Objects.Event;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.database.EventDetails;
import mtu.tourismSocialMediaApplication.database.OnGetDataListener;

public class EventDisplay extends AppCompatActivity {

    private EventDetails eventDetails = EventDetails.getInstance();
    private EditText titleInfo;
    private EditText descriptionInfo;
    private EditText locationInfo;
    private EditText organiserInfo;
    private TextView admissionRateInfo;
    private TextView startTimeInfo;
    private TextView endTimeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_display);

        titleInfo = findViewById(R.id.titleInfoText);
        descriptionInfo = findViewById(R.id.DescriptionInfoText);
        locationInfo = findViewById(R.id.LocationText);
        organiserInfo = findViewById(R.id.OrganiserText);
        admissionRateInfo = findViewById(R.id.AdmissionRateText);
        startTimeInfo = findViewById(R.id.StartTimeText);
        endTimeInfo = findViewById(R.id.EndTimeText);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        eventDetails.readEvent(title, new OnGetDataListener() {
            @Override
            public void onSuccess() {
                Event currentEvent = eventDetails.getCurrentEvent();
                titleInfo.setText(currentEvent.getTitle());
                descriptionInfo.setText(currentEvent.getDescription());
                locationInfo.setText(currentEvent.getLocation());
                organiserInfo.setText(currentEvent.getOrganiser());
                admissionRateInfo.setText("€" + String.valueOf(currentEvent.getAdmissionRate()));
                startTimeInfo.setText(String.valueOf(currentEvent.getStartTime()));
                endTimeInfo.setText(String.valueOf(currentEvent.getEndTime()));
            }

            @Override
            public void onStart() {
                System.out.println("Retrieving event data...");
            }

            @Override
            public void onFailed() {
                System.out.println("FAILED");
            }
        });
    }
}
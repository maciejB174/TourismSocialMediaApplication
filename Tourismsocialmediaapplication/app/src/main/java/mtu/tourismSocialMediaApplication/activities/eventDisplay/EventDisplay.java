package mtu.tourismSocialMediaApplication.activities.eventDisplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import mtu.tourismSocialMediaApplication.LoggedUser;
import mtu.tourismSocialMediaApplication.MapsActivity;
import mtu.tourismSocialMediaApplication.Objects.Event;
import mtu.tourismSocialMediaApplication.Objects.User;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.activities.home.HomeActivity;
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
    private TextView tagsDescription;
    private Button back, message, signUp;
    private Event event;

    private LoggedUser currentUser = LoggedUser.getInstance();

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
        tagsDescription = findViewById(R.id.tagsDescription);
        back = findViewById(R.id.backEventDisplay);
        message = findViewById(R.id.messageEventDisplay);
        signUp = findViewById(R.id.signUpEventDisplay);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        eventDetails.readEvent(title, new OnGetDataListener() {
            @Override
            public void onSuccess() {
                Event currentEvent = eventDetails.getCurrentEvent();
                event = currentEvent;
                titleInfo.setText(currentEvent.getTitle());
                descriptionInfo.setText(currentEvent.getDescription());
                locationInfo.setText(currentEvent.getLocation());
                organiserInfo.setText(currentEvent.getOrganiser());
                admissionRateInfo.setText("€" + String.valueOf(currentEvent.getAdmissionRate()));
                startTimeInfo.setText(String.valueOf(currentEvent.getStartTime()));
                if (currentEvent.getTags() != null) {
                    ArrayList<String> tags = currentEvent.getTags();
                    String tagsText = "";
                    for (int i = 0; i < tags.size(); i ++) {
                        if (i == tags.size() - 1) {
                            tagsText += tags.get(i);
                        }
                        else {
                            tagsText += tags.get(i) + ", ";
                        }
                    }
                    tagsDescription.setText(tagsText);
                }
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(EventDisplay.this, MapsActivity.class);
                startActivity(backIntent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = currentUser.getLoggedUser();
                eventDetails.signUpUser(event, user);
                Intent homeIntent = new Intent(EventDisplay.this, HomeActivity.class);
                Toast.makeText(EventDisplay.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                startActivity(homeIntent);
            }
        });
    }
}
package mtu.tourismSocialMediaApplication.activities.createEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import mtu.tourismSocialMediaApplication.LoggedUser;
import mtu.tourismSocialMediaApplication.Objects.Event;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.activities.home.HomeActivity;
import mtu.tourismSocialMediaApplication.database.EventDetails;

public class CreateEvent2 extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    EventDetails eventDetails = EventDetails.getInstance();

    DatePicker date;
    TimePicker startTime;
    Button backButton, hostEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("clients");

        date = findViewById(R.id.dateEdit);
        startTime = findViewById(R.id.startTimeEdit);

        backButton = findViewById(R.id.backCreateEvent2Button);
        hostEventButton = findViewById(R.id.hostEventButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEvent2.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        hostEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = date.getDayOfMonth();
                int month = date.getMonth() + 1;
                int year = date.getYear();

                int hour = startTime.getHour();
                int minute = startTime.getMinute();

                LocalDateTime time = LocalDateTime.of(year, month, day, hour, minute);

                String title = getIntent().getStringExtra("title");
                String location = getIntent().getStringExtra("location");
                ArrayList<String> tags = getIntent().getStringArrayListExtra("tags");
                String description = getIntent().getStringExtra("description");
                float admissionRate = getIntent().getFloatExtra("admissionRate", 0);
                double latitude = getIntent().getDoubleExtra("latitude", 0);
                double longitude = getIntent().getDoubleExtra("longitude", 0);
                FirebaseUser user = fAuth.getCurrentUser();

                final Event event = new Event(title, location, admissionRate);
                event.setStartTime(time);
                event.setDescription(description);
                event.setOrganiser(user.getEmail());
                event.setLatitude(latitude);
                event.setLongitude(longitude);
                event.setTags(tags);

                eventDetails.writeEventDetails(event);

                Intent intent = new Intent(CreateEvent2.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
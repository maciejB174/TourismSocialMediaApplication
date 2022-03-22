package mtu.tourismSocialMediaApplication.activities.createEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class CreateEvent extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference users;
    EventDetails eventDetails = EventDetails.getInstance();

    EditText title, description, location, admissionRate;
    Button backButton, hostEventButton;
    LoggedUser login = LoggedUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("clients");

        title = findViewById(R.id.titleEdit);
        description = findViewById(R.id.descriptionEdit);
        location = findViewById(R.id.locationEdit);
        admissionRate = findViewById(R.id.admissionRateEdit);

        backButton = findViewById(R.id.backCreateEvent1Button);
        hostEventButton = findViewById(R.id.nextEventButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEvent.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        hostEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String titleText = title.getText().toString();
                if (titleText.isEmpty()) {
                    title.setError("Please Enter a Title!");
                    return;
                }

                String locationText = location.getText().toString();
                if (locationText.isEmpty()) {
                    location.setError("Please Enter a Location!");
                    return;
                }

                List<Address> addresses = new ArrayList<Address>();
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    addresses = geocoder.getFromLocationName(locationText, 5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < addresses.size(); i ++) {
                    System.out.println(addresses.get(i));
                }

                Intent intent = new Intent(CreateEvent.this, CreateEvent2.class);
                intent.putExtra("title", titleText);
                intent.putExtra("location", locationText);
                intent.putExtra("admissionRate", Float.parseFloat(admissionRate.getText().toString()));

                String descriptionText = description.getText().toString();
                if (!descriptionText.isEmpty()) {
                    intent.putExtra("description", descriptionText);
                }

                startActivity(intent);
            }
        });
    }
}
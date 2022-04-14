package mtu.tourismSocialMediaApplication.activities.createEvent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mtu.tourismSocialMediaApplication.LoggedUser;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.activities.home.HomeActivity;
import mtu.tourismSocialMediaApplication.database.EventDetails;

public class CreateEvent extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    EditText title, description, admissionRate;
    Spinner tag1, tag2, tag3, tag4;
    Button backButton, hostEventButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("clients");

        title = findViewById(R.id.titleEdit);
        description = findViewById(R.id.descriptionEdit);
        tag1 = findViewById(R.id.tag1);
        tag2 = findViewById(R.id.tag2);
        tag3 = findViewById(R.id.tag3);
        tag4 = findViewById(R.id.tag4);
        admissionRate = findViewById(R.id.admissionRateEdit);

        backButton = findViewById(R.id.backCreateEventButton);
        hostEventButton = findViewById(R.id.nextEventButton1);

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

                Intent intent = new Intent(CreateEvent.this, CreateEvent1.class);

                String tag1Text = tag1.getSelectedItem().toString();
                String tag2Text = tag2.getSelectedItem().toString();
                String tag3Text = tag3.getSelectedItem().toString();
                String tag4Text = tag4.getSelectedItem().toString();
                ArrayList<String> tags = new ArrayList<>();
                tags.add(tag1Text);
                tags.add(tag2Text);
                tags.add(tag3Text);
                tags.add(tag4Text);

                intent.putExtra("title", titleText);
                intent.putExtra("admissionRate", Float.parseFloat(admissionRate.getText().toString()));
                intent.putExtra("tags", tags);
                String descriptionText = description.getText().toString();
                if (!descriptionText.isEmpty()) {
                    intent.putExtra("description", descriptionText);
                }
                startActivity(intent);
            }
        });
    }

}
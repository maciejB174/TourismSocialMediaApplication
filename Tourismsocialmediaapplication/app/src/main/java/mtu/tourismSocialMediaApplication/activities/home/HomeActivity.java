package mtu.tourismSocialMediaApplication.activities.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mtu.tourismSocialMediaApplication.MapsActivity;
import mtu.tourismSocialMediaApplication.Objects.User;
import mtu.tourismSocialMediaApplication.Objects.UserEvents;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.activities.createEvent.CreateEvent;
import mtu.tourismSocialMediaApplication.activities.login.LoginActivity;
import mtu.tourismSocialMediaApplication.activities.createEvent.CreateEvent;
import mtu.tourismSocialMediaApplication.activities.pastEvents.PastEvent;
import mtu.tourismSocialMediaApplication.activities.signUp.SignUp;
import mtu.tourismSocialMediaApplication.activities.suggestedEvents.SuggestedEvent;
import mtu.tourismSocialMediaApplication.activities.upcomingEvents.UpcomingEvent;
import mtu.tourismSocialMediaApplication.database.MessageList;

public class HomeActivity extends AppCompatActivity {

    private Button mapButton;
    private Button recommendedEventsButton;
    private Button pastEventsButton;
    private Button upcomingEventsButton;
    private Button signOutButton;
    private Button createEventButton;
    private UserEvents userEvents = UserEvents.getInstance();
    private DatabaseReference users;
    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mapButton = findViewById(R.id.MapButton);
        recommendedEventsButton = findViewById(R.id.recommendedEventsButton);
        pastEventsButton = findViewById(R.id.PastEventsButton);
        upcomingEventsButton = findViewById(R.id.UpcomingEventsButton);
        signOutButton = findViewById(R.id.SignOutButton);
        createEventButton = findViewById(R.id.createEventButton);

        userEvents.determineBestTags();
        userEvents.findUserEvents();

        users = FirebaseDatabase.getInstance("https://zinc-quest-329510-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("clients");
        fauth = FirebaseAuth.getInstance();

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        createEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(HomeActivity.this, CreateEvent.class);
                startActivity(intent);
            }
        });

        recommendedEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(HomeActivity.this, SuggestedEvent.class);
                startActivity(intent);
            }
        });

        upcomingEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(HomeActivity.this, UpcomingEvent.class);
                startActivity(intent);
            }
        });

        pastEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(HomeActivity.this, PastEvent.class);
                startActivity(intent);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =  new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
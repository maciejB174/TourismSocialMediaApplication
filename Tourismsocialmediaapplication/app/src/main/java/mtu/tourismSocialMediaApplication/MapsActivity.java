package mtu.tourismSocialMediaApplication;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import mtu.tourismSocialMediaApplication.Objects.Event;
import mtu.tourismSocialMediaApplication.activities.eventDisplay.EventDisplay;
import mtu.tourismSocialMediaApplication.database.EventDetails;
import mtu.tourismSocialMediaApplication.database.OnGetDataListener;
import mtu.tourismSocialMediaApplication.database.UserDetails;
import mtu.tourismSocialMediaApplication.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private ActivityMapsBinding binding;
    private GoogleMap mMap;
    private UserDetails userDetails = UserDetails.getInstance();
    private EventDetails eventDetails = EventDetails.getInstance();
    private ArrayList<Event> eventList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        eventDetails.readAllEvents(new OnGetDataListener() {
            @Override
            public void onSuccess() {
                onMapReady(mMap);
            }

            @Override
            public void onStart() {
                System.out.println("Retrieving event data");
            }

            @Override
            public void onFailed() {
                System.out.println("FAILED");
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Info window clicked",
                Toast.LENGTH_SHORT).show();
        Intent intent =  new Intent(MapsActivity.this, EventDisplay.class);
        intent.putExtra("title", marker.getTitle());
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<Event> allEvents = eventDetails.getAllEvents();

        for (int i = 0; i < allEvents.size(); i ++) {
            LatLng event = new LatLng(allEvents.get(i).getLatitude(), allEvents.get(i).getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(event).title(allEvents.get(i).getTitle()).snippet(allEvents.get(i).description));
        }
        googleMap.setOnInfoWindowClickListener(this);

//        mMap.moveCamera(CameraUpdateFactory.newLatLng(event));

    }
}
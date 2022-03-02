package mtu.tourismSocialMediaApplication;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import mtu.tourismSocialMediaApplication.Objects.Event;
import mtu.tourismSocialMediaApplication.Objects.User;
import mtu.tourismSocialMediaApplication.database.EventDetails;
import mtu.tourismSocialMediaApplication.database.OnGetDataListener;
import mtu.tourismSocialMediaApplication.database.UserDetails;
import mtu.tourismSocialMediaApplication.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private EventDetails eventDetails = EventDetails.getInstance();
    private UserDetails userDetails = UserDetails.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Event newEvent = new Event(0, "newEvent", 6, "This is a test event");
        eventDetails.writeEventDetails(newEvent);
        Event result = getEvent();

        User newUser = new User("maciej", "0861064120", "maciej.becmer@mycit.ie", 21, "password");
        userDetails.writeUserDetails(newUser);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private Event getEvent() {
        Event result = new Event();
        eventDetails.readEvent(0, new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                result.setTitle(dataSnapshot.child("title").getValue(String.class));
                result.setId(Integer.parseInt(dataSnapshot.getKey()));
                result.setDescription(dataSnapshot.child("description").getValue(String.class));
                result.setAdmissionRate(Float.parseFloat(dataSnapshot.child("admissionRate").getValue(String.class)));
                System.out.println(result.getTitle());
            }

            @Override
            public void onStart() {
                System.out.println("Started");
            }

            @Override
            public void onFailed(DatabaseError databaseError) {

            }
        });
        return result;
    }
}
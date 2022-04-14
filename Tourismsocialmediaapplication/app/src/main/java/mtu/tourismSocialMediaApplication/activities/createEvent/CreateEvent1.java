package mtu.tourismSocialMediaApplication.activities.createEvent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import mtu.tourismSocialMediaApplication.R;

public class CreateEvent1 extends AppCompatActivity {

    ThreadRunner thread = new ThreadRunner();
    Button back, next;
    EditText addressLine1, addressLine2, townCity, countyProvince;
    Spinner country;

    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event1);
        back = findViewById(R.id.backCreateEvent1Button);
        next = findViewById(R.id.nextEventButton2);
        addressLine1 = findViewById(R.id.addressLine1Edit);
        addressLine2 = findViewById(R.id.addressLine2Edit);
        townCity = findViewById(R.id.townCityEdit);
        countyProvince = findViewById(R.id.countyProvinceEdit);
        country = findViewById(R.id.countrySpinner);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address1Text = addressLine1.getText().toString();
                if (address1Text.isEmpty()) {
                    addressLine1.setError("Please Enter Address Line 1!");
                    return;
                }

                String address2Text = addressLine2.getText().toString();
                if (address2Text.isEmpty()) {
                    addressLine2.setError("Please Enter Address Line 2!");
                    return;
                }

                String townText = townCity.getText().toString();
                if (townText.isEmpty()) {
                    townCity.setError("Please Enter a Town!");
                    return;
                }

                String countyText = countyProvince.getText().toString();
                if (countyText.isEmpty()) {
                    countyProvince.setError("Please Enter a County!");
                    return;
                }

                String title = getIntent().getStringExtra("title");
                ArrayList<String> tags = getIntent().getStringArrayListExtra("tags");
                String description = getIntent().getStringExtra("description");
                float admissionRate = getIntent().getFloatExtra("admissionRate", 0);

                intent = new Intent(CreateEvent1.this, CreateEvent2.class);

                thread.run();

                String countryText = country.getSelectedItem().toString();

                String locationText = address1Text + " , " + address2Text + " , " + townText + " , " + countyText + " , " + countryText;

                intent.putExtra("location", locationText);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("admissionRate", admissionRate);
                intent.putExtra("tags", tags);

                startActivity(intent);
            }
        });
    }

    class ThreadRunner extends Thread{

        private boolean exit = false;

        ThreadRunner(){
        }

        @Override
        public void run() {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            String address1Text = addressLine1.getText().toString();
            String address2Text = addressLine2.getText().toString();
            String townText = townCity.getText().toString();
            String countyText = countyProvince.getText().toString();
            String countryText = country.getSelectedItem().toString();
            String locationText = address1Text + " , " + address2Text + " , " + townText + " , " + countyText + " , " + countryText;
            while (!exit) {
                try {
                    Thread.sleep(10);
                    List<Address> addresses = new ArrayList<Address>();
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        addresses = geocoder.getFromLocationName(locationText, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    for (int i = 0; i < addresses.size(); i++) {
                        System.out.println(addresses.get(i));
                        intent.putExtra("latitude", addresses.get(i).getLatitude());
                        intent.putExtra("longitude", addresses.get(i).getLongitude());
                        exit = true;
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                    exit = true;
                }
            }
        }
    }
}
package mtu.tourismSocialMediaApplication.activities.editEvent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import mtu.tourismSocialMediaApplication.Objects.UserEvents;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.activities.home.HomeActivity;
import mtu.tourismSocialMediaApplication.database.EventDetails;
import mtu.tourismSocialMediaApplication.database.OnGetDataListener;

public class EditEvent extends AppCompatActivity {

    Button edit, back;
    EditText description, admissionRate;

    EventDetails eventDetails = EventDetails.getInstance();
    UserEvents userEvents = UserEvents.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        Intent oldIntent = getIntent();
        String eventTitle = oldIntent.getStringExtra("title");

        edit = findViewById(R.id.editConfirmButton);
        back = findViewById(R.id.backEditButton);

        description = findViewById(R.id.editDescription);
        admissionRate = findViewById(R.id.editAdmissionRate);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventDetails.editEvent(eventTitle, description.getText().toString(), admissionRate.getText().toString());
                Intent intent = new Intent(EditEvent.this, HomeActivity.class);
                userEvents.findUserEvents();
                Toast.makeText(EditEvent.this, "Successfully edited event", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
    }
}
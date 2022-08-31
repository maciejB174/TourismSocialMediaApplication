//package mtu.tourismSocialMediaApplication.activities.rateEvent;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.RatingBar;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import mtu.tourismSocialMediaApplication.R;
//import mtu.tourismSocialMediaApplication.database.EventDetails;
//
//public class EventRating extends AppCompatActivity {
//
//    RatingBar ratingbar;
//    Button button;
//    EventDetails eventDetails = EventDetails.getInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setTitle("Rate the app");
//        setContentView(R.layout.activity_rate_event);
//        addListenerOnButtonClick();
//    }
//    public void addListenerOnButtonClick(){
//        ratingbar=(RatingBar)findViewById(R.id.ratingBar);
//        button=(Button)findViewById(R.id.button);
//        //Performing action on Button Click
//        button.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View arg0) {
//                float rating= (float) ratingbar.getRating();
//                if (rating == 0){
//                    Context context = getApplicationContext();
//                    CharSequence text = "Rating must be above 0";
//                    int duration = Toast.LENGTH_SHORT;
//
//                    Toast toast = Toast.makeText(context, text, duration);
//                    toast.show();
//                }
//                else {
//                    Intent pastIntent = getIntent();
//                    String title = pastIntent.getStringExtra("title");
//                    eventDetails.sendRating(title, rating);
//                    Context context = getApplicationContext();
//                    CharSequence text = "Thank you for rating the event";
//                    int duration = Toast.LENGTH_SHORT;
//
//                    Toast toast = Toast.makeText(context, text, duration);
//                    toast.show();
//                    finish();
//                }
//            }
//
//        });
//    }
//}
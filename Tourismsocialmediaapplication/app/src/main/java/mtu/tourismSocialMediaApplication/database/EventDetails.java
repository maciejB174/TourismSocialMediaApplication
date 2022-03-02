package mtu.tourismSocialMediaApplication.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mtu.tourismSocialMediaApplication.Objects.Event;

public class EventDetails {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://zinc-quest-329510-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference eventReference = firebaseDatabase.getReference("events");
    private static EventDetails ourInstance = new EventDetails();

    public EventDetails() {
    }

    public static EventDetails getInstance () {
        return ourInstance;
    }

    public void writeEventDetails(Event event) {
        eventReference.child(String.valueOf(event.id)).child("title").setValue(event.title);
//        eventReference.child(String.valueOf(event.id)).child("organiser").setValue(String.valueOf(event.organiser));
//        eventReference.child(String.valueOf(event.id)).child("startTime").setValue(String.valueOf(event.startTime));
//        eventReference.child(String.valueOf(event.id)).child("endTime").setValue(String.valueOf(event.endTime));
        eventReference.child(String.valueOf(event.id)).child("admissionRate").setValue(String.valueOf(event.admissionRate));
        eventReference.child(String.valueOf(event.id)).child("description").setValue(event.description);
//        eventReference.child(String.valueOf(event.id)).child("eventChat").child();
//        eventReference.child(String.valueOf(event.id)).child("tags").child();
//        eventReference.child(String.valueOf(event.id)).child("attendees").child();
    }

    public void readEvent(int id, final OnGetDataListener listener) {
        DatabaseReference ref = firebaseDatabase.getReference("events/");
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.getKey().equals(String.valueOf(id))) {
                        listener.onSuccess(postSnapshot);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed(error);
            }
        });
    }
}

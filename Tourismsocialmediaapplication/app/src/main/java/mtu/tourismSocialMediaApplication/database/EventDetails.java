package mtu.tourismSocialMediaApplication.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.util.ArrayList;

import mtu.tourismSocialMediaApplication.Objects.Event;
import mtu.tourismSocialMediaApplication.Objects.User;
import mtu.tourismSocialMediaApplication.Objects.UserEvents;

public class EventDetails {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://zinc-quest-329510-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference eventReference = firebaseDatabase.getReference("events");
    private static EventDetails ourInstance = new EventDetails();
    public ArrayList<Event> allEvents = new ArrayList<>();
    public Event currentEvent;
    public UserEvents userEventList = UserEvents.getInstance();

    public EventDetails() {
    }

    public static EventDetails getInstance () {
        return ourInstance;
    }

    public void writeEventDetails(Event event) {
        eventReference.child(String.valueOf(event.title)).child("title").setValue(event.title);
        eventReference.child(String.valueOf(event.title)).child("organiser").setValue(String.valueOf(event.organiser));
        eventReference.child(String.valueOf(event.title)).child("startTime").setValue(String.valueOf(event.startTime));
        eventReference.child(String.valueOf(event.title)).child("endTime").setValue(String.valueOf(event.endTime));
        eventReference.child(String.valueOf(event.title)).child("admissionRate").setValue(String.valueOf(event.admissionRate));
        eventReference.child(String.valueOf(event.title)).child("description").setValue(event.description);
        eventReference.child(String.valueOf(event.title)).child("location").setValue(event.location);
        eventReference.child(String.valueOf(event.title)).child("latitude").setValue(event.latitude);
        eventReference.child(String.valueOf(event.title)).child("longitude").setValue(event.longitude);
//        eventReference.child(String.valueOf(event.id)).child("eventChat").child();
        for (int i = 0; i < event.tags.size(); i ++) {
            eventReference.child(String.valueOf(event.title)).child("tags").child("tag" + i).setValue(event.tags.get(i));
        }
    }

    public void readAllEvents(final OnGetDataListener listener) {
        DatabaseReference ref = firebaseDatabase.getReference("events/");
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    System.out.println(postSnapshot.child("admissionRate").getValue(String.class));
                    Event event = new Event(postSnapshot.child("title").getValue(String.class), postSnapshot.child("location").getValue(String.class), Float.valueOf(postSnapshot.child("admissionRate").getValue(String.class)));
                    event.setDescription(postSnapshot.child("description").getValue(String.class));
                    event.setOrganiser(postSnapshot.child("organiser").getValue(String.class));
                    event.setLatitude(postSnapshot.child("latitude").getValue(Double.class));
                    event.setLongitude(postSnapshot.child("longitude").getValue(Double.class));
                    event.setStartTime(LocalDateTime.parse(postSnapshot.child("startTime").getValue(String.class)));
//                    event.setEndTime(LocalDateTime.parse(postSnapshot.child("endTime").getValue(String.class)));
                    ArrayList<String> tags = new ArrayList<>();
                    for (DataSnapshot tagsSnapshot : postSnapshot.child("tags").getChildren()) {
                        tags.add(tagsSnapshot.getValue(String.class));
                    }
                    allEvents.add(event);
                }
                listener.onSuccess();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed();
            }
        });
    }

    public void readEvent(String title, final OnGetDataListener listener) {
        DatabaseReference ref = firebaseDatabase.getReference("events/");
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.child("title").getValue(String.class).equals(title)) {
                        Event event = new Event(postSnapshot.child("title").getValue(String.class), postSnapshot.child("location").getValue(String.class), Float.valueOf(postSnapshot.child("admissionRate").getValue(String.class)));
                        event.setDescription(postSnapshot.child("description").getValue(String.class));
                        event.setOrganiser(postSnapshot.child("organiser").getValue(String.class));
                        event.setLatitude(postSnapshot.child("latitude").getValue(Double.class));
                        event.setLongitude(postSnapshot.child("longitude").getValue(Double.class));
                        event.setStartTime(LocalDateTime.parse(postSnapshot.child("startTime").getValue(String.class)));
//                        event.setEndTime(LocalDateTime.parse(postSnapshot.child("endTime").getValue(String.class)));
                        ArrayList<String> tags = new ArrayList<>();
                        for (DataSnapshot tagsSnapshot : postSnapshot.child("tags").getChildren()) {
                            tags.add(tagsSnapshot.getValue(String.class));
                        }
                        event.setTags(tags);
                        currentEvent = event;
                    }
                }
                listener.onSuccess();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed();
            }
        });
    }

    public void signUpUser(Event event, String email) {
        getAttendees(event, new OnGetDataListener() {
            @Override
            public void onSuccess() {
                eventReference.child(String.valueOf(event.title)).child("attendees").child("" + event.getNoAttendees() + 1).setValue(email);
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailed() {

            }
        });

    }

    public void getAttendees(Event event, final OnGetDataListener listener) {
        DatabaseReference ref = firebaseDatabase.getReference("events/");
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.child("title").getValue(String.class).equals(event.getTitle())) {
                        int attendees = 0;
                        for (DataSnapshot attendeesSnapshot : postSnapshot.child("attendees").getChildren()) {
                            attendees += 1;
                        }
                        event.setNoAttendees(attendees);
                    }
                }
                listener.onSuccess();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed();
            }
        });
    }

    public ArrayList<Event> getUserEvents(String email) {
        ArrayList<Event> userEvents = new ArrayList<Event>();
        findUserEvents(userEvents, email, new OnGetDataListener() {
            @Override
            public void onSuccess() {
                for (int i = 0; i < userEvents.size(); i ++) {
                    System.out.println(userEvents.get(i));
                }
            }

            @Override
            public void onStart() {

            }

            @Override
            public void onFailed() {

            }
        });
        return userEvents;
    }

    public void findUserEvents(ArrayList<Event> userEvents, String email, final OnGetDataListener listener) {
        DatabaseReference ref = firebaseDatabase.getReference("events/");
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    if (postSnapshot.child("attendees").exists()) {
                        for (DataSnapshot attendeeSnapshot : postSnapshot.child("attendees").getChildren()) {
                            if (attendeeSnapshot.getValue(String.class).equals(email)) {
                                Event event = new Event(postSnapshot.child("title").getValue(String.class), postSnapshot.child("location").getValue(String.class), Float.valueOf(postSnapshot.child("admissionRate").getValue(String.class)));
                                event.setDescription(postSnapshot.child("description").getValue(String.class));
                                event.setOrganiser(postSnapshot.child("organiser").getValue(String.class));
                                event.setLatitude(postSnapshot.child("latitude").getValue(Double.class));
                                event.setLongitude(postSnapshot.child("longitude").getValue(Double.class));
                                event.setStartTime(LocalDateTime.parse(postSnapshot.child("startTime").getValue(String.class)));
//                        event.setEndTime(LocalDateTime.parse(postSnapshot.child("endTime").getValue(String.class)));
                                ArrayList<String> tags = new ArrayList<>();
                                for (DataSnapshot tagsSnapshot : postSnapshot.child("tags").getChildren()) {
                                    tags.add(tagsSnapshot.getValue(String.class));
                                }
                                event.setTags(tags);
                                userEvents.add(event);
                            }
                        }
                    }
                }
                listener.onSuccess();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onFailed();
            }
        });
    }

    public ArrayList<Event> getAllEvents() {
        return this.allEvents;
    }

    public Event getCurrentEvent() {
        return this.currentEvent;
    }
}

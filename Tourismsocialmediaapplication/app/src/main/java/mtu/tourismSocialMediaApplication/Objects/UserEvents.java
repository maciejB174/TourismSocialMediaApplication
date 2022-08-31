package mtu.tourismSocialMediaApplication.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import mtu.tourismSocialMediaApplication.database.EventDetails;
import mtu.tourismSocialMediaApplication.database.OnGetDataListener;

public class UserEvents {
    private static UserEvents ourInstance = new UserEvents();
    private ArrayList<Event> userEvents;
    private ArrayList<Event> upcomingEvents = new ArrayList<Event>();
    private ArrayList<Event> pastEvents = new ArrayList<Event>();
    private EventDetails eventDetails = EventDetails.getInstance();
    private String userEmail;
    private ArrayList<String> bestTags;
    private ArrayList<Event> suggestedEvents = new ArrayList<Event>();
    Process mProcess;

    public UserEvents() {

    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ArrayList<Event> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(ArrayList<Event> userEvents) {
        this.userEvents = userEvents;
    }

    public ArrayList<Event> getPastEvents() {
        return pastEvents;
    }

    public ArrayList<Event> getUpcomingEvents() {
        return upcomingEvents;
    }

    public ArrayList<Event> returnSuggestedEvents() { return suggestedEvents; }

    public void findUserEvents() {
        userEvents = eventDetails.getUserEvents(userEmail);
    }

    public void determineBestTags() {
        ArrayList<String> allTags = new ArrayList<String>();
        for (int i = 0; i < userEvents.size(); i ++) {
            ArrayList<String> eventTags = userEvents.get(i).getTags();
            for (int j = 0; j < eventTags.size(); j ++) {
//                System.out.println(eventTags.get(j));
                allTags.add(eventTags.get(j));
            }
        }
        System.out.println(allTags);
        HashMap<String, Integer> occurences = new HashMap<String, Integer>();
        for (int i = 0; i < allTags.size(); i ++) {
            if (occurences.containsKey(allTags.get(i))) {
                occurences.put(allTags.get(i), occurences.get(allTags.get(i)) + 1);
            }
            else {
                String key = allTags.get(i);
                occurences.put(key, 1);
            }
        }

        ArrayList<String> futureTags = new ArrayList<String>();
        int highestOccurrence = 0;
        for (Map.Entry<String, Integer> entry : occurences.entrySet()) {
            if (entry.getValue() > highestOccurrence) {
                highestOccurrence = entry.getValue();
                futureTags. clear();
                futureTags.add(entry.getKey());
            }
            else if (entry.getValue() == highestOccurrence) {
                futureTags.add(entry.getKey());
            }
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        bestTags = futureTags;
        eventDetails.readAllEvents(new OnGetDataListener() {
            @Override
            public void onSuccess() {
                getSuggestedEvents();
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
    }

    public void getSuggestedEvents() {
        ArrayList<Event> allEvents = eventDetails.getAllEvents();
        ArrayList<Event> suggestedUpcomingEvents = new ArrayList<Event>();
        LocalDateTime localDateTime = LocalDateTime.now();
        for (int i = 0; i < allEvents.size(); i ++) {

//            System.out.println(allEvents.get(i).getTitle());
            if (allEvents.get(i).getStartTime().isAfter(localDateTime)) {
                int flag = 0;
                //if user is already signed up to event
                ArrayList<String> attendees = allEvents.get(i).getAttendees();
                if (!attendees.isEmpty())
                    for (int j = 0; j < attendees.size(); j ++) {
                        if (attendees.get(j).equals(userEmail)) {
                            flag = 1;
                            break;
                        }
                    }
                //location
                if (!allEvents.get(i).getLocation().contains("Ireland")) {
                    flag = 1;
                }

                for (int m = 0; m < suggestedUpcomingEvents.size(); m ++) {
                    if (suggestedUpcomingEvents.get(m).getTitle().equals(allEvents.get(i).getTitle())) {
                        flag = 1;
                    }
                }

                //if tags are in event
                ArrayList<String> eventTags = allEvents.get(i).getTags();
                if (flag == 0) {
                    int alreadyAdded = 0;
                    for (int j = 0; j < eventTags.size(); j ++) {
                        for (int k = 0; k < bestTags.size(); k ++) {
                            if (eventTags.get(j).equals(bestTags.get(k))) {
                                if (alreadyAdded == 0) {
                                    suggestedUpcomingEvents.add(allEvents.get(i));
                                    alreadyAdded = 1;
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i =0; i < suggestedUpcomingEvents.size(); i ++) {
            System.out.println(suggestedUpcomingEvents.get(i).getTitle());
        }
        suggestedEvents = suggestedUpcomingEvents;
    }

    public void setUpcomingAndPastEvents() {
        upcomingEvents.clear();
        pastEvents.clear();
        LocalDateTime localDateTime = LocalDateTime.now();
        for (int i = 0; i < userEvents.size(); i ++) {
            System.out.println(userEvents.get(i).getStartTime());
            if (userEvents.get(i).getStartTime().isAfter(localDateTime)) {
                upcomingEvents.add(userEvents.get(i));
            } else {
                pastEvents.add(userEvents.get(i));
            }
        }
    }

    public static UserEvents getInstance () {
        return ourInstance;
    }
}

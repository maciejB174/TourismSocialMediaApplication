package mtu.tourismSocialMediaApplication.Objects;

import java.time.LocalDateTime;
import java.util.ArrayList;

import mtu.tourismSocialMediaApplication.database.EventDetails;

public class UserEvents {
    private static UserEvents ourInstance = new UserEvents();
    private ArrayList<Event> userEvents;
    private ArrayList<Event> upcomingEvents = new ArrayList<Event>();
    private ArrayList<Event> pastEvents = new ArrayList<Event>();
    private EventDetails eventDetails = EventDetails.getInstance();
    private String userEmail;

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

    public void findUserEvents() {
        userEvents = eventDetails.getUserEvents(userEmail);
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

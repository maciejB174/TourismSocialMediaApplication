package mtu.tourismSocialMediaApplication.Objects;

import android.location.Location;

import java.time.LocalDateTime;
import java.util.ArrayList;

import mtu.tourismSocialMediaApplication.database.Message;
import mtu.tourismSocialMediaApplication.database.UserDetails;

public class Event {
    public int id;
    public String title;
    public User organiser;
    public Location location;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public float admissionRate;
    public String description;
    public ArrayList<Message> eventChat;
    public ArrayList<String> tags;
    public ArrayList<User> attendees;


    public Event() {
    }

    public Event(int id, String title, float admissionRate, String description) {
        this.id = id;
        this.title = title;
        this.admissionRate = admissionRate;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOrganiser() {
        return organiser;
    }

    public void setOrganiser(User organiser) {
        this.organiser = organiser;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public float getAdmissionRate() {
        return admissionRate;
    }

    public void setAdmissionRate(float admissionRate) {
        this.admissionRate = admissionRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Message> getEventChat() {
        return eventChat;
    }

    public void setEventChat(ArrayList<Message> eventChat) {
        this.eventChat = eventChat;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public ArrayList<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<User> attendees) {
        this.attendees = attendees;
    }
}

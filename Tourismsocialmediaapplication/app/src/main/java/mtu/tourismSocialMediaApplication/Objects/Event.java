package mtu.tourismSocialMediaApplication.Objects;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.provider.Telephony;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mtu.tourismSocialMediaApplication.database.Message;
import mtu.tourismSocialMediaApplication.database.UserDetails;

public class Event {
    public int id;
    public String title;
    public String organiser;
    public String location;
    public double latitude;
    public double longitude;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public float admissionRate;
    public String description;
    public ArrayList<Message> eventChat;
    public ArrayList<String> tags;
    public ArrayList<User> attendees;
    public int noAttendees;


    public Event() {
    }

    public Event(String title, String location, float admissionRate) {
        this.title = title;
        this.admissionRate = admissionRate;
        this.location = location;
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

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public int getNoAttendees() {
        return noAttendees;
    }

    public void setNoAttendees(int attendees) {
        this.noAttendees = attendees;
    }
}

package mtu.tourismSocialMediaApplication.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;

public class Message implements Serializable {
    private String sender;
    private String content;
    private String date;

    public Message(){
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void sendMessage(String eventTitle){
        final FirebaseDatabase database = FirebaseDatabase.getInstance("https://zinc-quest-329510-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference ref1 = database.getReference("events/" + eventTitle + "/messages");
        String key = ref1.push().getKey();
        ref1.child(key).child("content").setValue(content);
        ref1.child(key).child("date").setValue(date);
        ref1.child(key).child("sender").setValue(sender);
    }

    @Override
    public String toString(){
            String string = content + "\n" + date + "\n" + sender + "\n";
            return string;
    }
}

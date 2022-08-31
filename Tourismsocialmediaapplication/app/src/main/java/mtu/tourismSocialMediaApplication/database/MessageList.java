package mtu.tourismSocialMediaApplication.database;

import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import mtu.tourismSocialMediaApplication.LoggedUser;

public class MessageList {

    private static MessageList ourInstance = new MessageList();

    public static MessageList getInstance () {
        return ourInstance;
    }

    final ArrayList<Message> messageArrayList = new ArrayList<Message>();
    boolean isReverse = false;

    final FirebaseDatabase database = FirebaseDatabase.getInstance("https://zinc-quest-329510-default-rtdb.europe-west1.firebasedatabase.app/");

    public ArrayList<Message> getMessageArrayList(){
        return messageArrayList;
    }

    public void clear(){
        ourInstance = new MessageList();
    }

    public void reverseOrder(){
        Collections.reverse(messageArrayList);
        isReverse = true;
    }

    public void getMessagesForEvent(String eventTitle, final OnGetDataListener listener) {
        messageArrayList.clear();
        DatabaseReference ref = database.getReference("events/");
        listener.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if (postSnapshot.getKey().equals(eventTitle)) {
                        System.out.println("Hello world");
                        if (postSnapshot.child("messages").exists()) {
                            for (DataSnapshot messageSnapshot : postSnapshot.child("messages").getChildren()) {
                                String content = messageSnapshot.child("content").getValue(String.class);
                                String date = messageSnapshot.child("date").getValue(String.class);
                                String sender = messageSnapshot.child("sender").getValue(String.class);
                                Message message = new Message();
                                message.setContent(content);
                                message.setDate(date);
                                message.setSender(sender);
                                System.out.println(content + sender + date + "\n\n\n\n\n\n");
                                messageArrayList.add(message);
                            }
                        }
                    }
                }
                listener.onSuccess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listener.onFailed();
            }
        });
    }
}

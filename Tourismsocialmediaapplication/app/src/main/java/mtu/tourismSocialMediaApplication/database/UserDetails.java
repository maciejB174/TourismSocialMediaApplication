package mtu.tourismSocialMediaApplication.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import mtu.tourismSocialMediaApplication.Objects.User;

public class UserDetails {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://zinc-quest-329510-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference eventReference = firebaseDatabase.getReference("users");
    private static UserDetails ourInstance = new UserDetails();

    public UserDetails() {
    }

    public static UserDetails getInstance () {
        return ourInstance;
    }

    public void writeUserDetails(User user) {
        eventReference.child(String.valueOf(user.id)).child("username").setValue(user.username);
        eventReference.child(String.valueOf(user.id)).child("phoneNo").setValue(String.valueOf(user.phoneNo));
        eventReference.child(String.valueOf(user.id)).child("email").setValue(user.email);
        eventReference.child(String.valueOf(user.id)).child("age").setValue(user.age);
    }

    public void readUser(int id, final OnGetDataListener listener) {
        DatabaseReference ref = firebaseDatabase.getReference("users/");
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

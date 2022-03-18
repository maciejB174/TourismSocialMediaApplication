package mtu.tourismSocialMediaApplication.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public interface OnGetDataListener {
    void onSuccess();
    void onStart();
    void onFailed();
}

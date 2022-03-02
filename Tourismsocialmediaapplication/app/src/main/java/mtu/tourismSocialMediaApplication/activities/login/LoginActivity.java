package mtu.tourismSocialMediaApplication.activities.login;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mtu.tourismSocialMediaApplication.LoggedUser;
import mtu.tourismSocialMediaApplication.Objects.User;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.activities.signUp.SignUp;
import mtu.tourismSocialMediaApplication.database.UserDetails;


public class LoginActivity extends FragmentActivity {

    private UserDetails userDetails = UserDetails.getInstance();
    private User currentUser;
    private LoggedUser loggedUser = LoggedUser.getInstance();
//    private MessageList messages = MessageList.getInstance();
    private static EditText email;
    private static EditText password;
    private Button loginButton;
    private Button signUpButton;
    private DatabaseReference users;
    private TextView forgot_password;
    private FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.editEmailP);
        password = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.signupButton);
        signUpButton = findViewById(R.id.submit_Button);
        forgot_password = findViewById(R.id.forgot_password);
        users = FirebaseDatabase.getInstance("https://zinc-quest-329510-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("clients");
        fauth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(LoginActivity.this, SignUp.class);
                System.out.println("Hello world");
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(),password.getText().toString());
            }
        });

//        forgot_password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, ForgottenPasswordActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    private void login(final String useremail, final String userpassword) {

        FirebaseUser currentUser = fauth.getCurrentUser();
        if (currentUser != null) {
            System.out.println("Hello World");
//            reload();
        }

        System.out.println(useremail + userpassword);
        if (TextUtils.isEmpty(useremail)) {
            email.setError("Please enter an email address");
        } else if (TextUtils.isEmpty((userpassword))) {
            password.setError("Please enter a password");
        } else {
            fauth.signInWithEmailAndPassword(useremail.toLowerCase(), userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        System.out.println("SignInWithEmail: Success");
                        FirebaseUser user = fauth.getCurrentUser();
                        System.out.println(user.getEmail());
//                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        System.out.println("SignInWithEmail: Failure");
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
//                        updateUI(null);
                    }
                }
            });
        }
//
//            fauth.signInWithEmailAndPassword(useremail.toLowerCase(), userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if (task.isSuccessful()) {
//                        users.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                for (final DataSnapshot postSnapshot : snapshot.getChildren()) {
//                                    if (postSnapshot.child("email").getValue().toString().equals(useremail)) {
//                                        currentUser.setUsername(postSnapshot.child("username").getValue().toString());
//                                        currentUser.setPassword(userpassword);
//                                        currentUser.setAge(Integer.parseInt((String) postSnapshot.child("age").getValue()));
//                                        currentUser.setEmail(useremail);
//                                        currentUser.setPhoneNo(postSnapshot.child("phoneNo").getValue().toString());
//                                        currentUser.setId(Integer.parseInt((String) postSnapshot.child("id").getValue()));
////                                        if (loginDetails.financialDetailsPresent()) {
////                                            loginDetails.runLoanSuitability();
////                                        }
//                                        loggedUser.setLoggedUser(currentUser);
//                                        System.out.println(currentUser.getEmail());
////                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
////                                        startActivity(intent);
//                                        Toast.makeText(LoginActivity.this, "Successful login!", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                System.out.println("Cancelled");
//                            }
//                        });
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
    }
}
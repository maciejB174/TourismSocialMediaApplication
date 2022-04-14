package mtu.tourismSocialMediaApplication.activities.signUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mtu.tourismSocialMediaApplication.LoggedUser;
import mtu.tourismSocialMediaApplication.Objects.User;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.activities.login.LoginActivity;
import mtu.tourismSocialMediaApplication.database.UserDetails;

public class SignUp extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference users;

    EditText username, email, age, password, phone ;
    Button signup_button, login_button;
    TextView terms;
    CheckBox termsBox;
    LoggedUser login = LoggedUser.getInstance();
    UserDetails userDetails = UserDetails.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("clients");

        username = findViewById(R.id.editUsername);
        email = findViewById(R.id.editEmail);
        age = findViewById(R.id.editAge);
        password = findViewById(R.id.editPassword);
        phone = findViewById(R.id.editPhone);
        terms = findViewById(R.id.termsSignup);
        termsBox = findViewById(R.id.termsCheckBox);

        signup_button = findViewById(R.id.signupButton);
        login_button = findViewById(R.id.login_activity_Button);

//        if (fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
////            finish();
//        }

//        terms.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(SignUp.this, TermsAndConditions.class));
//            }
//        });


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                String usernameString = username.getText().toString();
                String emailString = email.getText().toString();
                int ageValue = Integer.parseInt(age.getText().toString());
                String passwordString = password.getText().toString();
                String phoneString = phone.getText().toString();
                intent.putExtra("username", usernameString);
                intent.putExtra("email", emailString);
                intent.putExtra("age", ageValue);
                intent.putExtra("password", passwordString);
                intent.putExtra("phone", phoneString);
                startActivity(intent);
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final User user = new User(username.getText().toString(), phone.getText().toString().toLowerCase(), email.getText().toString().toLowerCase(),
                        Integer.parseInt(age.getText().toString()));

                if (TextUtils.isEmpty(user.getUsername())) {
                    username.setError("Please Enter a Username!");
                    return;
                }
                if (TextUtils.isEmpty(user.getEmail())) {
                    email.setError("Please Enter an Email!");
                    return;
                }
                if (TextUtils.isEmpty(user.getPhoneNo())) {
                    username.setError("Please Enter a Phone Number");
                    return;
                }
                String passwordText = password.getText().toString();
                if (passwordText.matches("")) {
                    password.setError("Please Enter a Password");
                    return;
                }
                if (user.getAge() == 0) {
                    email.setError("Please Enter your age");
                    return;
                }
                if (passwordText.length() < 8) {
                    password.setError("Password must be at least 8 characters!");
                    return;
                }
                if (!termsBox.isChecked()) {
                    termsBox.setError("Please agree to the terms and conditions before signing up");
                    return;
                }

                users.child(user.getUsername()).setValue(user);

                fAuth.createUserWithEmailAndPassword(user.getEmail(), passwordText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(SignUp.this, LoginActivity.class);
                            startActivity(intent);
                            Toast.makeText(SignUp.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                            userDetails.writeUserDetails(user);
                        } else {
                            Toast.makeText(SignUp.this, "Error: " + task.getException(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}
package mtu.tourismSocialMediaApplication.activities.message;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;

import mtu.tourismSocialMediaApplication.LoggedUser;
import mtu.tourismSocialMediaApplication.R;
import mtu.tourismSocialMediaApplication.database.Message;
import mtu.tourismSocialMediaApplication.database.MessageList;

public class ChatActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    MyRecyclerViewAdapter adapter;
    MessageList messages = MessageList.getInstance();
    LoggedUser loggedUser = LoggedUser.getInstance();

    Button backButton,replyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Message Log");
        setContentView(R.layout.activity_chat);
        RecyclerView recyclerView = findViewById(R.id.item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        backButton = (Button) findViewById(R.id.backMessage);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();
            }
        });

        replyButton = (Button) findViewById(R.id.replyMessage);
        replyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText messageTextView = (EditText) findViewById(R.id.messageText);
                String messageText = messageTextView.getText().toString();
                Message message = new Message();
                message.setContent(messageText);
                message.setDate(LocalDateTime.now().toString());
                message.setSender(loggedUser.getLoggedUser());
                Intent intent = getIntent();
                message.sendMessage(intent.getStringExtra("title"));
                Toast.makeText(ChatActivity.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    public void OpenMessage(int position){
    }

}
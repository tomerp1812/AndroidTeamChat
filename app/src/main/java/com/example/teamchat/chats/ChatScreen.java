package com.example.teamchat.chats;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.adapters.ChatListAdapter;
import com.example.teamchat.viewModels.ChatViewModel;

public class ChatScreen extends AppCompatActivity {
    private Context context;
    private ChatViewModel chatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        context = getApplicationContext();
        // Retrieve the token from the intent
        String authorizationHeader = getIntent().getStringExtra("token");

        //Retrieve the username and profile picture fro the intent
        String username = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

        chatViewModel = new ChatViewModel(context,authorizationHeader);
        RecyclerView lvChat = findViewById(R.id.lvChat);
        lvChat.setLayoutManager(new LinearLayoutManager(this));

        final ChatListAdapter adapter = new ChatListAdapter(this);
        lvChat.setAdapter(adapter);
        chatViewModel.get().observe(this, adapter::setMessages);

        ImageView image_send_message = findViewById(R.id.image_send_message);
        image_send_message.setImageURI(Uri.parse(profilePic));



        TextView tvContact = findViewById(R.id.tvContact);
        tvContact.setText(username);

        ImageView imContact = findViewById(R.id.imContact);
        imContact.setImageURI(Uri.parse(profilePic));

        ImageButton ibSend = findViewById(R.id.ibSend);
        ibSend.setOnClickListener(view ->{
            EditText etMessage = findViewById(R.id.etMessage);
            String msg = etMessage.getText().toString();
            chatViewModel.add(msg);
        });


    }
}
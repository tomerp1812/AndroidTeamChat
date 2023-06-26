package com.example.teamchat.chats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.adapters.ChatListAdapter;
import com.example.teamchat.viewModels.ChatViewModel;

public class ChatScreen extends AppCompatActivity {
    private Context context;
    private ChatViewModel chatViewModel;


    private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", -1);
            String created = intent.getStringExtra("created");
            String sender = intent.getStringExtra("sender");
            String content = intent.getStringExtra("content");
            chatViewModel.onReceivedMessage(id, created, sender, content);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);

        // Register the broadcast receiver
        IntentFilter intentFilter = new IntentFilter("ACTION_MESSAGE_RECEIVED");
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, intentFilter);

        context = getApplicationContext();
        // Retrieve the token from the intent
        String authorizationHeader = getIntent().getStringExtra("token");

        String me = getIntent().getStringExtra("me");

        //Retrieve the username and profile picture from the intent
        String username = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

        int id = getIntent().getIntExtra("id", -1);

        ImageView imContact = findViewById(R.id.imContact);
        byte[] imageInBytes = Base64.decode(profilePic, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageInBytes, 0, imageInBytes.length);
        imContact.setImageBitmap(bitmap);

        chatViewModel = new ChatViewModel(context, authorizationHeader, id, me, username);
        RecyclerView lvChat = findViewById(R.id.lvChat);
        lvChat.setLayoutManager(new LinearLayoutManager(this));

//        UserNoPass user = chatViewModel.getUserDetail(me);
//        String myPic = user.getProfilePic();
//        byte[] imageInBytes1 = Base64.decode(myPic, Base64.DEFAULT);
//        Bitmap bitmap1 = BitmapFactory.decodeByteArray(imageInBytes1, 0, imageInBytes1.length);
        Bitmap bitmap1=null;
        final ChatListAdapter adapter = new ChatListAdapter(this, me,bitmap,bitmap1);
        lvChat.setAdapter(adapter);
        chatViewModel.get().observe(this, adapter::setMessages);

        TextView tvContact = findViewById(R.id.tvContact);
        tvContact.setText(username);


        ImageButton ibSend = findViewById(R.id.ibSend);
        ibSend.setOnClickListener(view -> {
            EditText etMessage = findViewById(R.id.etMessage);
            String msg = etMessage.getText().toString();
            etMessage.setText("");
            chatViewModel.add(msg, username);
        });


    }
}
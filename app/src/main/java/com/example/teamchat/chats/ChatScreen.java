package com.example.teamchat.chats;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.adapters.ChatListAdapter;
import com.example.teamchat.api.ChatApi;
import com.example.teamchat.entities.user.UserNoPass;
import com.example.teamchat.viewModels.ChatViewModel;

import java.util.concurrent.CompletableFuture;

public class ChatScreen extends AppCompatActivity {
    private Context context;
    private ChatViewModel chatViewModel;
    private Bitmap bitmap1;
    private  ChatListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
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


        ChatApi chatApi = new ChatApi(getApplicationContext(),authorizationHeader,1);
        CompletableFuture<UserNoPass> future = chatApi.onGetUserDetails(me);
        future.whenComplete((user, throwable) -> {
            if (user != null) {
                String myPic = user.getProfilePic();
                byte[] imageInBytes1 = Base64.decode(myPic, Base64.DEFAULT);
                 bitmap1 = BitmapFactory.decodeByteArray(imageInBytes1, 0, imageInBytes1.length);
            }
            adapter = new ChatListAdapter(this, me, bitmap, bitmap1);
            lvChat.setAdapter(adapter);
            chatViewModel.get().observe(this, adapter::setMessages);
        });




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


    @Override
    protected void onResume() {
        super.onResume();
    }
}
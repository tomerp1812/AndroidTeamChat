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

        String me = getIntent().getStringExtra("me");

        //Retrieve the username and profile picture fro the intent
        String username = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");

        int id = getIntent().getIntExtra("id", -1);

        chatViewModel = new ChatViewModel(context,authorizationHeader, id, me,username);
        RecyclerView lvChat = findViewById(R.id.lvChat);
        lvChat.setLayoutManager(new LinearLayoutManager(this));

        final ChatListAdapter adapter = new ChatListAdapter(this, me);
        lvChat.setAdapter(adapter);
        chatViewModel.get().observe(this, adapter::setMessages);

//        ImageView image_send_message = findViewById(R.id.image_send_message);
//        image_send_message.setImageURI(Uri.parse(profilePic));



        TextView tvContact = findViewById(R.id.tvContact);
        tvContact.setText(username);

        ImageView imContact = findViewById(R.id.imContact);
        byte[] imageInBytes = Base64.decode(profilePic, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageInBytes,0, imageInBytes.length);
       imContact.setImageBitmap(bitmap);

        ImageButton ibSend = findViewById(R.id.ibSend);
        ibSend.setOnClickListener(view ->{
            EditText etMessage = findViewById(R.id.etMessage);
            String msg = etMessage.getText().toString();
            etMessage.setText("");
            chatViewModel.add(msg, username);
        });


    }
}
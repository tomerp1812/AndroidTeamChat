package com.example.teamchat;

import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MessagingService extends FirebaseMessagingService {
    public MessagingService() {
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        SharedPreferences sharedPreferences = getSharedPreferences("firebaseTokenMap", MODE_PRIVATE);
        sharedPreferences.edit().putString("firebaseToken", token).apply();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
//        super.onMessageReceived(message);
        // Notify the current activity using a local broadcast
        // Access the notification body
        String notificationBody = Objects.requireNonNull(message.getNotification()).getBody();
        // Parse the JSON string into a JSONObject

        if(notificationBody!= null){
            JSONObject messageObj = null;
            try {
                messageObj = new JSONObject(notificationBody);
                // Extract the data into separate variables
                int id = messageObj.getInt("id");
                String created = messageObj.getString("created");
                String sender = messageObj.getString("sender");
                String content = messageObj.getString("content");
                Intent intent = new Intent("ACTION_MESSAGE_RECEIVED");
                intent.putExtra("id", id);
                intent.putExtra("created", created);
                intent.putExtra("sender", sender);
                intent.putExtra("content", content);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }



    }
}
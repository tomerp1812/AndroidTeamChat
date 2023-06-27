package com.example.teamchat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class MessagingService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "1";
    private HashMap<String, Integer> notificationMap;

    private Integer notificationId;

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
        if (message.getNotification() == null) {
            return;
        }
//        super.onMessageReceived(message);
        // Notify the current activity using a local broadcast
        // Access the notification body
        createNotificationChannel();
        String notificationBody = Objects.requireNonNull(message.getNotification()).getBody();

        // Parse the JSON string into a JSONObject

        if (notificationBody != null) {
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
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID).
                        setSmallIcon(R.drawable.baseline_chat_24).
                        setContentTitle(sender).
                        setContentText(content).
                        setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                //create map of sender : id for notifications.
                if (notificationMap == null) {
                    notificationMap = new HashMap<>();
                    notificationId = 1;
                }
                if (!notificationMap.containsKey(sender)) {
                    notificationMap.put(sender, notificationId);
                    notificationId++;
                }
                notificationManagerCompat.notify(notificationMap.get(sender), builder.build());
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }


    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            return;
        }
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}
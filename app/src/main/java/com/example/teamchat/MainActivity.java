package com.example.teamchat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.teamchat.Dao.Chat.ChatDB;
import com.example.teamchat.Dao.ContactDB;
import com.example.teamchat.Dao.Settings.SettingsDB;
import com.example.teamchat.Dao.Settings.SettingsDao;
import com.example.teamchat.api.userApi;
import com.example.teamchat.chats.ContactList;
import com.example.teamchat.entities.SettingsEntity;
import com.example.teamchat.entities.user.UserForLogin;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    private SettingsDB settingsDB;

    private SettingsDao settingsDao;

    private String firstLogin;
    private String etName;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        //setting DB
        this.settingsDB = SettingsDB.getInstance(getApplicationContext());
        this.settingsDao = settingsDB.settingsDao();


        if (firstLogin == null) {
            //check if room empty (happens only after first download of the app)
            CompletableFuture.supplyAsync(() -> settingsDao.index())
                    .thenAccept(settingsEntities -> {
                        if (settingsEntities.size() == 0) {
                            SettingsEntity settingsEntity = new SettingsEntity("http://10.0.2.2:5000/api/", false);
                            settingsDao.insert(settingsEntity);
                        } else {
                            if (settingsEntities.get(0).getNightMode()) {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            } else {
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            }
//                            Intent intent = new Intent(this, ContactList.class);
//                            // Pass the token to the next activity if needed
//                            intent.putExtra("me", settingsEntities.get(0).getUserConnected());
//                            intent.putExtra("token", settingsEntities.get(0).getAuthorizationHeader());
//                            startActivity(intent);
                        }
                    });
        }

        firstLogin = "";
        ImageButton settingsBtn = findViewById(R.id.settingsBtnInLogin);
        settingsBtn.setOnClickListener(v -> {
            Intent settingIntent = new Intent(this, Settings.class);
            startActivity(settingIntent);
        });

        Button btnLogin = findViewById(R.id.LoginButton);
        btnLogin.setOnClickListener(v -> {

//            TextView errorTextView = findViewById(R.id.errorTextView);
//            //string of name and password
//            EditText usernameEditText = findViewById(R.id.usernameEditTextInLogin);
//            EditText passwordEditText = findViewById(R.id.PasswordEditTextInLogin);
//            String edName = usernameEditText.getText().toString();
//            String edPassword = passwordEditText.getText().toString();
//
//            UserForLogin user = new UserForLogin(edName, edPassword);
//            userApi userApi = new userApi(context);
//
//            // Retrieve the Firebase token
//            FirebaseMessaging.getInstance().getToken()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful() && task.getResult() != null) {
//                            String fireBaseToken = task.getResult();
//                            CompletableFuture<ResponseBody> loginFuture = userApi.onLogin(fireBaseToken, user);
//                            loginFuture.thenAccept(responseBody -> {
//                                try {
//                                    String response = responseBody.string();
//                                    String authorizationHeader = "bearer " + response;
//                                    Intent i = new Intent(this, ContactList.class);
//                                    // Pass the token to the next activity if needed
//                                    i.putExtra("me", edName);
//                                    i.putExtra("token", authorizationHeader);
//                                    startActivity(i);
//                                    // token variable now contains the token string
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    errorTextView.setText("Invalid username or password");
//                                    errorTextView.setVisibility(View.VISIBLE);
//                                }
//                            }).exceptionally(ex -> {
//                                errorTextView.setText("Invalid username or password");
//                                errorTextView.setVisibility(View.VISIBLE);
//                                return null;
//                            });
//                        } else {
//                            // Handle the failure to retrieve the Firebase token
//                            Log.e("Firebase", "Failed to retrieve Firebase token: " + task.getException());
//                        }
//                    });

            Login();

        });


    }

    @SuppressLint("SetTextI18n")
    private void Login() {
        TextView errorTextView = findViewById(R.id.errorTextView);
        //string of name and password
        EditText usernameEditText = findViewById(R.id.usernameEditTextInLogin);
        EditText passwordEditText = findViewById(R.id.PasswordEditTextInLogin);
        String edName = usernameEditText.getText().toString();
        String edPassword = passwordEditText.getText().toString();
        this.etName = edName;
        UserForLogin user = new UserForLogin(edName, edPassword);
        userApi userApi = new userApi(context);
//         Retrieve the Firebase token
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        String fireBaseToken = task.getResult();
                        CompletableFuture<ResponseBody> loginFuture = userApi.onLogin(fireBaseToken, user);
                        loginFuture.thenAccept(responseBody -> {

                            try {
                                String response = responseBody.string();
                                String authorizationHeader = "bearer " + response;
                                //login successfully
                                //clear the DB if it is a new user login
                                List<SettingsEntity> settingsEntities = CompletableFuture.supplyAsync(() -> settingsDao.index())
                                        .join();
                                if (settingsEntities.get(0).getUserConnected() == null) {
                                    settingsEntities.get(0).setUserConnected(edName);
                                    settingsEntities.get(0).setAuthorizationHeader(authorizationHeader);
                                    settingsDao.update(settingsEntities.get(0));
                                } else if (!(settingsEntities.get(0).getUserConnected().equals(edName))) {
                                    //delete the DB-setting(default), contacts, messages
                                    settingsEntities.get(0).setNightMode(false);
                                    ContactDB.deleteDatabase(getApplicationContext());
                                    ChatDB.deleteDatabase(getApplicationContext());
                                    //update the new user connection
                                    settingsEntities.get(0).setUserConnected(edName);
                                    settingsEntities.get(0).setAuthorizationHeader(authorizationHeader);
                                    settingsDao.update(settingsEntities.get(0));
                                }


                                Intent i = new Intent(this, ContactList.class);
                                // Pass the token to the next activity if needed
                                i.putExtra("me", edName);
                                i.putExtra("token", authorizationHeader);
                                startActivity(i);
                                // token variable now contains the token string
                            } catch (Exception e) {
                                e.printStackTrace();
                                errorTextView.setText("Invalid username or password");
                                errorTextView.setVisibility(View.VISIBLE);
                            }
                        }).exceptionally(ex -> {
                            errorTextView.setText("Invalid username or password");
                            errorTextView.setVisibility(View.VISIBLE);
                            return null;
                        });
                    } else {
                        // Handle the failure to retrieve the Firebase token
                        Log.e("Firebase", "Failed to retrieve Firebase token: " + task.getException());
                    }
                });
    }

    public void navigateToRegistrationScreen(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btnLogin = findViewById(R.id.LoginButton);
        btnLogin.setOnClickListener(v -> {
            Login();
        });
    }
}
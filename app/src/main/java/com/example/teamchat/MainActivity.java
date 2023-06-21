package com.example.teamchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamchat.api.userApi;
import com.example.teamchat.chats.ContactList;
import com.example.teamchat.entities.user.UserForLogin;

import java.util.concurrent.CompletableFuture;

import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        Button btnLogin = findViewById(R.id.LoginButton);
        btnLogin.setOnClickListener(v -> {
            //string of name and password
            EditText usernameEditText = findViewById(R.id.usernameEditTextInLogin);
            EditText passwordEditText = findViewById(R.id.PasswordEditTextInLogin);
            String edName = usernameEditText.getText().toString();
            String edPassword = passwordEditText.getText().toString();

            UserForLogin user = new UserForLogin(edName, edPassword);
            userApi userApi = new userApi(context);
            CompletableFuture<ResponseBody> loginFuture = userApi.onLogin(user);
            loginFuture.thenAccept(responseBody -> {
                try {
                    String response = responseBody.string();
                    String authorizationHeader = "bearer " + response;
                    Intent i = new Intent(this, ContactList.class);
                    // Pass the token to the next activity if needed
                    i.putExtra("me", edName);
                    i.putExtra("token", authorizationHeader);
                    startActivity(i);
                    // token variable now contains the token string
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle JSON parsing error or response body reading error
                }
            }).exceptionally(ex -> {
                // Handle login failure
                // setErrorMessage("UserName or Password incorrect");...
                return null;
            });
        });

    }

    public void navigateToRegistrationScreen(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
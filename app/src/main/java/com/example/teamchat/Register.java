package com.example.teamchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.teamchat.api.userApi;

public class Register extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createTermsView();
        context = getApplicationContext();


        Button btnRegister = findViewById(R.id.RegisterButton);
        btnRegister.setOnClickListener(v -> {
            EditText usernameEditText = findViewById(R.id.usernameEditTextInRegister);
            EditText passwordEditText = findViewById(R.id.PasswordEditTextInRegister);
            EditText confirmPasswordEditText = findViewById(R.id.ConfirmPasswordEditText);
            EditText displayNameEditText = findViewById(R.id.DisplayNameEditText);
            ImageView profileImageView = findViewById(R.id.ProfileImageImageView);

            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();
            String displayName = displayNameEditText.getText().toString();
            String profilePic = profileImageView.toString();


            UserWithPass user = new UserWithPass(username, password, displayName, profilePic);
            userApi userApi = new userApi(context);
            userApi.onRegister(user);
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

    }

    public void navigateToLoginScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createTermsView() {
        //creating instances of the edit texts
        EditText editUserName = findViewById(R.id.usernameEditTextInRegister);
        EditText editPassword = findViewById(R.id.PasswordEditTextInRegister);
        EditText editDisplayName = findViewById(R.id.DisplayNameEditText);
        //creating instances of the under the Terms
        EditText underTermsPassword = findViewById(R.id.ConfirmPasswordEditText);
        LinearLayout underTermsDisplayName = findViewById(R.id.ProfileImageLinearLayout);
        //creating instances of the text Views
        TextView termsUserName = findViewById(R.id.usernameTerms);
        TextView termsPassword = findViewById(R.id.passwordTerms);
        TextView termsDisplayName = findViewById(R.id.displayNameTerms);
        //creating instances of the TermsWatcher to see if data has changed
        TermsWatcher usernameWatcher = new TermsWatcher(editUserName, termsUserName, editPassword);
        TermsWatcher passwordWatcher = new TermsWatcher(editPassword, termsPassword, underTermsPassword);
        TermsWatcher displayNameWatcher = new TermsWatcher(editDisplayName, termsDisplayName, underTermsDisplayName);
        //add a listener in case someone start to write something
        editUserName.setOnFocusChangeListener(usernameWatcher);
        editPassword.setOnFocusChangeListener(passwordWatcher);
        editDisplayName.setOnFocusChangeListener(displayNameWatcher);

    }
}
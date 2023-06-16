package com.example.teamchat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamchat.api.userApi;
import com.example.teamchat.entities.UserNoPass;
import com.example.teamchat.entities.UserWithPass;

import java.util.concurrent.CompletableFuture;

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

//            !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

//            if (user !2 - 8...){
//
//            }else if (!password.equals(confirmPassword)) {
//
//            } else if (password < 2 && password > 8...){
//
//            }else if (displayName ...){
//
//            }else if (profilePic ...){
//
//            }else{
                UserWithPass user = new UserWithPass(username, password, displayName, 123);
                userApi userApi = new userApi(context);

                CompletableFuture<UserNoPass> registrationFuture = userApi.onRegister(user);
                registrationFuture.whenComplete((registeredUser, throwable) -> {
                    if (registeredUser != null) {
                        navigateToLoginScreen();
                    } else {
//                        setErrorMessageForRegistrationFailure
                    }
                });



            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
        });
    }

    public void navigateToLoginScreen() {
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
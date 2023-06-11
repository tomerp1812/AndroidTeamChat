package com.example.teamchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.number.IntegerWidth;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createTermsView();




        Button btnRegister = findViewById(R.id.RegisterButton);
        btnRegister.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

    }

    public void navigateToLoginScreen(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void createTermsView(){
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
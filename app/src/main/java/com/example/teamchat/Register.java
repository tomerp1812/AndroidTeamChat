package com.example.teamchat;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamchat.api.userApi;
import com.example.teamchat.entities.user.UserNoPass;
import com.example.teamchat.entities.user.UserWithPass;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    public static Context context;
    private static final int GALLERY_REQUEST_CODE = 1;
    private ImageView profilePic;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        createTermsView();
        context = getApplicationContext();

        //move to login screen
        TextView moveToLogin = findViewById(R.id.toLoginTextViewInRegister);
        moveToLogin.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //clicking the button, a gallery will open to choose a profile picture
        profilePic = findViewById(R.id.ProfileImageImageView);
        LinearLayout profileImageLayout = findViewById(R.id.ProfileImageLinearLayout);
        profileImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


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

            TextView errorTextView = findViewById(R.id.errorTextView);


            String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(password);

            //check registration details
            if (username.length() < 2 || username.length() > 10) {
                errorTextView.setText("Invalid username");
                errorTextView.setVisibility(View.VISIBLE);
            } else if (!password.equals(confirmPassword)) {
                errorTextView.setText("Passwords do not match.");
                errorTextView.setVisibility(View.VISIBLE);
            } else if (!matcher.matches()) {
                errorTextView.setText("Invalid password.");
                errorTextView.setVisibility(View.VISIBLE);
            } else if (displayName.length() < 2 || displayName.length() > 10) {
                errorTextView.setText("Invalid displayName");
                errorTextView.setVisibility(View.VISIBLE);
            } else if (profilePic.length() < 1) {
                errorTextView.setText("Select picture");
                errorTextView.setVisibility(View.VISIBLE);
            } else {
                //if everything is correct - create the user in the server
                UserWithPass user = new UserWithPass(username, password, displayName, transferToBase64());
                userApi userApi = new userApi(context);

                CompletableFuture<UserNoPass> registrationFuture = userApi.onRegister(user);
                registrationFuture.whenComplete((registeredUser, throwable) -> {
                    if (registeredUser != null) {
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        errorTextView.setText("Failed to register");
                        errorTextView.setVisibility(View.VISIBLE);                    }
                });
            }
        });
    }

    // Method to open the gallery
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    public String transferToBase64() {
        InputStream inputStream = null;
        try {
            inputStream = getContentResolver().openInputStream(selectedImageUri);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while (true) {
            try {
                if ((bytesRead = inputStream.read(buffer)) == -1) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Get the selected image URI
            this.selectedImageUri = data.getData();

            // Set the image in the ImageView
            profilePic.setImageURI(selectedImageUri);
        }
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
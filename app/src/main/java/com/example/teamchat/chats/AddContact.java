package com.example.teamchat.chats;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamchat.R;
import com.example.teamchat.repositories.ContactRepository;

public class AddContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        // Retrieve the token from the intent
        String token = getIntent().getStringExtra("token");
        ContactRepository repository = ContactRepository.getRepository(this, token);
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            EditText username = findViewById(R.id.etContent);
            String user = username.getText().toString();
            repository.add(user);
            finish();
        });
    }
}
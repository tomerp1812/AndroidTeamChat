package com.example.teamchat.chats;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.teamchat.R;

public class AddContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ///DATABASE!!!!!
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            EditText username = findViewById(R.id.etContent);
//            username.getText().toString()
            //CHECK IF THE USERNAME IS IN THE DB AND ENTER IT

            finish();
        });
    }
}
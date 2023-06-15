package com.example.teamchat.chats;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.adapters.ContactListAdapter;
import com.example.teamchat.entities.Contact;
import com.example.teamchat.viewModels.ContactViewModel;

import java.util.ArrayList;

public class ContactList extends AppCompatActivity {
    private ContactViewModel viewModel;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        context = getApplicationContext();



        //check this!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        ArrayList<Contact> contacts = null;
        RecyclerView lvContactList = findViewById(R.id.lvContactList);
        final ContactListAdapter adapter = new ContactListAdapter(null, context);
        lvContactList.setAdapter(adapter);
        lvContactList.setClickable(true);
        lvContactList.setLayoutManager(new LinearLayoutManager(this));

        viewModel.get().observe(this,users -> {
            adapter.setContacts(users);
        });
    }
}
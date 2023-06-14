package com.example.teamchat.chats;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.adapters.ContactListAdapter;
import com.example.teamchat.viewModels.ContactViewModel;

public class ContactList extends AppCompatActivity {
    private ContactViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        RecyclerView lvContactList = findViewById(R.id.lvContactList);
        final ContactListAdapter adapter = new ContactListAdapter();
        lvContactList.setAdapter(adapter);
        lvContactList.setLayoutManager(new LinearLayoutManager(this));


    }
}
package com.example.teamchat.chats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.adapters.ContactListAdapter;
import com.example.teamchat.entities.contacts.Contact;
import com.example.teamchat.viewModels.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactList extends AppCompatActivity {
    private ContactViewModel viewModel;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);
        context = getApplicationContext();
        // Retrieve the token from the intent
        String me = getIntent().getStringExtra("me");
        String authorizationHeader = getIntent().getStringExtra("token");
        viewModel = new ContactViewModel(context, authorizationHeader);

        RecyclerView lvContactList = findViewById(R.id.lvContactList);
        lvContactList.setClickable(true);
        lvContactList.setLayoutManager(new LinearLayoutManager(this));

        final ContactListAdapter adapter = new ContactListAdapter(this);
        lvContactList.setAdapter(adapter);
        viewModel.get().observe(this, adapter::setContacts);



       ///// DATABASE!!!!!
        //add contact to the list
        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(view ->{
            Intent intent = new Intent(this, AddContact.class);
            intent.putExtra("me", me);
            intent.putExtra("token", authorizationHeader);
            startActivity(intent);
        });




        //search contacts!!!!!!!

        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                adapter.filterContacts("");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                String searchText = s.toString();
//                adapter.filterContacts(searchText);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String searchText = s.toString();
                adapter.filterContacts(searchText);
            }
        });





//        move to the chat screen
        lvContactList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View childView = rv.findChildViewUnder(e.getX(), e.getY());
                if (childView != null && e.getAction() == MotionEvent.ACTION_DOWN) {
                    int position = rv.getChildAdapterPosition(childView);
                    // Retrieve the item details at the clicked position
                    Contact data = adapter.getContactByPosition(position);

                    // Start a new activity and pass the item details
                    Intent intent = new Intent(context, ChatScreen.class);
                    String userName = data.getUser().getUsername();
                    String profilePicture = data.getUser().getProfilePic();
                    int id = data.getId();
//                    String lastMassage = data.getLastMsg().getContent();
//                    String time = data.getLastMsg().getCreated();
                    // Retrieve other fields if needed
                    intent.putExtra("userName", userName);
                    intent.putExtra("token", authorizationHeader);
                    intent.putExtra("id", id);
                    intent.putExtra("profilePic", profilePicture);
//                    intent.putExtra("lastMassage", lastMassage);
//                    intent.putExtra("time", time);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }


}
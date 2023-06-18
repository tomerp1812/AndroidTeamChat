package com.example.teamchat.chats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.R;
import com.example.teamchat.adapters.ContactListAdapter;
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
            intent.putExtra("token", authorizationHeader);
            startActivity(intent);
        });

        //move to the chat screen
//        lvContactList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//            @Override
//            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//                View childView = rv.findChildViewUnder(e.getX(), e.getY());
//                if (childView != null && e.getAction() == MotionEvent.ACTION_DOWN) {
//                    int position = rv.getChildAdapterPosition(childView);
//                    // Retrieve the item details at the clicked position
//                    Contact data = adapter.getItem(position);
//
//                    // Start a new activity and pass the item details
//                    Intent intent = new Intent(getApplicationContext(), ChatScreen.class);
//                    String userName = data.getUser().getUsername();
//                    int profilePicture = data.getUser().getProfilePic();
//                    String lastMassage = data.getLastMsg().getContent();
//                    String time = data.getLastMsg().getCreated();
//                    // Retrieve other fields if needed
//                    intent.putExtra("userName", userName);
//                    intent.putExtra("profilePicture", profilePicture);
//                    intent.putExtra("lastMassage", lastMassage);
//                    intent.putExtra("time", time);
//                    startActivity(intent);
//
//                    return true;
//                }
//                return false;
//            }
//            @Override
//            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
//
//            }
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
//
//            }
//        });
    }


}
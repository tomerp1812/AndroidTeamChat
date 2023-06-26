package com.example.teamchat.chats;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teamchat.Dao.Chat.ChatDB;
import com.example.teamchat.Dao.ContactDB;
import com.example.teamchat.Dao.Settings.SettingsDB;
import com.example.teamchat.MainActivity;
import com.example.teamchat.R;
import com.example.teamchat.Settings;
import com.example.teamchat.adapters.ContactListAdapter;
import com.example.teamchat.entities.contacts.Contact;
import com.example.teamchat.viewModels.ContactViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactList extends AppCompatActivity {
    private ContactViewModel viewModel;
    private Context context;

    private final BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getIntExtra("id", -1);
            String created = intent.getStringExtra("created");
            String sender = intent.getStringExtra("sender");
            String content = intent.getStringExtra("content");
            viewModel.onReceivedMessage(id, created, sender, content);

        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Add your logout functionality here
                SettingsDB.deleteDatabase(getApplicationContext());
                ContactDB.deleteDatabase(getApplicationContext());
                ChatDB.deleteDatabase(getApplicationContext());

                // move to the login activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        // Register the broadcast receiver
        IntentFilter intentFilter = new IntentFilter("ACTION_MESSAGE_RECEIVED");
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, intentFilter);

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
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddContact.class);
            intent.putExtra("me", me);
            intent.putExtra("token", authorizationHeader);
            startActivity(intent);
        });

        ///// Settings!!!!!
        ImageButton settingsBtn = findViewById(R.id.ibPreferences);
        settingsBtn.setOnClickListener(v -> {
            Intent settingIntent = new Intent(this, Settings.class);
            startActivity(settingIntent);
        });

        //search contacts!
        EditText etSearch = findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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

                    // Retrieve other fields if needed
                    intent.putExtra("userName", userName);
                    intent.putExtra("token", authorizationHeader);
                    intent.putExtra("id", id);
                    intent.putExtra("profilePic", profilePicture);
                    intent.putExtra("me", me);
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
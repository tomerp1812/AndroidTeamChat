package com.example.teamchat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.teamchat.Dao.Chat.ChatDB;
import com.example.teamchat.Dao.ContactDB;
import com.example.teamchat.Dao.Settings.SettingsDB;
import com.example.teamchat.Dao.Settings.SettingsDao;
import com.example.teamchat.entities.SettingsEntity;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.List;

public class Settings extends AppCompatActivity {

    private SettingsDB settingsDB;

    private SettingsDao settingsDao;

    private List<SettingsEntity> settingsEntity;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.settingsDB = SettingsDB.getInstance(getApplicationContext());
        this.settingsDao = settingsDB.settingsDao();

        //switch night/light mode
        SwitchMaterial switchBtn = findViewById(R.id.SwitchModeBtn);
        switchBtn.setOnClickListener(v -> {

            settingsEntity = settingsDao.index();
            settingsEntity.get(0).setNightMode(!settingsEntity.get(0).getNightMode()); // switch mode
            settingsDao.update(settingsEntity.get(0));
            if (settingsEntity.get(0).getNightMode()) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }

        });
        TextView tvSuccess = findViewById(R.id.successTextView);

        //switch url
        EditText urlText = findViewById(R.id.SettingsET);
        Button switchUrl = findViewById(R.id.SwitchUrlBtn);
        switchUrl.setOnClickListener(v -> {
            String input = urlText.getText().toString();
            if (!input.equals("")) {
                new Thread(() -> {
                    settingsEntity = settingsDao.index();
                    settingsEntity.get(0).setUrl(input + "/api/");
                    settingsDao.update(settingsEntity.get(0));
                }).start();
                tvSuccess.setText("URL changed successfully");
                tvSuccess.setVisibility(View.VISIBLE);
            }
        });


        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(view ->{
            logout();
        });

    }

    public void logout(){
        // delete all the DB from the app
        SettingsDB.deleteDatabase(getApplicationContext());
        ContactDB.deleteDatabase(getApplicationContext());
        ChatDB.deleteDatabase(getApplicationContext());

        // move to the login activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}
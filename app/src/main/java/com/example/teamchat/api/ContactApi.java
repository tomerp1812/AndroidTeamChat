package com.example.teamchat.api;

import android.content.Context;

import com.example.teamchat.Dao.Settings.SettingsDB;
import com.example.teamchat.Dao.Settings.SettingsDao;
import com.example.teamchat.entities.SettingsEntity;
import com.example.teamchat.entities.contacts.Contact;
import com.example.teamchat.entities.contacts.ContactNoMsg;
import com.example.teamchat.entities.user.Username;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApi {
    private Retrofit retrofit;
    private ContactApiService contactApiService;
    private Context context;
    private String authorizationHeader;
    private SettingsDB settingsDB;
    private SettingsDao settingsDao;
    private List<SettingsEntity> settingsEntityList;

    public ContactApi(Context context1, String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
        context = context1;
        this.settingsDB = SettingsDB.getInstance(context);
        this.settingsDao = settingsDB.settingsDao();
        this.settingsEntityList = settingsDao.index();
        String url = this.settingsEntityList.get(0).getUrl();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        contactApiService = retrofit.create(ContactApiService.class);
    }

    public void setAuthorizationHeader(String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
    }

    public String getAuthorizationHeader() {
        return authorizationHeader;
    }

    // get all the contact list  from the server
    public CompletableFuture<List<Contact>> onGetContactList() {
        CompletableFuture<List<Contact>> future = new CompletableFuture<>();
        Call<List<Contact>> call = contactApiService.getContacts(authorizationHeader);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    // add new contact to the server and return contactNoMsg object
    public CompletableFuture<ContactNoMsg> onAddContact(String username) {
        CompletableFuture<ContactNoMsg> future = new CompletableFuture<>();
        Username newUser = new Username(username);
        Call<ContactNoMsg> call = contactApiService.addContact(authorizationHeader, newUser);
        call.enqueue(new Callback<ContactNoMsg>() {
            @Override
            public void onResponse(Call<ContactNoMsg> call, Response<ContactNoMsg> response) {
                if (response.isSuccessful()) {
                    ContactNoMsg contactNoMsg = response.body();
                    future.complete(contactNoMsg); // Resolve the future with the ContactNoMsg object
                } else {
                    future.completeExceptionally(new Exception("Failed to add contact")); // Resolve the future with an exception
                }
            }
            @Override
            public void onFailure(Call<ContactNoMsg> call, Throwable t) {
                future.completeExceptionally(t); // Resolve the future with the failure exception
            }
        });

        return future;
    }

}

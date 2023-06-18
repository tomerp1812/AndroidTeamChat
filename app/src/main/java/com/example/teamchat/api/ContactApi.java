package com.example.teamchat.api;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.ContactDao;
import com.example.teamchat.R;
import com.example.teamchat.entities.contacts.Contact;
import com.example.teamchat.entities.messages.ContactNoMsg;
import com.example.teamchat.entities.user.Username;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApi {
    private ContactDao contactDao;
    private Retrofit retrofit;
    private ContactApiService contactApiService;

    private Context context;

    private String authorizationHeader;

    public ContactApi(Context context1, String authorizationHeader) {
        this.authorizationHeader = authorizationHeader;
        context = context1;
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.userUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        contactApiService = retrofit.create(ContactApiService.class);
    }

    public void onGetContactList(MutableLiveData<List<Contact>> contacts) {
        Call<List<Contact>> call = contactApiService.getContacts(authorizationHeader);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                Log.i("Response", response.body().toString());
                if (response.isSuccessful()) {
                    contacts.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.i("ResponseFail", t.toString());
            }
        });

    }


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

    public void onDeleteContact() {
        /////need to add id
        Call<Void> call = contactApiService.deleteContact(authorizationHeader,1);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}

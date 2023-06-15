package com.example.teamchat.api;

import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.ContactDao;
import com.example.teamchat.R;
import com.example.teamchat.TeamChat;
import com.example.teamchat.entities.Contact;
import com.example.teamchat.entities.ContactNoMsg;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContactApi {
    private ContactDao contactDao;
    private MutableLiveData<List<Contact>> contacts;
    private Retrofit retrofit;
    private ContactApiService contactApiService;

    public ContactApi(MutableLiveData<List<Contact>> contacts, ContactDao contactDao) {
        this.contacts = contacts;
        this.contactDao = contactDao;
        retrofit = new Retrofit.Builder()
                .baseUrl(TeamChat.context.getString(R.string.userUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        contactApiService = retrofit.create(ContactApiService.class);
    }

    public void onGetContactList(MutableLiveData<List<Contact>> contactListData) {
        Call<List<Contact>> call = contactApiService.getContacts();
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                new Thread(() -> {
                    contactListData.postValue(contactDao.index());

                }).start();
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {

            }
        });
    }


    public void onAddContact(String username) {
        Call<ContactNoMsg> call = contactApiService.addContact(username);
        call.enqueue(new Callback<ContactNoMsg>() {
            @Override
            public void onResponse(Call<ContactNoMsg> call, Response<ContactNoMsg> response) {

            }

            @Override
            public void onFailure(Call<ContactNoMsg> call, Throwable t) {

            }
        });
    }

    public void onDeleteContact() {
        /////need to add id
        Call<Void> call = contactApiService.deleteContact(1);
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

package com.example.teamchat.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.ContactDB;
import com.example.teamchat.Dao.ContactDao;
import com.example.teamchat.api.ContactApi;
import com.example.teamchat.entities.contacts.Contact;
import com.example.teamchat.entities.messages.ContactNoMsg;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ContactRepository {
    private ContactDao contactDao;
    private ContactApi contactApi;
    private ContactListData contactListData;

    private int id;

    private String authorizationHeader;

    public ContactRepository(Context context, String authorizationHeader) {
        id = 0;
        ContactDB db = ContactDB.getInstance(context);
        contactDao = db.contactDao();
        contactListData = new ContactListData();
        contactApi = new ContactApi(context, authorizationHeader);
        this.authorizationHeader = authorizationHeader;
//        UserNoPass userNoPass = new UserNoPass("tomer", "tomer", null);
//        LastMsg lastMsg = new LastMsg(1, "1", "hi");
//        Contact contact = new Contact(1,userNoPass, null);
//        new Thread(() -> {
//            contactDao.insert(contact);
//        }).start();

    }

    public String getToken() {
        return authorizationHeader;
    }

    public void setToken(String token) {
        this.authorizationHeader = authorizationHeader;
    }


    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void add(final String contact) {
        CompletableFuture<ContactNoMsg> future = contactApi.onAddContact(contact);

        future.thenAccept(contactNoMsg -> {
            Contact newContact = new Contact(id, contactNoMsg.getUserNoPass(), null);
            new Thread(() -> {
                contactDao.insert(newContact);
                contactListData.setValue(contactDao.index());
            }).start();
            id++;
        });
    }


//    public Contact getContact(int id){
//        return contactApi.onGetContactDetails(id);
//    }

//    public void delete(final Contact contact) {
//        contactApi.onDeleteContact();
//    }

    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            new Thread(() -> {
                List<Contact> contacts = contactDao.index();
                contactListData.postValue(contacts);
            }).start();

        }

        @Override
        protected void onActive() {
            super.onActive();
        }
    }
}


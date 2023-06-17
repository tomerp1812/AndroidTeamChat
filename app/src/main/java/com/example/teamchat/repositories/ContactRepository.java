package com.example.teamchat.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.ContactDB;
import com.example.teamchat.Dao.ContactDao;
import com.example.teamchat.api.ContactApi;
import com.example.teamchat.entities.contacts.Contact;
import com.example.teamchat.entities.messages.ContactNoMsg;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ContactRepository {
    private ContactDao contactDao;
    private ContactApi contactApi;
    private ContactListData contactListData;

    private String authorizationHeader;

    public ContactRepository(Context context, String authorizationHeader) {
        contactListData = new ContactListData();
        contactApi = new ContactApi(contactListData, context, authorizationHeader);
        ContactDB db = ContactDB.getInstance(context);
        contactDao = db.contactDao();
        this.authorizationHeader = authorizationHeader;
    }

    public String getToken() {
        return authorizationHeader;
    }

    public void setToken(String token) {
        this.authorizationHeader = authorizationHeader;
    }

    class ContactListData extends MutableLiveData<List<Contact>> {
        public ContactListData() {
            super();
            List<Contact> contacts = new LinkedList<>();
            ///here we need to add items to the list
            setValue(contacts);


        }

        @Override
        protected void onActive() {
            super.onActive();
            //get information from the DB
            new Thread(() -> contactListData.postValue(contactDao.index())).start();


            // Get information from the server
            new Thread(() -> {
                Future<List<Contact>> future = contactApi.onGetContactList();

                try {
                    contactListData.postValue(future.get());
                } catch (InterruptedException e) {
                    // Handle interrupted exception
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // Handle execution exception
                    e.printStackTrace();
                }
            }).start();

        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void add(final String contact) {
        CompletableFuture<ContactNoMsg> future = contactApi.onAddContact(contact);

        future.thenAccept(contactNoMsg -> {
            // The future has completed, and the contact data is available
            CompletableFuture<List<Contact>> indexFuture = CompletableFuture.supplyAsync(() -> contactDao.index());
            indexFuture.thenAccept(contacts -> {
                int id = contacts.size();
                Contact newContact = new Contact(id, contactNoMsg.getUserNoPass(), null);
                contactDao.insert(newContact);
            });
        }).exceptionally(throwable -> {
            // Handle exceptions occurred during the execution of the future
            return null;
        });
    }


//    public Contact getContact(int id){
//        return contactApi.onGetContactDetails(id);
//    }

    public void delete(final Contact contact) {
        contactApi.onDeleteContact();
    }
}


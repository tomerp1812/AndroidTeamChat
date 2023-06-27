package com.example.teamchat.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.ContactDB;
import com.example.teamchat.Dao.ContactDao;
import com.example.teamchat.api.ContactApi;
import com.example.teamchat.entities.contacts.Contact;
import com.example.teamchat.entities.contacts.ContactNoMsg;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ContactRepository {
    private ContactDao contactDao;
    private ContactApi contactApi;
    private ContactListData contactListData;
    private Context context;
    private ContactDB db;
    private static ContactRepository contactRepository;
    private String authorizationHeader;

    private ContactRepository(Context context, String authorizationHeader) {
        this.db = ContactDB.getInstance(context);
        this.contactDao = db.contactDao();
        this.contactListData = new ContactListData();
        this.contactApi = new ContactApi(context, authorizationHeader);
        this.authorizationHeader = authorizationHeader;
        this.context = context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setAuthorizationHeader(String authorizationHeader){
        this.authorizationHeader = authorizationHeader;
        contactApi.setAuthorizationHeader(authorizationHeader);
    }
    public static ContactRepository getRepository(Context context, String authorizationHeader) {
        if (contactRepository == null) {
            contactRepository = new ContactRepository(context, authorizationHeader);
        } else {
            contactRepository.setContext(context);
            contactRepository.setAuthorizationHeader(authorizationHeader);
        }
        return contactRepository;
    }

    // get all the contact list
    public LiveData<List<Contact>> getAll() {
        CompletableFuture.supplyAsync(() -> contactDao.index())
                .thenAccept(contactList -> contactListData.postValue(contactList));

        CompletableFuture<List<Contact>> future = contactApi.onGetContactList();
        future.thenAccept(contacts -> {
            new Thread(() -> {
                for (Contact contact: contacts) {
                    if(contactDao.get(contact.getId()) == null){
                        contactDao.insert(contact);
                    }else{
                        contactDao.update(contact);
                    }
                }
                contactListData.postValue(contactDao.index());
            }).start();
        });
        return contactListData;
    }

    // add new contact to user list
    public void add(final String contact) {
        CompletableFuture<ContactNoMsg> future = contactApi.onAddContact(contact);

        future.thenAccept(contactNoMsg -> {
            int id = contactNoMsg.getId();
            Contact newContact = new Contact(id ,contactNoMsg.getUserNoPass(), null);
            new Thread(() -> {
                contactDao.insert(newContact);
                List<Contact> contacts = contactDao.index();
                contactListData.postValue(contacts);
            }).start();
        });
    }

    public void receivedMessage(int id, String created, String sender, String content){
        new Thread(() -> {
            CompletableFuture.supplyAsync(() -> contactDao.index())
                    .thenAccept(contactList -> {
                        for (Contact contact: contactList) {
                            if(contact.getUser().getUsername().equals(sender)){
                                contact.getLastMessage().setId(id);
                                contact.getLastMessage().setCreated(created);
                                contact.getLastMessage().setContent(content);
                                contactDao.update(contact);
                                List<Contact> contacts = contactDao.index();
                                contactListData.postValue(contacts);
                            }
                        }
                    });
        }).start();
    }


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


package com.example.teamchat.repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.ContactDB;
import com.example.teamchat.Dao.ContactDao;
import com.example.teamchat.api.ContactApi;
import com.example.teamchat.entities.Contact;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private ContactApi contactApi;
    private ContactListData contactListData;

    public ContactRepository(Context context) {
        contactListData = new ContactListData();
        contactApi = new ContactApi(contactListData, contactDao, context);
        ContactDB db = ContactDB.getInstance(context);
        contactDao = db.contactDao();
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
            new Thread(() -> {
                contactListData.postValue(contactDao.index());
            }).start();
            ;


            contactApi.onGetContactList(this);
        }
    }

    public LiveData<List<Contact>> getAll() {
        return contactListData;
    }

    public void add(final String contact) {
        contactApi.onAddContact(contact);
        AsyncTask.execute(() -> contactDao.insert(contact));

    }

//    public Contact getContact(int id){
//        return contactApi.onGetContactDetails(id);
//    }

    public void delete(final Contact contact) {
        contactApi.onDeleteContact();
    }
}


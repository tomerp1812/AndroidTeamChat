package com.example.teamchat.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.ContactDao;
import com.example.teamchat.api.contactApi;
import com.example.teamchat.entities.Contact;

import java.util.LinkedList;
import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private  contactApi contactApi;
    private ContactListData contactListData;



    public ContactRepository(){
        contactListData= new ContactListData();
        contactApi= new contactApi();
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
            new Thread(()->{
                contactListData.postValue(contactDao.index());
            }).start();;


            contactApi contactApi = new contactApi();
            contactApi.onGetContactList(this);
        }
    }
public LiveData<List<Contact>> getAll(){return contactListData;}

    public void add(final Contact contact){
        contactApi.onAddContact(contact.getUsername());
    }

    public void delete(final Contact contact){
        contactApi.onDeleteContact();
    }
}


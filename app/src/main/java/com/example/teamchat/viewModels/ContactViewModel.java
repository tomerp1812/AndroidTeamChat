package com.example.teamchat.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.teamchat.entities.contacts.Contact;
import com.example.teamchat.repositories.ContactRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private ContactRepository repository;
    private LiveData<List<Contact>> contacts;
    private Context context;

    public ContactViewModel(Context context, String authorizationHeader) {
        repository = new ContactRepository(context, authorizationHeader);
        contacts = repository.getAll();
    }

    public LiveData<List<Contact>> get() {
        return repository.getAll();
        //return contacts
    }

    public void add(String contact) {
        repository.add(contact);
    }

//    public void delete(Contact contact) {
//        repository.delete(contact);
//    }

}

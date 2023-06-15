package com.example.teamchat.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.teamchat.entities.Contact;
import com.example.teamchat.repositories.ContactRepository;

import java.util.List;

public class ContactViewModel extends ViewModel {
    private ContactRepository repository;
    private LiveData<List<Contact>> contacts;
    private Context context;

    public ContactViewModel(Context context) {
        repository = new ContactRepository(context);
        contacts = repository.getAll();
    }

    public LiveData<List<Contact>> get() {
        return contacts;
    }

    public void add(Contact contact) {
        repository.add(contact);
    }

    public void delete(Contact contact) {
        repository.delete(contact);
    }

}

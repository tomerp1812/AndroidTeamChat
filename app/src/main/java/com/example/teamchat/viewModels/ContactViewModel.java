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
        repository = ContactRepository.getRepository(context, authorizationHeader);
        contacts = repository.getAll();
    }

    public ContactRepository getRepository() {
        return repository;
    }

    public LiveData<List<Contact>> get() {
        return repository.getAll();
    }


//    public void delete(Contact contact) {
//        repository.delete(contact);
//    }

}

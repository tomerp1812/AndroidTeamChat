package com.example.teamchat.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.teamchat.entities.messages.Message;
import com.example.teamchat.repositories.ChatRepository;

import java.util.List;

public class ChatViewModel extends ViewModel {

    private LiveData<List<Message>> messages;
    private ChatRepository chatRepository;

    private String me;
    private String contact;

    public ChatViewModel(Context context, String authorizationHeader, int id, String me, String contact) {
        chatRepository = new ChatRepository(context, authorizationHeader, id);
        this.me = me;
        this.contact = contact;
        messages = chatRepository.getAll(me, contact);
    }

    public LiveData<List<Message>> get() {
        return chatRepository.getAll(me, contact);
    }

    public void add(String msg, String receiver) {
        chatRepository.add(msg, receiver);
    }

//    public UserNoPass getUserDetail(String username){ return chatRepository.getUserDetails(username);}

}

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

    public ChatViewModel(Context context, String authorizationHeader){
        chatRepository = new ChatRepository(context,authorizationHeader);
        messages = chatRepository.getAll();
    }

    public LiveData<List<Message>> get(){return chatRepository.getAll();}
    public void add (String msg){ chatRepository.add(msg);}

}

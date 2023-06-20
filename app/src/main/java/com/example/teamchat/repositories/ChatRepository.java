package com.example.teamchat.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.Chat.ChatDao;
import com.example.teamchat.Dao.ContactDB;
import com.example.teamchat.api.ChatApi;
import com.example.teamchat.entities.messages.Message;
import com.example.teamchat.entities.messages.MessageReturn;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChatRepository {
    private ChatDao chatDao;
    private ChatApi chatApi;
    private ChatListData chatListData;
    private int id;
    private String authorizationHeader;


    public ChatRepository(Context context, String authorizationToken) {
        id = 0;
        ContactDB db = ContactDB.getInstance(context);
        chatDao = db.chatDao();
        chatListData = new ChatListData();
        chatApi = new ChatApi(context, authorizationToken);
        this.authorizationHeader = authorizationToken;
    }

    public void add(String msg) {
        CompletableFuture<MessageReturn> future = chatApi.addMessage(msg);
        future.thenAccept(message -> {
            new Thread(() -> {
                chatDao.insert(message);
            }).start();
        });

    }

    public LiveData<List<Message>> getAll() {
        return chatListData;
    }

    class ChatListData extends MutableLiveData<List<Message>> {
        public ChatListData() {
            super();
        }

        @Override
        protected void onActive() {
            super.onActive();

        }
    }
}

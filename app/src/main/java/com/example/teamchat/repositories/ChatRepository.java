package com.example.teamchat.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.Chat.ChatDB;
import com.example.teamchat.Dao.Chat.ChatDao;
import com.example.teamchat.api.ChatApi;
import com.example.teamchat.entities.messages.Message;
import com.example.teamchat.entities.user.UserNoPass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ChatRepository {
    private ChatDao chatDao;
    private ChatApi chatApi;
    private ChatListData chatListData;
    private int id;
    private String authorizationHeader;
    private ChatDB chatDB;

    private List<Message> sentMessages;

    public ChatRepository(Context context, String authorizationToken, int id) {
        this.sentMessages = new ArrayList<>();
        this.id = id;
        this.chatDB = ChatDB.getInstance(context);
        this.chatDao = chatDB.chatDao();
        this.chatListData = new ChatListData();
        this.chatApi = new ChatApi(context, authorizationToken, id);
        this.authorizationHeader = authorizationToken;
    }


    //get all the messages from the server and filter it for specific contact
    public LiveData<List<Message>> getAll(String receiver, String sender) {
        List<Message> filteredMessages = new ArrayList<>();
        CompletableFuture.supplyAsync(() -> chatDao.index())
                .thenAccept(chatList -> {
                    for (Message message : chatList) {
                        if ((message.getReceiver().equals(receiver) &&
                                message.getSender().getUsername().equals(sender))
                                || (message.getReceiver().equals(sender) &&
                                message.getSender().getUsername().equals(receiver))) {
                            filteredMessages.add(message);
                        }
                    }
                    chatListData.postValue(filteredMessages); //messages for specific contact
                });

        CompletableFuture<List<Message>> future = chatApi.onGetMessages();
        future.thenAccept(messages -> {
            for (Message message : messages) {
                if (message.getSender().getUsername().equals(sender)) {
                    message.setReceiver(receiver);
                } else {
                    message.setReceiver(sender);
                }
            }
            chatListData.postValue(messages);

            //update room
            new Thread(() -> {
                for (Message message : messages) {
                    if (chatDao.getMessageById(message.getId()) == null) {
                        chatDao.insert(message);
                    }
                }
            }).start();
            sentMessages.clear();
            sentMessages.addAll(messages);
        });
        return chatListData;
    }

    //add new message to the db and server
    public void add(String msg, String receiver) {
        CompletableFuture<Message> future = chatApi.onAddMessage(msg);
        future.thenAccept(message -> {
            message.setReceiver(receiver);
            new Thread(() -> {
                chatDao.insert(message);
                sentMessages.add(message);
                chatListData.postValue(sentMessages);
            }).start();
        });

    }

    public void receivedMessage(int id, String created, String sender, String content) {
        new Thread(() -> {
            CompletableFuture<UserNoPass> future = chatApi.onGetUserDetails(sender);
            future.thenAccept(userNoPass -> {
                Message message = new Message(id, created, userNoPass, content);
                new Thread(() -> {
                    chatDao.insert(message);
                    sentMessages.add(message);
                    chatListData.postValue(sentMessages);
                }).start();
            });
        }).start();
    }



    class ChatListData extends MutableLiveData<List<Message>> {
        public ChatListData() {
            super();
            new Thread(() -> {
                List<Message> messages = chatDao.index();
                chatListData.postValue(messages);
            }).start();
        }

        @Override
        protected void onActive() {
            super.onActive();

        }
    }
}

package com.example.teamchat.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.Dao.ChatDao;
import com.example.teamchat.api.chatApi;
import com.example.teamchat.entities.Message;

import java.util.LinkedList;
import java.util.List;

public class ChatRepository {
    private ChatDao chatDao;
    private chatApi chatApi;
    private ChatListData chatListData;

    public ChatRepository(Context context){
        chatListData= new ChatListData();
        chatApi = new chatApi(context);
    }

    class ChatListData extends MutableLiveData<List<Message>>{
        public ChatListData(){
            super();
            List<Message> messages = new LinkedList<>();
            ///here we need to add items to the list
            setValue(messages);
        }

        @Override
        protected void onActive() {
            super.onActive();


            //get information from the DB
            new Thread(()->{
//                chatListData.postValue(....);
            }).start();;


//            chatApi chatApi = new chatApi();
            chatApi.onGetMessages(this);
        }
    }
}

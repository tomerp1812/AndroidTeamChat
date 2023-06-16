package com.example.teamchat.api;

import com.example.teamchat.entities.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface chatsApiService {

    //TOKEN!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages();

    @POST("Chats/{id}/Messages")
    Call<Message> addMessage();
}

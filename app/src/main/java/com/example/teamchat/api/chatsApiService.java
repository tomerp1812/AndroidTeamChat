package com.example.teamchat.api;

import com.example.teamchat.entities.messages.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface chatsApiService {

    //TOKEN!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Header("Authorization") String authorization);

    @POST("Chats/{id}/Messages")
    Call<Message> addMessage(@Header("Authorization") String authorization);
}

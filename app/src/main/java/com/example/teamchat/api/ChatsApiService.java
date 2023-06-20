package com.example.teamchat.api;

import com.example.teamchat.entities.messages.Message;
import com.example.teamchat.entities.messages.MessageReturn;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ChatsApiService {

    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Header("Authorization") String authorization);

    @POST("Chats/{id}/Messages")
    Call<MessageReturn> addMessage(@Header("Authorization") String authorization, @Body String msg);
}

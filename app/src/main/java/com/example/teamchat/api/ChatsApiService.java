package com.example.teamchat.api;

import com.example.teamchat.entities.messages.Message;
import com.example.teamchat.entities.messages.MessageString;
import com.example.teamchat.entities.user.UserNoPass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ChatsApiService {
    @GET("Users/{username}")
    Call<UserNoPass> getUserDetails(@Header("Authorization") String authorization, @Path("username") String username);
    @GET("Chats/{id}/Messages")
    Call<List<Message>> getMessages(@Header("Authorization") String authorization, @Path("id") int id);

    @POST("Chats/{id}/Messages")
    Call<Message> addMessage(@Header("Authorization") String authorization, @Path("id") int id , @Body MessageString msg);
}

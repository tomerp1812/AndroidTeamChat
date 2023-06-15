package com.example.teamchat.api;

import com.example.teamchat.entities.Contact;
import com.example.teamchat.entities.ContactNoMsg;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface contactApiService {
    //////////TOKEN/////////
    @GET("Chats")
    Call<List<Contact>> getContacts();

    @POST("Chats")
     Call<ContactNoMsg> addContact(@Body String username);

    @DELETE("Chats/{id}")
     Call < Void> deleteContact(@Path("id") int id);
}

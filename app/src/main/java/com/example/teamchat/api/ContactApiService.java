package com.example.teamchat.api;

import com.example.teamchat.entities.contacts.Contact;
import com.example.teamchat.entities.messages.ContactNoMsg;
import com.example.teamchat.entities.user.Username;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ContactApiService {
    //////////TOKEN/////////
    @GET("Chats")
    Call<List<Contact>> getContacts(@Header("Authorization") String authorization);

    @POST("Chats")
    Call<ContactNoMsg> addContact(@Header("Authorization") String authorization, @Body Username username);

    @DELETE("Chats/{id}")
    Call<Void> deleteContact(@Header("Authorization") String authorization, @Path("id") int id);
}

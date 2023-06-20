package com.example.teamchat.api;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.R;
import com.example.teamchat.entities.messages.Message;
import com.example.teamchat.entities.messages.MessageReturn;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatApi {

    private Retrofit retrofit;
    private ChatsApiService chatsApiService;

    private String authorizationToken;

    public ChatApi(Context context, String authorizationToken) {
        this.authorizationToken = authorizationToken;
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.userUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatsApiService = retrofit.create(ChatsApiService.class);
    }

    public void onGetMessages(MutableLiveData<List<Message>> messageListData) {
        Call<List<Message>> call = chatsApiService.getMessages(authorizationToken);

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    public CompletableFuture<MessageReturn> addMessage(String message) {
        CompletableFuture<MessageReturn> completableFuture = new CompletableFuture<>();

        // Make the API call to add the message
        Call<MessageReturn> call = chatsApiService.addMessage(authorizationToken, message);
        call.enqueue(new Callback<MessageReturn>() {
            @Override
            public void onResponse(Call<MessageReturn> call, Response<MessageReturn> response) {
                if (response.isSuccessful()) {
                    // API request succeeded
                    MessageReturn addedMessage = response.body();
                    completableFuture.complete(addedMessage);
                } else {
                    // API request failed
                    completableFuture.completeExceptionally(new Exception("API request failed"));
                }
            }

            @Override
            public void onFailure(Call<MessageReturn> call, Throwable t) {
                // Handle network or other errors
                completableFuture.completeExceptionally(t);
            }
        });

        return completableFuture;
    }
}


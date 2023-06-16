package com.example.teamchat.api;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.teamchat.R;
import com.example.teamchat.entities.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class chatApi {

    private Retrofit retrofit;
    private chatsApiService chatsApiService;

    public chatApi(Context context){
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.userUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatsApiService = retrofit.create(chatsApiService.class);
    }

    public void onGetMessages(MutableLiveData<List<Message>> messageListData){
        Call<List<Message>> call = chatsApiService.getMessages();

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    public void onAddMessage(MutableLiveData<Message> messageMutableLiveData){
        Call<Message> call = chatsApiService.addMessage();

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }
}

package com.example.teamchat.api;

import android.content.Context;

import com.example.teamchat.R;
import com.example.teamchat.entities.user.UserForLogin;
import com.example.teamchat.entities.user.UserNoPass;
import com.example.teamchat.entities.user.UserWithPass;

import java.util.concurrent.CompletableFuture;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class userApi {
    Retrofit retrofit;
    UsersApiService usersApiService;

    public userApi(Context context){
        retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.userUrl)).
                addConverterFactory(GsonConverterFactory.create()).build();
        usersApiService = retrofit.create(UsersApiService.class);
    }

    public CompletableFuture<UserNoPass> onRegister(UserWithPass user) {
        CompletableFuture<UserNoPass> future = new CompletableFuture<>();

        Call<UserNoPass> call = usersApiService.Register(user);
        call.enqueue(new Callback<UserNoPass>() {
            @Override
            public void onResponse(Call<UserNoPass> call, Response<UserNoPass> response) {
                if (response.isSuccessful()) {
                    UserNoPass user = response.body();
                    future.complete(user); // Complete the future with the success result
                } else {
                    future.completeExceptionally(new RuntimeException("Registration failed")); // Complete the future exceptionally
                }
            }

            @Override
            public void onFailure(Call<UserNoPass> call, Throwable t) {
                future.completeExceptionally(t); // Complete the future exceptionally
            }
        });

        return future;
    }



    public CompletableFuture<ResponseBody> onLogin(UserForLogin user) {
        CompletableFuture<ResponseBody> future = new CompletableFuture<>();

        Call<ResponseBody> call = usersApiService.Login(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body()); // Complete the future with the response body
                } else {
                    future.completeExceptionally(new RuntimeException("Login failed")); // Complete the future exceptionally
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                future.completeExceptionally(t); // Complete the future exceptionally
            }
        });

        return future;
    }

}

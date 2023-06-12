package com.example.teamchat.api;

import android.content.Context;

import com.example.teamchat.MainActivity;
import com.example.teamchat.R;
import com.example.teamchat.UserForLogin;
import com.example.teamchat.UserNoPass;
import com.example.teamchat.UserWithPass;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class userApi {
    Retrofit retrofit;
    usersApiService usersApiService;

    public userApi(Context context){
        retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.userUrl)).
                addConverterFactory(GsonConverterFactory.create()).build();
        usersApiService = retrofit.create(usersApiService.class);
    }

    public void onRegister(UserWithPass user){
        Call<UserNoPass> call = usersApiService.Register(user);
        call.enqueue(new Callback<UserNoPass>() {
            @Override
            public void onResponse(Call<UserNoPass> call, Response<UserNoPass> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<UserNoPass> call, Throwable t) {

            }
        });

    }

    public void onLogin(UserForLogin user){
        Call<ResponseBody> call = usersApiService.Login(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}

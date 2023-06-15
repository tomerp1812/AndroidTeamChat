package com.example.teamchat.api;

import com.example.teamchat.entities.UserForLogin;
import com.example.teamchat.entities.UserNoPass;
import com.example.teamchat.entities.UserWithPass;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;


public interface UsersApiService {

    @POST("Users")
    Call<UserNoPass> Register(@Body UserWithPass userWithPass);

    @POST("Tokens")
    Call<ResponseBody> Login(@Body UserForLogin user);
}

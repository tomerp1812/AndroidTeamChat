package com.example.teamchat.api;

import com.example.teamchat.entities.user.UserForLogin;
import com.example.teamchat.entities.user.UserNoPass;
import com.example.teamchat.entities.user.UserWithPass;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Body;


public interface UsersApiService {

    @POST("Users")
    Call<UserNoPass> Register(@Body UserWithPass userWithPass);

    @POST("Tokens")
    Call<ResponseBody> Login(@Header("FireBaseToken") String fireBaseToken, @Body UserForLogin user);
}

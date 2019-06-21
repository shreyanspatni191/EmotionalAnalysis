package com.example.emotionalanalysis;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginApi {
    @POST("loginUser.php")
    Call<LoginResponse> login(@Query("email") String email, @Query("password") String password);
}



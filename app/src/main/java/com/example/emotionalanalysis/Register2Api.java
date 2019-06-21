package com.example.emotionalanalysis;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Register2Api {
    @POST("registerUser.php")
    Call<Register2Response> register(@Query("username")String username, @Query("email")String email, @Query("password")String password,
                                     @Query("firstname")String firstname, @Query("middlename")String middlename,
                                     @Query("lastname")String lastname, @Query("age")String age, @Query("gender")String gender,
                                     @Query("weight")String weight, @Query("height")String height, @Query("country")String country);
}

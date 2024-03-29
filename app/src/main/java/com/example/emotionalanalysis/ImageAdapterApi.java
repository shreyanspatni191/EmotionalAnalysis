package com.example.emotionalanalysis;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ImageAdapterApi {
    @POST("testImageInfo.php")
    Call<ImageAdapterResponse> testImage(@Query("email")String email, @Query("starttime")String imgStartTime,
                                         @Query("endtime")String imgEndTime, @Query("imagename") String imagename);
}

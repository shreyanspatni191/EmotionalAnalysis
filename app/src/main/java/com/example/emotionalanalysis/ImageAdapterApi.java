package com.example.emotionalanalysis;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ImageAdapterApi {
    @POST("imageAdapter.php")
    Call<ImageAdapterResponse> testImage(@Query("imageName") String imageName);
}

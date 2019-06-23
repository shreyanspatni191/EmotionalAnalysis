package com.example.emotionalanalysis;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SessionApi {
    @POST("testInfo.php")
    Call<SessionResponse> testInfo( @Query("email")String email, @Query("starttime")String starttime,
                                   @Query("endtime")String endtime, @Query("testplace")String testplace,
                                   @Query("testplacedescription")String testplacedescription, @Query("testambience")String testambience,
                                    @Query("watchno")String watchno);
}

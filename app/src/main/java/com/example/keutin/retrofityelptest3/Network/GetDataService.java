package com.example.keutin.retrofityelptest3.Network;

import com.example.keutin.retrofityelptest3.BuildConfig;
import com.example.keutin.retrofityelptest3.Model.YelpAnswersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("businesses/search")
    Call<YelpAnswersResponse> getBusinessData(@Header("Authorization") String ApiKey,
                                              @Query("term") String term,
                                              @Query("location") String location,
                                              @Query("limit") String limit);

}

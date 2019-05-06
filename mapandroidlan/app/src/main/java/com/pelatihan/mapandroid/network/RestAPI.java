package com.pelatihan.mapandroid.network;

import com.pelatihan.mapandroid.model.ResponseMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestAPI {

    @GET("json")
    Call<ResponseMap> getRute(
            @Query("origin") String lokasiawal,
            @Query("destination") String lokasitujuan,
            @Query("key") String key

    );

}

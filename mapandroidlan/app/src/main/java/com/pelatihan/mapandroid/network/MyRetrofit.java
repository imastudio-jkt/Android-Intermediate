package com.pelatihan.mapandroid.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pelatihan.mapandroid.helper.MyConstant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//untuk menggunakan retrofit di semua class yang menggunakannya
public class MyRetrofit {

    //inizialisasi sebuah library untuk accesss data dari server
    //library yang digunakan adalah retrofit dari square

    public static Retrofit setInit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder().baseUrl(MyConstant.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }
    public static RestAPI getInstaceRetrofit(){

        return setInit().create(RestAPI.class);


    }
}

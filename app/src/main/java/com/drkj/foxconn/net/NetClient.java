package com.drkj.foxconn.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ganlong on 2018/1/24.
 */

public class NetClient {
    private static NetClient instance;

    private Retrofit retrofit;

    private NetClient(){
        Gson gson = new GsonBuilder().create();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(ConstantUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    public static NetClient getInstance(){
        if (instance==null)
            instance = new NetClient();
        return instance;
    }

    public ServerApi getApi(){
        return retrofit.create(ServerApi.class);
    }
}

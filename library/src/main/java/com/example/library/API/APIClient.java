package com.example.library.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://othertanpage51.conveyor.cloud/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}

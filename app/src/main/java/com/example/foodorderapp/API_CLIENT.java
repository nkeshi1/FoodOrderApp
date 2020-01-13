package com.example.foodorderapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_CLIENT{
//    private static String BASE_URL = "http://10.0.2.2:81/FoodOrderApp/";
    private static String BASE_URL = "http://192.168.43.142:81/FoodOrderApp/";

    private static Retrofit retrofit = null;

    public static Retrofit getApiClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}

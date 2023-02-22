package com.example.TicketingRestApi.ticket.restapi.service;

import java.util.concurrent.TimeUnit;

import com.example.TicketingRestApi.ticket.restapi.api.PayGoApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PayGoApiService {
    private static Retrofit getRetrofit()
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(45, TimeUnit.SECONDS)
                .connectTimeout(45, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();
        //192.168.100.28
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("  https://dssl-payment-gateway.digitalpaygo.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    public static PayGoApi getPaymentApiService()
    {
        PayGoApi paymentInterface = getRetrofit().create( PayGoApi.class);
        return  paymentInterface;
    }
}

package com.example.shuttlemobile.util;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();

    /**
     * Create a HTTP Interceptor which injects the following headers:
     * Content-Type: application/json
     * Authorization: Bearer [jwt]
     *
     * The JWT will be added only if it exists.
     * Use this in Service classes when instantiating the Retrofit object
     * through a builder.
     * @return An OkHttpClient with an interceptor for jwt.
     */
    public static OkHttpClient basicJsonJwtClient() {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Content-Type", "application/json");

                final String token = SettingsUtil.get(SettingsUtil.KEY_ACCESS_TOKEN, "");
                if (!token.equals("")) {
                    requestBuilder.header("Authorization", "Bearer " + token);
                }

                return chain.proceed(requestBuilder.build());
            }
        }).build();
        return client;
    }
}

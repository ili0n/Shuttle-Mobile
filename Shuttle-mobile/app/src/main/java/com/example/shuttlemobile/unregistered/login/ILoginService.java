package com.example.shuttlemobile.unregistered.login;

import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ILoginService {
    @POST("/api/user/login")
    Call<TokenDTO> getUser(@Body LoginDTO loginDTO);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();
    ILoginService service = retrofit.create(ILoginService.class);
}

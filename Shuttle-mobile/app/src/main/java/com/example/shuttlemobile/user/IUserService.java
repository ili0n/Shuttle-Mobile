package com.example.shuttlemobile.user;

import com.example.shuttlemobile.driver.IDriverService;
import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;
import com.example.shuttlemobile.vehicle.VehicleDTO;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IUserService {
    @GET("/api/user/{id}/active")
    Call<Boolean> getActive(@Path("id") Long id);

    @PUT("/api/user/{id}/active")
    Call<Boolean> setActive(@Path("id") Long id);

    @PUT("/api/user/{id}/inactive")
    Call<Boolean> setInactive(@Path("id") Long id);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();
    IUserService service = retrofit.create(IUserService.class);
}

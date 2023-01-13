package com.example.shuttlemobile.passenger.orderride;

import com.example.shuttlemobile.ride.CreateRideDTO;
import com.example.shuttlemobile.ride.RideDTO;
import com.example.shuttlemobile.unregistered.login.LoginDTO;
import com.example.shuttlemobile.unregistered.login.TokenDTO;
import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ICreateRideService {

    @POST("/api/ride")
    Call<RideDTO> postRide(@Body CreateRideDTO createRideDTO);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();
    ICreateRideService service = retrofit.create(ICreateRideService.class);
}

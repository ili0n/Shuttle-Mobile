package com.example.shuttlemobile.driver;

import com.example.shuttlemobile.ride.IRideService;
import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;
import com.example.shuttlemobile.vehicle.VehicleDTO;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IDriverService {
    @GET("/api/driver/{id}/vehicle")
    Call<VehicleDTO> getVehicle(@Path("id") Long driverId);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();
    IDriverService service = retrofit.create(IDriverService.class);
}

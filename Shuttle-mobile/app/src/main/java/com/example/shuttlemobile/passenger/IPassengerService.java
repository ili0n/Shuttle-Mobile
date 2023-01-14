package com.example.shuttlemobile.passenger;

import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.vehicle.VehicleDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPassengerService {
    @GET("/api/passenger/{id}")
    Call<PassengerDTO> getPassenger(@Path("id") Long passengerId);
    IPassengerService service = RetrofitUtils.retrofit.create(IPassengerService.class);
}

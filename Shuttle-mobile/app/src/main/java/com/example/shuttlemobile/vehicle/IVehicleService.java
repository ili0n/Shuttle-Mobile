package com.example.shuttlemobile.vehicle;

import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.ride.dto.VehicleDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IVehicleService {
    @GET("/api/driver/{driverId}/vehicle")
    Call<VehicleDTO> getDriverLocation(@Path("driverId") long driverId);
}

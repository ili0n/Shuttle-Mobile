package com.example.shuttlemobile.vehicle;

import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.ride.dto.VehicleDTO;
import com.example.shuttlemobile.ride.dto.VehicleLocationDTO;
import com.example.shuttlemobile.util.RetrofitUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IVehicleService {
    @GET("/api/vehicle/active")
    Call<List<VehicleLocationDTO>> getDriversLocation();
    IVehicleService service = RetrofitUtils.retrofit.create(IVehicleService.class);
}

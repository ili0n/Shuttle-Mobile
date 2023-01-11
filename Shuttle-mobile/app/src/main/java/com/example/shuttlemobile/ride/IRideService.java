package com.example.shuttlemobile.ride;

import com.example.shuttlemobile.ride.dto.RideDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRideService {
    @GET("/api/ride/driver/{driverId}/active")
    Call<RideDTO> getActiveRide(@Path("driverId") long driverId);

    @PUT("/api/ride/{rideId}/end")
    Call<RideDTO> endRide(@Path("rideId") long rideId);


}

package com.example.shuttlemobile.ride;

import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.util.RetrofitUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import com.example.shuttlemobile.unregistered.login.ILoginService;
import com.example.shuttlemobile.unregistered.login.LoginDTO;
import com.example.shuttlemobile.unregistered.login.TokenDTO;
import com.example.shuttlemobile.util.RetrofitUtils;
import com.example.shuttlemobile.util.Utils;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRideService {
    @GET("/api/ride/driver/{driverId}/active")
    Call<RideDTO> getActiveRide(@Path("driverId") long driverId);

    @PUT("/api/ride/{rideId}/end")
    Call<RideDTO> endRide(@Path("rideId") long rideId);

    @GET("/api/ride/passenger/{id}/active")
    Call<RideDTO> getActiveRidePassenger(@Path("id") Long passengerId);

    @GET("/api/ride/driver/{id}/active")
    Call<RideDTO> getActiveRideDriver(@Path("id") Long driverId);

    //@PUT("/api/ride/{id}/cancel")
    //Call<RideDTO> rejectRide(@Path("id") Long rideId, @Body RejectionDTOMinimal rejectionDTOMinimal);
    @PUT("/api/ride/{id}/cancel")
    Call<RideDTO> rejectRide(@Path("id") Long rideId, @Body RejectionDTOMinimal rejectionDTOMinimal);

    @PUT("/api/ride/{id}/accept")
    Call<RideDTO> acceptRide(@Path("id") Long rideId);

    IRideService service = RetrofitUtils.retrofit.create(IRideService.class);
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();
    IRideService service = retrofit.create(IRideService.class);
}

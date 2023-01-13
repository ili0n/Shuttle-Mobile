package com.example.shuttlemobile.ride;

import com.example.shuttlemobile.ride.dto.PanicDTO;
import com.example.shuttlemobile.ride.dto.RejectionDTOMinimal;
import com.example.shuttlemobile.ride.dto.ReviewDTO;
import com.example.shuttlemobile.ride.dto.ReviewSendDTO;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.util.RetrofitUtils;

import retrofit2.Call;
import retrofit2.http.GET;

import com.example.shuttlemobile.util.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IRideService {
    @GET("/api/ride/{id}")
    Call<RideDTO> getById(@Path("id") long rideId);

    @GET("/api/ride/driver/{driverId}/active")
    Call<RideDTO> getActiveRide(@Path("driverId") long driverId);

    @PUT("/api/ride/{rideId}/end")
    Call<RideDTO> endRide(@Path("rideId") long rideId);

    @GET("/api/ride/passenger/{id}/active")
    Call<RideDTO> getActiveRidePassenger(@Path("id") Long passengerId);

    @GET("/api/ride/driver/{id}/active")
    Call<RideDTO> getActiveRideDriver(@Path("id") Long driverId);

    @PUT("/api/ride/{id}/cancel")
    Call<RideDTO> rejectRide(@Path("id") Long rideId, @Body RejectionDTOMinimal rejectionDTOMinimal);

    @PUT("/api/ride/{id}/accept")
    Call<RideDTO> acceptRide(@Path("id") Long rideId);

    @PUT("/api/ride/{id}/panic")
    Call<RideDTO> panicRide(@Path("id") Long rideId, @Body PanicDTO panicDTO);

    @POST("/api/review/{rideId}/vehicle")
    Call<ReviewDTO> leaveReviewVehicle(@Path("rideId") Long rideId, @Body ReviewSendDTO review);

    @POST("/api/review/{rideId}/driver")
    Call<ReviewDTO> leaveReviewDriver(@Path("rideId") Long rideId, @Body ReviewSendDTO review);

    IRideService service = RetrofitUtils.retrofit.create(IRideService.class);
}

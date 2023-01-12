package com.example.shuttlemobile.ride;

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
    @GET("/api/ride/passenger/{id}/active")
    Call<RideDTO> getActiveRidePassenger(@Path("id") Long passengerId);

    @GET("/api/ride/driver/{id}/active")
    Call<RideDTO> getActiveRideDriver(@Path("id") Long driverId);

    @PUT("/api/ride/{id}/cancel")
    Call<RideDTO> rejectRide(@Path("id") Long rideId, @Body RejectionDTOMinimal rejectionDTOMinimal);

    @PUT("/api/ride/{id}/accept")
    Call<RideDTO> acceptRide(@Path("id") Long rideId);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.ServerOrigin)
            .addConverterFactory(GsonConverterFactory.create())
            .client(RetrofitUtils.basicJsonJwtClient())
            .build();
    IRideService service = retrofit.create(IRideService.class);
}

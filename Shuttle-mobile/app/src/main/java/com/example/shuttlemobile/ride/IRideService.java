package com.example.shuttlemobile.ride;

import com.example.shuttlemobile.ride.dto.RideDTO;
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
    Call<RideDTO> getRide(@Path("driverId") long driverId);

    @PUT("/api/ride/{rideId}/end")
    Call<RideDTO> endRide(@Path("rideId") long rideId);


}

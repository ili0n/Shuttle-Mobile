package com.example.shuttlemobile.passenger.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shuttlemobile.FavoriteDialog;
import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.common.UserChatActivity;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.driver.DriverDTO;
import com.example.shuttlemobile.driver.IDriverService;
import com.example.shuttlemobile.driver.fragments.DriverHistory;
import com.example.shuttlemobile.message.Message;
import com.example.shuttlemobile.passenger.orderride.OrderActivity;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.route.LocationDTO;
import com.example.shuttlemobile.user.dto.UserEmailDTO;
import com.example.shuttlemobile.util.Utils;
import com.example.shuttlemobile.vehicle.IVehicleService;
import com.example.shuttlemobile.vehicle.VehicleDTO;
import com.mapbox.geojson.Point;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerHistoryDetails extends GenericUserMapFragment {
    private RideDTO ride;
    private DriverDTO driver;
    private static final String RIDE_KEY = "ride";

    private TextView txtRouteFrom;
    private TextView txtRouteTo;
    private ImageButton btnFavorite;
    private Button btnOrderAgain;
    private ImageView imgDriver;
    private TextView txtDriverFullName;
    private TextView txtVehicle;
    private Button btnChat;
    private TextView txtDistace;
    private TextView txtPrice;
    private TextView txtDate;
    private TextView txtTime;
    private VehicleDTO vehicle;
    private ListView lvPassengers;

    public static PassengerHistoryDetails newInstance(RideDTO ride) {
        PassengerHistoryDetails fragment = new PassengerHistoryDetails();
        Bundle args = new Bundle();
        args.putSerializable(RIDE_KEY, ride);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.ride = (RideDTO) getArguments().getSerializable(RIDE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passenger_history_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final LocationDTO A_loc = ride.getLocations().get(0).getDeparture();
        final LocationDTO B_loc = ride.getLocations().get(ride.getLocations().size() - 1).getDestination();
        Point a = Point.fromLngLat(A_loc.getLongitude(), A_loc.getLatitude());
        Point b = Point.fromLngLat(B_loc.getLongitude(), B_loc.getLatitude());

        drawRoute(a, b, "#FF0000");
        lookAtPoint(a, 10, 1000);
        initViews(view);
        fillData();
    }

    private void fillData() {
//        basic ride data
        final LocationDTO A_loc = ride.getLocations().get(0).getDeparture();
        final LocationDTO B_loc = ride.getLocations().get(ride.getLocations().size() - 1).getDestination();
        txtRouteFrom.setText(A_loc.getAddress());
        txtRouteTo.setText(B_loc.getAddress());
        txtDistace.setText(ride.getTotalLength().toString() + "km");
        txtPrice.setText(ride.getTotalCost().toString() + "RSD");

//        Date time data
        LocalDateTime endTime = LocalDateTime.parse(ride.getEndTime());
        LocalDateTime startTime = LocalDateTime.parse(ride.getStartTime());
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        txtDate.setText(dateFormatter.format(endTime));
        txtTime.setText(timeFormatter.format(startTime) + " - " + timeFormatter.format(endTime));

//        button listeners
        btnOrderAgain.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), OrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(OrderActivity.KEY_ROUTE, ride.getLocations().get(0));
            bundle.putSerializable(OrderActivity.KEY_DIST, ride.getTotalLength());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        btnFavorite.setOnClickListener(view1 -> {
            DialogFragment favoriteDialog = FavoriteDialog.newInstance(ride);
            favoriteDialog.show(getChildFragmentManager(), "favorite");
        });

        lvPassengers.setAdapter(new EasyListAdapter<UserEmailDTO>() {
            @Override
            public List<UserEmailDTO> getList() {
                return ride.getPassengers();
            }

            @Override
            public LayoutInflater getLayoutInflater() {
                return PassengerHistoryDetails.this.getLayoutInflater();
            }

            @Override
            public void applyToView(View view, UserEmailDTO passenger) {
                TextView txtPassenger = view.findViewById(R.id.txt_p_history_p_name);
                txtPassenger.setText(passenger.getEmail());

            }

            @Override
            public int getListItemLayoutId() {
                return R.layout.list_p_history_passengers;
            }
        });

//        driver fetching and filling data
        Call<DriverDTO> call = IDriverService.service.getDriver(ride.getDriver().getId());
        call.enqueue(new Callback<DriverDTO>() {
            @Override
            public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
                if(response.isSuccessful()){
                    driver = response.body();
                    if(driver != null){
                        fillDriverData(driver);
                    }
                }
            }

            @Override
            public void onFailure(Call<DriverDTO> call, Throwable t) {

            }
        });
    }

    private void fillDriverData(DriverDTO driver) {
        imgDriver.setImageBitmap(Utils.getImageFromBase64(driver.getProfilePicture()));
        txtDriverFullName.setText(driver.getName() + " " + driver.getSurname());
        btnChat.setOnClickListener(view -> {
            openSMS(driver.getTelephoneNumber());
        });

        Call<VehicleDTO> call = IDriverService.service.getVehicle(driver.getId());
        call.enqueue(new Callback<VehicleDTO>() {
            @Override
            public void onResponse(Call<VehicleDTO> call, Response<VehicleDTO> response) {
                if(response.isSuccessful()){
                    vehicle = response.body();
                    if(vehicle != null){
                        txtVehicle.setText(vehicle.getModel());
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleDTO> call, Throwable t) {

            }
        });
    }

    private void openSMS(String number) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.setData(Uri.parse("sms:" + number));
        startActivity(sendIntent);
    }

    private void initViews(View view) {
        txtRouteFrom = view.findViewById(R.id.txt_p_ride_routeA);
        txtRouteTo = view.findViewById(R.id.txt_p_ride_routeB);
        btnOrderAgain = view.findViewById(R.id.bnt_p_ride_again);
        btnFavorite = view.findViewById(R.id.btn_p_ride_favorite);

        imgDriver =  view.findViewById(R.id.img_p_ride_dpfp);
        txtDriverFullName = view.findViewById(R.id.txt_p_ride_dname);
        txtVehicle = view.findViewById(R.id.txt_p_ride_dcar);
        btnChat = view.findViewById(R.id.btn_p_ride_dchat);
        txtDistace = view.findViewById(R.id.txt_p_ride_distance);
        txtPrice = view.findViewById(R.id.txt_p_ride_price);
        txtDate = view.findViewById(R.id.txt_p_ride_date);
        txtTime = view.findViewById(R.id.txt_p_ride_tinterval);
        lvPassengers = view.findViewById(R.id.li_p_ride_passengers);

    }

    @Override
    public String getPublicMapApiToken() {
        return requireActivity().getResources().getString(R.string.mapbox_access_token);
    }

    @Override
    public void onMapLoaded() {

    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_passenger_history_details;
    }

    @Override
    public int getMapViewID() {
        return R.id.p_details_map;
    }

    @Override
    public void onNewLocation(Location location) {

    }

}
package com.example.shuttlemobile.driver.subactivities;

import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.route.LocationDTO;
import com.example.shuttlemobile.user.UserEmailDTO;
import com.mapbox.geojson.Point;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DriverHistoryDetails extends GenericUserMapFragment {
    private RideDTO ride;
    private Point A, B;

    public void setRide(RideDTO dto) {
        this.ride = dto;
    }

    public static DriverHistoryDetails newInstance(RideDTO ride) {
        DriverHistoryDetails dhd = new DriverHistoryDetails();
        dhd.setRide(ride);
        return dhd;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final LocationDTO A_loc = ride.getLocations().get(0).getDeparture();
        final LocationDTO B_loc = ride.getLocations().get(ride.getLocations().size() - 1).getDestination();
        A = Point.fromLngLat(A_loc.getLongitude(), A_loc.getLatitude());
        B = Point.fromLngLat(B_loc.getLongitude(), B_loc.getLatitude());

        drawRoute(A, B, "#FF0000");
        fitViewport(A, B, 3000); // TODO: What?

        initViewElements(view);
    }

    @Override
    public String getPublicMapApiToken() {
        return getActivity().getResources().getString(R.string.mapbox_access_token);
    }

    @Override
    public void onMapLoaded() {
    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_driver_history_details;
    }


    @Override
    public int getMapViewID() {
        return R.id.map_driver_history_details;
    }

    @Override
    public void onNewLocation(Location location) {

    }


    private void initViewElements(View view) {
        TextView txtStart = view.findViewById(R.id.txt_dhd_start);
        TextView txtEnd = view.findViewById(R.id.txt_dhd_end);
        TextView txtDist = view.findViewById(R.id.txt_dhd_dist);
        TextView txtPrice = view.findViewById(R.id.txt_dhd_price);
        TextView txtA = view.findViewById(R.id.txt_dhd_A);
        TextView txtB = view.findViewById(R.id.txt_dhd_B);

        txtA.setText(ride.getLocations().get(0).getDeparture().getAddress());
        txtB.setText(ride.getLocations().get(ride.getLocations().size() - 1).getDestination().getAddress());

        if (ride.getStartTime() == null) {
            txtStart.setText("Ride did not begin.");
        } else {
            txtStart.setText(LocalDateTime.parse(ride.getStartTime()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        }

        if (ride.getEndTime() == null) {
            txtEnd.setText("Ride did not finish properly.");
        } else {
            txtEnd.setText(LocalDateTime.parse(ride.getEndTime()).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        }

        //txtDist.setText((ride.getTotalLength() / 1000.0) + "km");
        txtPrice.setText(ride.getTotalCost() + " RSD");

        initPassengerList(view);
    }

    private void initPassengerList(View view) {
        ListView liPassengers = view.findViewById(R.id.list_dhd_passengers);

        liPassengers.setAdapter(new EasyListAdapter<UserEmailDTO>() {
            @Override
            public List<UserEmailDTO> getList() {
                return ride.getPassengers();
            }

            @Override
            public LayoutInflater getLayoutInflater() {
                return DriverHistoryDetails.this.getLayoutInflater();
            }

            @Override
            public void applyToView(View view, UserEmailDTO obj) {
                TextView txtName = view.findViewById(R.id.txt_dhdp_name);
                RatingBar ratingDriver = view.findViewById(R.id.rating_dhdp_driver);
                RatingBar ratingVehicle = view.findViewById(R.id.rating_dhdp_vehicle);
                TextView txtCommentDriver = view.findViewById(R.id.txt_dhdp_driver_comment);
                TextView txtCommentVehicle = view.findViewById(R.id.txt_dhdp_vehicle_comment);

                txtName.setText(obj.getEmail());
            }

            @Override
            public int getListItemLayoutId() {
                return R.layout.list_dhd_passenger;
            }
        });
    }
}
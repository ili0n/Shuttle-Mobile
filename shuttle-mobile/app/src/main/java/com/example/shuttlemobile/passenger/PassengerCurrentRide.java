package com.example.shuttlemobile.passenger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

public class PassengerCurrentRide extends Fragment {

    private MapView map;

    public PassengerCurrentRide() {
        // Required empty public constructor
    }

    public static PassengerCurrentRide newInstance() {
        PassengerCurrentRide fragment = new PassengerCurrentRide();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_passenger_current_ride, null);

        map = (MapView) root.findViewById(R.id.currentRideMap);
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
//        map.setHorizontalMapRepetitionEnabled(false);
//        map.setVerticalMapRepetitionEnabled(false);
//        IMapController mapController = map.getController();
//        mapController.setZoom(4f);
//        map.setMinZoomLevel(4.0);
        return root;


    }
}
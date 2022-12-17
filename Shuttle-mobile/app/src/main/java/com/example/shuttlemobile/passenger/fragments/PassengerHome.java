package com.example.shuttlemobile.passenger.fragments;

import static com.mapbox.core.constants.Constants.PRECISION_6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.GenericUserMapFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.LegStep;
import com.mapbox.api.directions.v5.models.RouteLeg;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Geometry;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.CircleAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.viewannotation.ViewAnnotationManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerHome extends GenericUserMapFragment {
    private MapView mapView;

    public static PassengerHome newInstance(SessionContext session) {
        PassengerHome fragment = new PassengerHome();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onMapLoaded() {
        drawCar(Point.fromLngLat(0, 0), true);
        drawCar(Point.fromLngLat(3, 0), false);

        List<Point> points = new ArrayList<>();
        points.add(Point.fromLngLat(19.80613, 45.23673));
        points.add(Point.fromLngLat(19.80057, 45.24089));

        MapboxDirections client = MapboxDirections.builder()
                .accessToken(getResources().getString(R.string.mapbox_access_token))
                .routeOptions(RouteOptions.builder()
                                .coordinatesList(points)
                                .profile(DirectionsCriteria.PROFILE_DRIVING)
                                .overview(DirectionsCriteria.OVERVIEW_FULL)
                        .build())
                .build();

        final DirectionsRoute[] currentRoute = new DirectionsRoute[1];

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response.body() == null) {
                    Log.e("", "No routes found, make sure you set the right user and access token.");
                    return;
                } else if (response.body().routes().size() < 1) {
                    Log.e("", "No routes found");
                    return;
                }

                currentRoute[0] = response.body().routes().get(0);
                Feature directionsRouteFeature = Feature.fromGeometry(LineString.fromPolyline(currentRoute[0].geometry(), PRECISION_6));

                //Log.e(":)", currentRoute[0].distance().toString());

                // je LineString

                Log.e(":)", directionsRouteFeature.geometry().type());

                // ((LineString)(directionsRouteFeature.geometry())).coordinates();
                drawPolylineToMap(((LineString)(directionsRouteFeature.geometry())).coordinates(), "#4e3c0");

                List<Point> routePoints = new ArrayList<>();
                for (RouteLeg l : currentRoute[0].legs()) {
                    for (LegStep s : l.steps()) {
                        routePoints.add(s.maneuver().location());
                    }
                }

                drawPolylineToMap(routePoints, "#4e3c0");
            }

            @Override public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                Log.e("", "Error: " + throwable.getMessage());
            }
        });


        drawCircleToMap(points.get(0), 8.0, "#ff0088");
        drawCircleToMap(points.get(1), 8.0, "#00ff88");
        //drawPolylineToMap(points, "#4e3c0");


    }

    @Override
    public int getLayoutID() {
        return R.layout.fragment_passenger_home;
    }

    @Override
    public int getMapViewID() {
        return R.id.mapView;
    }
}
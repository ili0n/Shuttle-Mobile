package com.example.shuttlemobile.common;

import static com.mapbox.core.constants.Constants.PRECISION_6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.util.Utils;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
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
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class GenericUserMapFragment extends GenericUserFragment {
    private MapView mapView;
    private AnnotationPlugin annotationApi;
    private CircleAnnotationManager circleAnnotationManager;
    private PointAnnotationManager pointAnnotationManager;
    private PolylineAnnotationManager polylineAnnotationManager;
    private Bitmap carAvailable;
    private Bitmap carUnavailable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = getActivity().findViewById(getMapViewID());

        initMapAPI();
        initIcons();

        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                onMapLoaded();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    public abstract String getPublicMapApiToken();

    public abstract void onMapLoaded();

    public abstract int getLayoutID();

    public abstract int getMapViewID();

    private void initMapAPI() {
        annotationApi = AnnotationPluginImplKt.getAnnotations(mapView);
        circleAnnotationManager = CircleAnnotationManagerKt.createCircleAnnotationManager(annotationApi, new AnnotationConfig());
        pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationApi, new AnnotationConfig());
        polylineAnnotationManager = PolylineAnnotationManagerKt.createPolylineAnnotationManager(annotationApi, new AnnotationConfig());
    }

    private void initIcons() {
        carAvailable = Utils.getBitmapFromVectorDrawable(getActivity(), R.drawable.car_green);
        carUnavailable = Utils.getBitmapFromVectorDrawable(getActivity(), R.drawable.car_red);
    }


    public final void drawCar(Point pos, boolean available) {
        if (available) {
            drawAnnotationToMap(pos, carAvailable);
        } else {
            drawAnnotationToMap(pos, carUnavailable);
        }
    }

    public final void drawCircleToMap(Point point, Double radius, String hexColor) {
        CircleAnnotationOptions circleAnnotationOptions = new CircleAnnotationOptions()
                .withPoint(point)
                .withCircleRadius(radius)
                .withCircleColor(hexColor)
                .withCircleStrokeWidth(2.0)
                .withCircleStrokeColor("#ffffff")
        ;
        circleAnnotationManager.create(circleAnnotationOptions);
    }

    public final void drawAnnotationToMap(Point point, Bitmap image) {
        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(point)
                .withIconImage(image)
        ;
        pointAnnotationManager.create(pointAnnotationOptions);
    }

    public final void drawPolylineToMap(List<Point> points, String hexColor) {
        PolylineAnnotationOptions polylineAnnotationOptions = new PolylineAnnotationOptions()
                .withPoints(points)
                .withLineWidth(6.0)
                .withLineColor(hexColor);
        ;
        polylineAnnotationManager.create(polylineAnnotationOptions);
    }

    public final void drawRoute(Point A, Point B, String hexColor) {
        final List<Point> points = Arrays.asList(A, B);

        final MapboxDirections client = MapboxDirections.builder()
                .accessToken(getPublicMapApiToken())
                .routeOptions(RouteOptions.builder()
                        .coordinatesList(points)
                        .profile(DirectionsCriteria.PROFILE_DRIVING)
                        .overview(DirectionsCriteria.OVERVIEW_FULL)
                        .build())
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                drawRouteOnResponse(call, response, hexColor);
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {
                // Failed to call.
            }
        });
    }

    private void drawRouteOnResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response, String hexColor) {
        if (response.body() == null) {
            // No response.
            return;
        }

        final List<DirectionsRoute> routes = response.body().routes();

        if (routes.size() == 0) {
            // No routes.
            return;
        }

        // We only draw the first route since we don't need alternative routes.

        final DirectionsRoute route = routes.get(0);
        final Feature routeFeature = Feature.fromGeometry(LineString.fromPolyline(route.geometry(), PRECISION_6));
        final LineString routeGeometry = (LineString)(routeFeature.geometry());

        final List<Point> routePoints = routeGeometry.coordinates();
        final Point A = routePoints.get(0);
        final Point B = routePoints.get(routePoints.size() - 1);

        drawPolylineToMap(routePoints, hexColor);
    }
}
package com.example.shuttlemobile.passenger.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.passenger.dto.FavoriteRouteDTO;
import com.example.shuttlemobile.ride.IRideService;
import com.example.shuttlemobile.route.RouteDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerAccountFavorites extends GenericUserFragment {
    private ListView lvFavorites;
    private final long passengerId = 2;


    public static PassengerAccountFavorites newInstance(SessionContext session) {
        PassengerAccountFavorites fragment = new PassengerAccountFavorites();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_passenger_account_favorites, container, false);
        initViews(v);
        fillData();
        return v;
    }

    private void fillData() {
        Call<List<FavoriteRouteDTO>> call = IRideService.service.getFavoriteRidesByPassenger(passengerId);
        call.enqueue(new Callback<List<FavoriteRouteDTO>>() {
            @Override
            public void onResponse(Call<List<FavoriteRouteDTO>> call, Response<List<FavoriteRouteDTO>> response) {
                if(response.isSuccessful()){
                    List<FavoriteRouteDTO> favorites = response.body();
                    setFavoriteRoutes(favorites);
                }
                else {
                    Toast.makeText(requireContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<FavoriteRouteDTO>> call, Throwable t) {
                Toast.makeText(requireContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setFavoriteRoutes(List<FavoriteRouteDTO> favorites) {
        lvFavorites.setAdapter(new EasyListAdapter<FavoriteRouteDTO>() {
            @Override
            public List<FavoriteRouteDTO> getList() {
                return favorites;
            }

            @Override
            public LayoutInflater getLayoutInflater() {
                return PassengerAccountFavorites.this.getLayoutInflater();
            }

            @Override
            public void applyToView(View view, FavoriteRouteDTO favoriteRoute) {
                TextView tvName = view.findViewById(R.id.tv_p_fav_name);
                TextView tvRouteA = view.findViewById(R.id.list_p_favorites_route_A);
                TextView tvRouteB = view.findViewById(R.id.list_p_favorites_route_B);

                tvName.setText(favoriteRoute.getFavoriteName());
                List<RouteDTO> locations = favoriteRoute.getLocations();
                tvRouteA.setText(locations.get(0).getDeparture().getAddress());
                tvRouteB.setText(locations.get(locations.size() - 1).getDestination().getAddress());

            }

            @Override
            public long getItemId(int i) {
                return getList().get(i).getId();
            }

            @Override
            public int getListItemLayoutId() {
                return R.layout.list_p_favorites;
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        deleteMarkedFavorites();

    }

    private void deleteMarkedFavorites() {
        for(int  i = 0; i < lvFavorites.getAdapter().getCount() - 1; ++i){
            View v = lvFavorites.getChildAt(i);
            CheckBox cb = v.findViewById(R.id.cb_p_fav_star);
            if(!cb.isChecked()){
                FavoriteRouteDTO routeToDelete = (FavoriteRouteDTO) lvFavorites.getAdapter().getItem(i);
                deleteFavorite(routeToDelete);
            }
        }
    }

    private void deleteFavorite(FavoriteRouteDTO favoriteRoute) {
        Call<Void> call = IRideService.service.deleteFavoriteRoute(favoriteRoute.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(requireContext(), "Failed removing favorite route", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed removing favorite route", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initViews(View v) {
        lvFavorites = v.findViewById(R.id.lv_p_favorites);
    }


}
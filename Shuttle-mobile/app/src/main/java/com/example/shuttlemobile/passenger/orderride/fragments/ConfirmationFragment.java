package com.example.shuttlemobile.passenger.orderride.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.driver.fragments.DriverHistory;
import com.example.shuttlemobile.passenger.orderride.ICreateRideService;
import com.example.shuttlemobile.passenger.orderride.OrderActivity;
import com.example.shuttlemobile.ride.CreateRideDTO;
import com.example.shuttlemobile.ride.RideDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmationFragment extends Fragment {
    private Button confirm;

    public static ConfirmationFragment newInstance(SessionContext session) {
        ConfirmationFragment fragment = new ConfirmationFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        confirm = (Button) view.findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateRideDTO createRideDTO =((OrderActivity) getActivity()).getCreateRideDTO();
                Call<RideDTO> call = ICreateRideService.service.postRide(createRideDTO);
                call.enqueue(new Callback<RideDTO>() {
                    @Override
                    public void onResponse(Call<RideDTO> call, Response<RideDTO> response) {
                        if(response.code() == 200)
                            Toast.makeText(getContext(), "Ride created", Toast.LENGTH_LONG).show();
                        else {
                            Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RideDTO> call, Throwable t) {
                        Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                });
                getActivity().finish();

            }
        });
    }

    public Button getConfirmButton(){
        return confirm;
    }
}
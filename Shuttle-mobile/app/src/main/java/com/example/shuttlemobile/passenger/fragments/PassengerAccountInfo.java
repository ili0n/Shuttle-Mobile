package com.example.shuttlemobile.passenger.fragments;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.passenger.IPassengerService;
import com.example.shuttlemobile.passenger.dto.PassengerDTO;
import com.example.shuttlemobile.user.JWT;
import com.example.shuttlemobile.util.JWTDecoder;
import com.example.shuttlemobile.util.SettingsUtil;
import com.example.shuttlemobile.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment for viewing and updating account info for the passenger of this session.
 * Only certain fields are editable, while others must be changed in the web application.
 */
public class PassengerAccountInfo extends GenericUserFragment {
    private EditText editName;
    private EditText editSurname;
    private EditText editAddress;
    private EditText editPhone;
    private ImageButton editPfp;
    private Button btnSubmit;

    public static PassengerAccountInfo newInstance() {
        PassengerAccountInfo fragment = new PassengerAccountInfo();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passenger_account_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        editName = getActivity().findViewById(R.id.txt_p_info_name);
        editSurname = getActivity().findViewById(R.id.txt_p_info_surname);
        editAddress = getActivity().findViewById(R.id.txt_p_info_address);
        editPhone = getActivity().findViewById(R.id.txt_p_info_phone);
        editPfp = getActivity().findViewById(R.id.img_p_info_pfp);
        btnSubmit = getActivity().findViewById(R.id.btn_p_info_info_submit);

        editPfp.setOnClickListener(view -> {
            // TODO: Select image from storage.
        });

        fillData();

        // TODO: If all the input fields are the same as the current user data, disable the button.
        // You have to use listeners for each edit text.
        boolean canSubmit = true;
        btnSubmit.setActivated(canSubmit);
        btnSubmit.setOnClickListener(view -> pushChanges());
    }

    private void fillData(){
        long passengerId = SettingsUtil.getUserJWT().getId();
        Call<PassengerDTO> call = IPassengerService.service.getPassenger(passengerId);
        call.enqueue(new Callback<PassengerDTO>() {
            @Override
            public void onResponse(Call<PassengerDTO> call, Response<PassengerDTO> response) {
                if(response.isSuccessful()){
                    PassengerDTO passenger = response.body();
                    editPfp.setImageBitmap(Utils.getImageFromBase64(passenger.getProfilePicture()));
                    editName.setText(passenger.getName());
                    editSurname.setText(passenger.getSurname());
                    editAddress.setText(passenger.getAddress());
                    editPhone.setText(passenger.getTelephoneNumber());
                }
            }

            @Override
            public void onFailure(Call<PassengerDTO> call, Throwable t) {

            }
        });
    }

    private void pushChanges() {

    }
}
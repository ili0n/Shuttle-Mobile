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

public class PassengerAccountInfo extends GenericUserFragment {
    public static PassengerAccountInfo newInstance(SessionContext session) {
        PassengerAccountInfo fragment = new PassengerAccountInfo();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
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
    }

    private void initView() {
        EditText editName = getActivity().findViewById(R.id.txt_p_info_name);
        EditText editSurname = getActivity().findViewById(R.id.txt_p_info_surname);
        EditText editAddress = getActivity().findViewById(R.id.txt_p_info_address);
        EditText editPhone = getActivity().findViewById(R.id.txt_p_info_phone);
        ImageButton editPfp = getActivity().findViewById(R.id.img_p_info_pfp);
        Button btnSubmit = getActivity().findViewById(R.id.btn_p_info_info_submit);

        editPfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Select image from storage.
            }
        });


        String name = editName.getText().toString();
        String surname = editName.getText().toString();
        String address = editAddress.getText().toString();
        String phone = editPhone.getText().toString();
        // Image pfp = editPfp.

        // TODO: If all the input fields are the same as the current user data, disable the button.
        boolean canSubmit = true;
        btnSubmit.setActivated(canSubmit);
        btnSubmit.setOnClickListener(view -> pushChanges());
    }

    private void pushChanges() {

    }
}
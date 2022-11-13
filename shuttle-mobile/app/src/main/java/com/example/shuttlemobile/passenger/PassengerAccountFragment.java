package com.example.shuttlemobile.passenger;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.route.FavouriteRoutesActivity;

public class PassengerAccountFragment extends Fragment {
    private ImageView addressEditImage;
    private ImageView nameEditImage;
    private ImageView surnameEditImage;
    private ImageView phoneEditImage;
    private ImageView emailEditImage;
    private Button btnSavePassenger;
    private Button btnFavouriteRoutes;
    private Button btnReports;

    private Passenger passenger = new Passenger(123, "Pera", "Peric",
            "0642314", "pera@gmail.com", "perica123", false,
            "Sime simica 10");
    private EditText etAddress;
    private EditText etName;
    private EditText etSurname;
    private EditText etPhone;
    private EditText etEmail;

    public static PassengerAccountFragment newInstance() {
        return new PassengerAccountFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.titleAccount);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passenger_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        initImageViews(view);
        initEditTexts(view);
        setPassenger(view);
        setListeners(view);
    }


    private void setListeners(View view) {
        addressEditImage.setOnClickListener(new EditImageListener(view, R.id.addressValue));
        nameEditImage.setOnClickListener(new EditImageListener(view, R.id.nameValue));
        surnameEditImage.setOnClickListener(new EditImageListener(view, R.id.surnameValue));
        phoneEditImage.setOnClickListener(new EditImageListener(view, R.id.phoneValue));
        emailEditImage.setOnClickListener(new EditImageListener(view, R.id.emailValue));

        btnSavePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btn) {
                passenger.setAddress(etAddress.getText().toString());
                passenger.setEmail(etEmail.getText().toString());
                passenger.setName(etName.getText().toString());
                passenger.setPhone(etPhone.getText().toString());
                passenger.setSurname(etSurname.getText().toString());

                etAddress.setEnabled(false);
                etName.setEnabled(false);
                etSurname.setEnabled(false);
                etEmail.setEnabled(false);
                etPhone.setEnabled(false);

                btnSavePassenger.setVisibility(View.INVISIBLE);
            }
        });

        btnFavouriteRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), FavouriteRoutesActivity.class));
            }
        });
    }

    private void setPassenger(View view) {
        etAddress.setText(passenger.getAddress());
        etName.setText(passenger.getName());
        etSurname.setText(passenger.getSurname());
        etPhone.setText(passenger.getPhone());
        etEmail.setText(passenger.getEmail());
    }

    private void initEditTexts(View view) {
        etAddress = view.findViewById(R.id.addressValue);
        etName = view.findViewById(R.id.nameValue);
        etSurname = view.findViewById(R.id.surnameValue);
        etPhone = view.findViewById(R.id.phoneValue);
        etEmail = view.findViewById(R.id.emailValue);
        btnSavePassenger = view.findViewById(R.id.btnSavePassenger);
        btnFavouriteRoutes = view.findViewById(R.id.btnFavouriteRoutes);
        btnReports = view.findViewById(R.id.btnReports);
    }

    private void initImageViews(View view) {
        addressEditImage = view.findViewById(R.id.addressEdit);
        nameEditImage = view.findViewById(R.id.nameEdit);
        surnameEditImage = view.findViewById(R.id.surnameEdit);
        phoneEditImage = view.findViewById(R.id.phoneEdit);
        emailEditImage = view.findViewById(R.id.emailEdit);
        btnSavePassenger = view.findViewById(R.id.btnSavePassenger);
    }

    class EditImageListener implements View.OnClickListener {

        private final int etId;
        private final View v;

        public EditImageListener(View v, int etId) {
            this.etId = etId;
            this.v = v;
        }

        @Override
        public void onClick(View view) {
            EditText etAddress =  v.findViewById(etId);
            etAddress.setEnabled(true);
            btnSavePassenger.setVisibility(View.VISIBLE);
        }
    }
}
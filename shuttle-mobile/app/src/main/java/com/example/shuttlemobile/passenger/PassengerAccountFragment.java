package com.example.shuttlemobile.passenger;

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

public class PassengerAccountFragment extends Fragment {
    private ImageView addressEdit;
    private ImageView nameEdit;
    private ImageView surnameEdit;
    private ImageView phoneEdit;
    private ImageView emailEdit;
    private Button btnSavePassenger;
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
        addressEdit = view.findViewById(R.id.addressEdit);
        nameEdit = view.findViewById(R.id.nameEdit);
        surnameEdit = view.findViewById(R.id.surnameEdit);
        phoneEdit = view.findViewById(R.id.phoneEdit);
        emailEdit = view.findViewById(R.id.emailEdit);
        btnSavePassenger = view.findViewById(R.id.btnSavePassenger);


        addressEdit.setOnClickListener(new EditListener(view, R.id.addressValue));
        nameEdit.setOnClickListener(new EditListener(view, R.id.nameValue));
        surnameEdit.setOnClickListener(new EditListener(view, R.id.surnameValue));
        phoneEdit.setOnClickListener(new EditListener(view, R.id.phoneValue));
        emailEdit.setOnClickListener(new EditListener(view, R.id.emailValue));
        btnSavePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btn) {
                addressEdit.setEnabled(false);
                nameEdit.setEnabled(false);
                surnameEdit.setEnabled(false);
                emailEdit.setEnabled(false);
                phoneEdit.setEnabled(false);
            }
        });
    }

    class EditListener implements View.OnClickListener {

        private final int etId;
        private final View v;

        public EditListener(View v, int etId) {
            this.etId = etId;
            this.v = v;
        }

        @Override
        public void onClick(View view) {
            EditText etAddress =  v.findViewById(etId);
            etAddress.setEnabled(true);
        }
    }
}
package com.example.shuttlemobile.driver.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.driver.DriverDTO;
import com.example.shuttlemobile.driver.IDriverService;
import com.example.shuttlemobile.user.JWT;
import com.example.shuttlemobile.util.SettingsUtil;
import com.example.shuttlemobile.vehicle.VehicleDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment for viewing and updating account info for the driver of this session.
 * Only certain fields are editable, while others must be changed in the web application.
 */
public class DriverAccountInfo extends GenericUserFragment {

    DriverDTO driverDTO;
    VehicleDTO vehicleDTO;

    public static DriverAccountInfo newInstance(SessionContext session) {
        DriverAccountInfo fragment = new DriverAccountInfo();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_account_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        EditText editName = getActivity().findViewById(R.id.txt_d_info_name);
        EditText editSurname = getActivity().findViewById(R.id.txt_d_info_surname);
        EditText editAddress = getActivity().findViewById(R.id.txt_d_info_address);
        EditText editPhone = getActivity().findViewById(R.id.txt_d_info_phone);
        ImageButton editPfp = getActivity().findViewById(R.id.img_d_info_pfp);
        EditText editModel = getActivity().findViewById(R.id.txt_d_info_vehicle_model);
        EditText editPlate = getActivity().findViewById(R.id.txt_d_info_vehicle_plate);
        Switch babySwitch = getActivity().findViewById(R.id.info_d_baby_switch);
        Switch petSwitch = getActivity().findViewById(R.id.info_d_pet_switch);
        Spinner seatSpinner = getActivity().findViewById(R.id.info_d_seat_count_spinner);
        Spinner typeSpinner = getActivity().findViewById(R.id.info_d_vehicle_type_spinner);
        Button btnSubmit = getActivity().findViewById(R.id.btn_d_info_info_submit);
        setTypeSpinnerItems(typeSpinner);
        setSeatSpinnerItems(seatSpinner);

        final JWT jwt = SettingsUtil.getUserJWT();
        setDriverDataFields(editName, editSurname, editAddress, editPhone, jwt);


        setVehicleDataFields(editModel, editPlate, babySwitch, petSwitch, seatSpinner, typeSpinner, jwt);

        editPfp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Select image from storage.
            }
        });

//        editName.setText(session.getUser().getName());
//        editSurname.setText(session.getUser().getLastName());
//        editAddress.setText(session.getUser().getLocation());
//        editPhone.setText(session.getUser().getPhone());
        // editPfp.

        // TODO: If all the input fields are the same as the current user data, disable the button.
        // You have to use listeners for each edit text.
        boolean canSubmit = true;
        btnSubmit.setActivated(canSubmit);
        btnSubmit.setOnClickListener(view1 -> pushChanges(editName, editSurname, editAddress, editPhone,
                editModel, editPlate, babySwitch, petSwitch, seatSpinner, typeSpinner, jwt));
    }

    private void setVehicleDataFields(EditText editModel, EditText editPlate, Switch babySwitch, Switch petSwitch, Spinner seatSpinner, Spinner typeSpinner, JWT jwt) {
        IDriverService.service.getVehicle(jwt.getId()).enqueue(new Callback<VehicleDTO>() {
            @Override
            public void onResponse(Call<VehicleDTO> call, Response<VehicleDTO> response) {
                if (response.code() == 200) {
                    vehicleDTO = response.body();
                    editModel.setText(vehicleDTO.getModel());
                    editPlate.setText(vehicleDTO.getLicenseNumber());
                    babySwitch.setChecked(vehicleDTO.getBabyTransport());
                    petSwitch.setChecked(vehicleDTO.getPetTransport());
                    seatSpinner.setSelection((int) (vehicleDTO.getPassengerSeats() - 1));
                    setTypeSelection(vehicleDTO, typeSpinner);

                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<VehicleDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to retrieve driver data", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setTypeSelection(VehicleDTO vehicleDTO, Spinner typeSpinner) {
        int i = 0;
        for (String type : getVehicleTypes()) {
            if (type.toLowerCase().equals(vehicleDTO.getVehicleType().toLowerCase().trim()))
                break;
            i++;
        }
        typeSpinner.setSelection(i);
    }

    private void setDriverDataFields(EditText editName, EditText editSurname, EditText editAddress, EditText editPhone, JWT jwt) {
        IDriverService.service.getDriver(jwt.getId()).enqueue(new Callback<DriverDTO>() {
            @Override
            public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
                if (response.code() == 200) {
                    driverDTO = response.body();
                    editAddress.setText(driverDTO.getAddress());
                    editName.setText(driverDTO.getName());
                    editSurname.setText(driverDTO.getSurname());
                    editPhone.setText(driverDTO.getTelephoneNumber());

                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<DriverDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to retrieve driver data", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void pushChanges(EditText editName, EditText editSurname, EditText editAddress, EditText editPhone,
                             EditText editModel, EditText editPlate, Switch babySwitch, Switch petSwitch, Spinner seatSpinner, Spinner typeSpinner, JWT jwt) {

        boolean driverChanged = checkDriverDTO(editName, editSurname, editAddress, editPhone);
        boolean vehicleChanged = checkVehicleDTO(editModel, editPlate, babySwitch, petSwitch, seatSpinner, typeSpinner);
        if (!driverChanged && !vehicleChanged) {
            Toast.makeText(getContext(), "You didnt make any changes", Toast.LENGTH_LONG).show();
        } else if (driverChanged) {
            updateDriver(editName, editSurname, editAddress, editPhone, jwt);
        } else if (vehicleChanged) {
            updateVehicle(editModel, editPlate, babySwitch, petSwitch, seatSpinner, typeSpinner, jwt);
        }


    }

    private void updateVehicle(EditText editModel, EditText editPlate, Switch babySwitch, Switch petSwitch, Spinner seatSpinner, Spinner typeSpinner, JWT jwt) {
        updateVehicleDTO(editModel, editPlate, babySwitch, petSwitch, seatSpinner, typeSpinner);

        IDriverService.service.updateVehicle(jwt.getId(), vehicleDTO).enqueue(new Callback<VehicleDTO>() {
            @Override
            public void onResponse(Call<VehicleDTO> call, Response<VehicleDTO> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Vehicle changes posted waiting for admins approval", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VehicleDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Failed putting changes to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateVehicleDTO(EditText editModel, EditText editPlate, Switch babySwitch, Switch petSwitch, Spinner seatSpinner, Spinner typeSpinner) {
        vehicleDTO.setBabyTransport(babySwitch.isChecked());
        vehicleDTO.setPetTransport(petSwitch.isChecked());
        vehicleDTO.setModel(editModel.getText().toString());
        vehicleDTO.setLicenseNumber(editPlate.getText().toString());
        vehicleDTO.setVehicleType(typeSpinner.getSelectedItem().toString());
        vehicleDTO.setPassengerSeats(Long.parseLong(seatSpinner.getSelectedItem().toString()));
    }

    private void updateDriver(EditText editName, EditText editSurname, EditText editAddress, EditText editPhone, JWT jwt) {
        updateDriverDTO(editName, editSurname, editAddress, editPhone);
        IDriverService.service.updateDriver(jwt.getId(), driverDTO).enqueue(new Callback<DriverDTO>() {
            @Override
            public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
                if (response.code() == 200) {
                    Toast.makeText(getContext(), "Driver changes posted waiting for admins approval", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DriverDTO> call, Throwable t) {
                Toast.makeText(getContext(), "Failed putting changes to server", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateDriverDTO(EditText editName, EditText editSurname, EditText editAddress, EditText editPhone) {
        driverDTO.setName(editName.getText().toString());
        driverDTO.setAddress(editAddress.getText().toString());
        driverDTO.setSurname(editSurname.getText().toString());
        driverDTO.setTelephoneNumber(editPhone.getText().toString());
    }

    private boolean checkVehicleDTO(EditText editModel, EditText editPlate, Switch babySwitch, Switch petSwitch, Spinner seatSpinner, Spinner typeSpinner) {
        if (!editModel.getText().toString().equals(vehicleDTO.getModel()) && !editModel.getText().toString().isEmpty())
            return true;
        if (!editPlate.getText().toString().equals(vehicleDTO.getLicenseNumber()) && !editPlate.getText().toString().isEmpty())
            return true;
        if (vehicleDTO.getBabyTransport() != babySwitch.isChecked())
            return true;
        if (vehicleDTO.getPetTransport() != petSwitch.isChecked())
            return true;
        if (!vehicleDTO.getVehicleType().equals(typeSpinner.getSelectedItem().toString()))
            return true;
        if (!seatSpinner.getSelectedItem().toString().equals(vehicleDTO.getPassengerSeats().toString()))
            return true;

        return false;
    }

    private boolean checkDriverDTO(EditText editName, EditText editSurname, EditText editAddress, EditText editPhone) {
        if (!driverDTO.getAddress().equals(editAddress.getText().toString()) && !editAddress.getText().toString().isEmpty())
            return true;
        if (!driverDTO.getName().equals(editName.getText().toString()) && !editName.getText().toString().isEmpty())
            return true;
        if (!driverDTO.getSurname().equals(editSurname.getText().toString()) && !editSurname.getText().toString().isEmpty())
            return true;
        if (!driverDTO.getTelephoneNumber().equals(editPhone.getText().toString()) && !editPhone.getText().toString().isEmpty())
            return true;

        return false;
    }

    private List<Integer> getVehicleSeats() {
        List<Integer> seats = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            seats.add(i);
        }
        return seats;
    }

    private List<String> getVehicleTypes() {
        List<String> list = new ArrayList<>();
        list.add("STANDARD");
        list.add("VAN");
        list.add("LUXURY");
        return list;
    }

    private void setSeatSpinnerItems(Spinner spinner) {
        List<Integer> types = getVehicleSeats();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setTypeSpinnerItems(Spinner spinner) {
        List<String> types = getVehicleTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
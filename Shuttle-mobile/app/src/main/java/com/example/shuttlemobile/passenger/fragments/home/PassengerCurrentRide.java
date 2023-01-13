package com.example.shuttlemobile.passenger.fragments.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.UserChatActivity;
import com.example.shuttlemobile.driver.services.CurrentRideTimeService;
import com.example.shuttlemobile.message.Chat;
import com.example.shuttlemobile.message.Message;
import com.example.shuttlemobile.message.MessageDTO;
import com.example.shuttlemobile.message.SendMessageDTO;
import com.example.shuttlemobile.passenger.fragments.PassengerHome;
import com.example.shuttlemobile.passenger.services.PassengerRideService;
import com.example.shuttlemobile.ride.IRideService;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.ride.dto.PanicDTO;
import com.example.shuttlemobile.ride.dto.RideDTO;
import com.example.shuttlemobile.route.LocationDTO;
import com.example.shuttlemobile.user.IUserService;
import com.example.shuttlemobile.util.SettingsUtil;
import com.mapbox.geojson.Point;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerCurrentRide extends Fragment {
    private TextView txtDeparture;
    private TextView txtDestination;
    private TextView txtDistance;
    private ImageView imgPet;
    private ImageView imgBaby;
    private TextView txtDriver;
    private Button btnNote;
    private Button btnPanic;
    private Group layWhenStarted;

    private TextView txtElapsedTime;

    private RideDTO ride = null;
    private Point A = null;
    private Point B = null;

    private PassengerHome parent = null;

    private BroadcastReceiver rideReceiver;
    private BroadcastReceiver timeReceiver;

    boolean startedTimer;


    public static PassengerCurrentRide newInstance() {
        return new PassengerCurrentRide();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_passenger_current_ride, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initParent();
        initViewElements(view);
        initTimeReceiver();
        initRideReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(rideReceiver);
        getActivity().unregisterReceiver(timeReceiver);
        stopTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        subscribeToRideReceiver();
        subscribeToTimeReceiver();
        startTimer();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void initParent() {
        parent = (PassengerHome) PassengerCurrentRide.this.getParentFragment();
        if (parent == null) {
            throw new IllegalStateException("Parent is null (should be DriverHome)");
        }
    }

    private void initViewElements(View view) {
        txtDeparture = view.findViewById(R.id.txt_p_cur_ride_departure);
        txtDestination = view.findViewById(R.id.txt_p_cur_ride_destination);
        txtDistance = view.findViewById(R.id.txt_p_cur_ride_distance);
        txtElapsedTime = view.findViewById(R.id.txt_p_cur_ride_timer);
        imgBaby = view.findViewById(R.id.img_p_cur_ride_baby);
        imgPet = view.findViewById(R.id.img_p_cur_ride_pet);
        txtDriver = view.findViewById(R.id.txt_p_cur_ride_driver);
        btnNote = view.findViewById(R.id.btn_p_cur_ride_note);
        btnPanic = view.findViewById(R.id.btn_p_cur_ride_panic);
        layWhenStarted = (Group) view.findViewById(R.id.lay_p_cur_ride_when_started);

        initDriverInfoClick();
        initNoteButtonClick();
        initPanicButtonClick();
    }

    private void initNoteButtonClick() {
        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendNote();
            }
        });
    }

    private void initPanicButtonClick() {
        btnPanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View v = inflater.inflate(R.layout.alert_reject_reason, null);
                EditText txtReason = v.findViewById(R.id.txt_reject_reason);

                builder.setView(v)
                        .setPositiveButton(R.string.panic, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sendPanic(txtReason.getText().toString());
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                AlertDialog dialog = builder.show();

                // If the specified reason is empty, disable the button.

                txtReason.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        if (charSequence.toString().trim().length() == 0) {
                            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
                        } else {
                            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
                        }
                    }

                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void afterTextChanged(Editable editable) {}
                });

                // Disable it right from the start (onTextChanged() isn't called on its own).

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
            }
        });
    }

    private void initDriverInfoClick() {
        txtDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = requireActivity().getLayoutInflater();
                View v = inflater.inflate(R.layout.alert_driver_info, null);
                TextView txtReason = v.findViewById(R.id.txt_alert_driver_info_email);
                Button btnMessage = v.findViewById(R.id.btn_alert_driver_info_message);
                Button btnCall = v.findViewById(R.id.btn_alert_driver_info_call);

                txtReason.setText(ride.getDriver().getEmail());
                btnMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // TODO This will crash because chat activity uses the obsolete 'session'.
                        Intent intent = new Intent(PassengerCurrentRide.this.getActivity(), UserChatActivity.class);
                        //intent.putExtra(UserChatActivity.PARAM_SESSION, null); no need for session anymore.
                        intent.putExtra(UserChatActivity.PARAM_CHAT, new Chat());
                        startActivity(intent);
                    }
                });
                btnCall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "Call user", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setView(v).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.show();
            }
        });
    }

    private void initTimeReceiver() {
        // TODO: This service is used by both passenger and driver, but
        //  it's declared in the driver package. See also startTimer().

        timeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra(CurrentRideTimeService.NEW_TIME_MESSAGE);
                txtElapsedTime.setText(s);
            }
        };

        subscribeToTimeReceiver();
    }

    private void subscribeToTimeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(CurrentRideTimeService.RESULT);
        getActivity().registerReceiver(timeReceiver, intentFilter);
    }

    private void initRideReceiver() {
        rideReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                RideDTO rideDTO = (RideDTO)intent.getSerializableExtra(PassengerRideService.INTENT_RIDE_KEY);
                onGetRide(rideDTO);
            }
        };

        subscribeToRideReceiver();
    }

    private void subscribeToRideReceiver() {
        if (rideReceiver == null) {
            return;
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PassengerRideService.BROADCAST_CHANNEL);
        getActivity().registerReceiver(rideReceiver, intentFilter);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private void onGetRide(RideDTO dto) {
        if (dto == null) {
            ride = null;
            A = null;
            B = null;
            stopTimer();
            return;
        }

        // If the currently cached ride is different from this one, redraw the route and focus on it.

        boolean isNewRide = false;
        if (ride == null || !ride.getId().equals(dto.getId())) {
            isNewRide = true;
        }

        boolean shouldShowTimer = false;
        if (ride != null) {
            Ride.Status dtoStatus = Ride.Status.valueOf(dto.getStatus());
            if (dtoStatus == Ride.Status.Accepted && dto.getStartTime() != null) {
                shouldShowTimer = true;
            }
        }

        boolean canPanicOrNote = false;
        if (dto != null) {
            Ride.Status dtoStatus = Ride.Status.valueOf(dto.getStatus());
            if (dtoStatus == Ride.Status.Accepted || dtoStatus == Ride.Status.Started) {
                canPanicOrNote = true;
            }
        }

        // Update ride.

        ride = dto;
        final LocationDTO A_loc = ride.getLocations().get(0).getDeparture();
        final LocationDTO B_loc = ride.getLocations().get(ride.getLocations().size() - 1).getDestination();
        A = Point.fromLngLat(A_loc.getLongitude(), A_loc.getLatitude());
        B = Point.fromLngLat(B_loc.getLongitude(), B_loc.getLatitude());

        if (isNewRide) {
            updateViewElements(A_loc, B_loc);
            drawAndFocusCurrentRoute();
        }

        if (shouldShowTimer) {
            if (!startedTimer)
                startTimer();
        } else {
            stopTimer();
        }

        if (canPanicOrNote) {
            layWhenStarted.setVisibility(View.VISIBLE);
        } else {
            layWhenStarted.setVisibility(View.GONE);
        }
    }

    private void startTimer() {
        if (ride != null && ride.getStartTime() != null) {
            Intent intent = new Intent(getActivity(), CurrentRideTimeService.class);
            intent.putExtra(CurrentRideTimeService.TIME_START, ride.getStartTime());
            requireActivity().startService(intent);
            startedTimer = true;
        }
    }

    private void stopTimer() {
        Intent myService = new Intent(requireContext(), CurrentRideTimeService.class);
        requireContext().stopService(myService);
    }

    private void drawAndFocusCurrentRoute() {
        if (A != null && B != null) {
            parent.drawRoute(A, B, "#FF0000");
            parent.fitViewport(A, B, 3000);
        }
    }

    private void updateViewElements(LocationDTO A_loc, LocationDTO B_loc) {
        txtDeparture.setText(A_loc.getAddress());
        txtDestination.setText(B_loc.getAddress());
        txtDistance.setText("???");

        imgBaby.setAlpha(ride.getBabyTransport() ? 1.0f : 0.25f);
        imgPet.setAlpha(ride.getPetTransport() ? 1.0f : 0.25f);

        txtDriver.setText(ride.getDriver().getEmail());
    }

    private void sendPanic(String reason) {
        if (ride == null) {
            return;
        }

        IRideService.service.panicRide(ride.getId(), new PanicDTO(reason)).enqueue(new Callback<RideDTO>() {
            @Override
            public void onResponse(Call<RideDTO> call, Response<RideDTO> response) {
            }

            @Override
            public void onFailure(Call<RideDTO> call, Throwable t) {
                Log.e("REST Error", t.toString());
            }
        });
    }

    private void sendNote() {
        IUserService.service.sendMessage(SettingsUtil.getUserJWT().getId(), new SendMessageDTO(
                Long.valueOf(-1),
                "The driver is not following the expected route",
                Message.Type.RIDE.toString(),
                ride.getId()
        )).enqueue(new Callback<MessageDTO>() {
            @Override
            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                Toast.makeText(getContext(), "Note sent!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MessageDTO> call, Throwable t) {

            }
        });
    }
}
package com.example.shuttlemobile.passenger.orderride;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.RouteDTO;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.passenger.orderride.fragments.ConfirmationFragment;
import com.example.shuttlemobile.passenger.orderride.fragments.InviteFragment;
import com.example.shuttlemobile.passenger.orderride.fragments.RidePropertiesFragment;
import com.example.shuttlemobile.passenger.orderride.fragments.ScheduleRide;
import com.example.shuttlemobile.ride.CreateRideDTO;
import com.example.shuttlemobile.ride.RideDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    int step = 0;
    int MAX_STEP = 3;
    private Button btnNext;
    private Button btnPrevious;
    private final String STACK_FRAGMENTS = "UserActivityFragment";
    protected Map<Integer, Fragment> fragments = new HashMap<>();
    private Fragment currentFragment;
    protected SessionContext session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initViewElements(getWindow().getDecorView().getRootView());

        session = new SessionContext();
        session.setUser(new Passenger(
                "", "", "", "", "", "", "", false, true
        ));

        initializeFragmentMap();
        changeStep();
//        setVisibleFragment(getDefaultFragment());
    }


    void previousStep() {
        step--;
        if (step < 0) step = 0;
        changeStep();
    }

    void nextStep() {
        step++;
        if (step > 3) step = MAX_STEP;
        changeStep();
    }

    protected void changeStep() {

        Fragment f = fragments.get(this.step);
        if (f != null) {
            setVisibleFragment(f);
        }
    }


    protected int getFragmentFrameId() {
        return R.id.order_fragment_view;
    }


    protected void initializeFragmentMap() {
        fragments.put(0, RidePropertiesFragment.newInstance(session));
        fragments.put(1, InviteFragment.newInstance(session));
        fragments.put(2, ScheduleRide.newInstance(session));
        fragments.put(3, ConfirmationFragment.newInstance(session));
    }


    protected Fragment getDefaultFragment() {
        return fragments.get(R.id.toolbar_home);
    }


    private void initViewElements(View view) {
        btnNext = view.findViewById(R.id.next_button);
        btnPrevious = view.findViewById(R.id.previous_button);

        btnNext.setOnClickListener(view1 -> {
            nextStep();
        });

        btnPrevious.setOnClickListener(view12 -> {
            previousStep();
        });
    }


    protected final void setVisibleFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .setReorderingAllowed(true)
                .replace(getFragmentFrameId(), fragment);
        fragmentTransaction.addToBackStack(STACK_FRAGMENTS);
        fragmentTransaction.commit();
        currentFragment = fragment;
    }


    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (currentFragment == getDefaultFragment()) {
            super.onBackPressed();
        } else {
            fm.popBackStack(STACK_FRAGMENTS, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            setVisibleFragment(getDefaultFragment());
        }
    }

    public CreateRideDTO getCreateRideDTO() {
        RidePropertiesFragment properties = (RidePropertiesFragment) fragments.get(0);
        InviteFragment invite = (InviteFragment) fragments.get(1);
        ScheduleRide schedule = (ScheduleRide) fragments.get(2);
        return generateDTO(properties, schedule, invite);
    }

    private CreateRideDTO generateDTO(RidePropertiesFragment properties, ScheduleRide schedule, InviteFragment invite) {
        CreateRideDTO dto = new CreateRideDTO();


        Bundle bundle = getIntent().getBundleExtra("routes");
        dto.setLocations((List<RouteDTO>) bundle.getSerializable("routes"));
        dto.setBabyTransport(properties.isBabyChecked());
        dto.setPetTransport(properties.isPetChecked());
        dto.setVehicleType(properties.getVehicleType());
        dto.setMinute(schedule.getMinuteAdvance());
        dto.setHour(schedule.getHourAdvance());
        dto.setPassengers(invite.getPassengers());
        Log.e("?", dto.toString());
        return dto;
    }

}
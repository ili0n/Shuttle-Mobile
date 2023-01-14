package com.example.shuttlemobile.passenger.orderride.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.passenger.orderride.ICheckUsersService;
import com.example.shuttlemobile.passenger.orderride.InvitesAdapter;
import com.example.shuttlemobile.unregistered.LoginActivity;
import com.example.shuttlemobile.user.JWT;
import com.example.shuttlemobile.user.RidePassengerDTO;
import com.example.shuttlemobile.util.SettingsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InviteFragment extends Fragment {
    private ArrayList<String> invites = new ArrayList<>();
    private ArrayList<RidePassengerDTO> inviteUsers = new ArrayList<>();
    private InvitesAdapter invitesAdapter;
    private ImageButton btn;
    private EditText txtInvite;

    public static InviteFragment newInstance(SessionContext session) {
        InviteFragment fragment = new InviteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_invite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerView(view);
        txtInvite = view.findViewById(R.id.edit_u_invite);
        btn = view.findViewById(R.id.btn_order_stepper_add_p);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(txtInvite.getText().toString().trim());
            }
        });
    }

    private void setRecyclerView(View view) {
        invitesAdapter = new InvitesAdapter(invites, getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.invites_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(invitesAdapter);
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });
    }

    private void addItem(String item) {
        // on below line we are checking
        // if item is empty or not.
        if (!item.isEmpty() && !invites.contains(item)) {
            // on below line we are adding
            // item to our list

            Call<RidePassengerDTO> call = ICheckUsersService.service.getUser(item);
            call.enqueue(new Callback<RidePassengerDTO>() {
                @Override
                public void onResponse(Call<RidePassengerDTO> call, Response<RidePassengerDTO> response) {
                    if (response.code() == 200) {
                        inviteUsers.add(response.body());
                        invites.add(item);
                        invitesAdapter.notifyDataSetChanged();
                        txtInvite.setText("");
                    } else
                        Toast.makeText(getContext(), "User doesn't exist", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<RidePassengerDTO> call, Throwable t) {
                    Toast.makeText(getContext(), "User doesn't exist", Toast.LENGTH_LONG).show();
                }
            });


        }
    }

    public List<RidePassengerDTO> getPassengers() {
        final JWT jwt = SettingsUtil.getUserJWT();
        RidePassengerDTO ridePassengerDTO = new RidePassengerDTO();
        ridePassengerDTO.setEmail(jwt.getEmail());
        ridePassengerDTO.setId(jwt.getId());

        List<RidePassengerDTO> res = inviteUsers.stream().map(p -> p).collect(Collectors.toList());
        res.add(ridePassengerDTO);
        return res;
    }
}
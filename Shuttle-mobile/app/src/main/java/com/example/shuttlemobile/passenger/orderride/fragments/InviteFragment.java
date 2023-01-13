package com.example.shuttlemobile.passenger.orderride.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteFragment extends Fragment {

    private ArrayList<String> invites = new ArrayList<>();
    public ArrayList<RidePassengerDTO> inviteUsers = new ArrayList<>();
    InvitesAdapter invitesAdapter;


    public InviteFragment() {
        // Required empty public constructor
    }

    public static InviteFragment newInstance(SessionContext session) {
        InviteFragment fragment = new InviteFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invite, container, false);
        setRecyclerView(view);
        EditText inviteField = (EditText) view.findViewById(R.id.edit_u_invite);
        ((Button) view.findViewById(R.id.btn_u_invite)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItem(inviteField.getText().toString().trim());
                Log.println(Log.ASSERT, "TextField", invitesAdapter.getItemCount() + "");
            }
        });

        return view;
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
    public List<RidePassengerDTO> getPassengers(){
        final JWT jwt = SettingsUtil.getUserJWT();
        RidePassengerDTO ridePassengerDTO = new RidePassengerDTO();
        ridePassengerDTO.setEmail(jwt.getEmail());
        ridePassengerDTO.setId(jwt.getId());
        inviteUsers.add(ridePassengerDTO);
        return inviteUsers;
    }
}
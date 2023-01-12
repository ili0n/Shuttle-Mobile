package com.example.shuttlemobile.passenger.orderride.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.GenericUserFragment;
import com.example.shuttlemobile.common.SessionContext;
import com.example.shuttlemobile.passenger.orderride.InvitesAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InviteFragment extends Fragment {

    private ArrayList<String> invites = new ArrayList<>();
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
                Log.println(Log.ASSERT, "TextField", inviteField.getText().toString().trim());
            }
        });

        return view;
    }

    private void setRecyclerView(View view) {
        invitesAdapter = new InvitesAdapter(invites, getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.invites_recycler);
        recyclerView.setAdapter(invitesAdapter);
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                recyclerView.getChildAdapterPosition(view);
                Log.println(Log.ASSERT, "Adapter", recyclerView.getChildAdapterPosition(view) + "");
                return false;
            }
        });

    }

    private void addItem(String item) {
        // on below line we are checking
        // if item is empty or not.
        if (!item.isEmpty()) {
            // on below line we are adding
            // item to our list
            invites.add(item);
            // on below line we are notifying
            // adapter that data has updated.
            invitesAdapter.notifyItemInserted(invites.size()-1);
        }
    }
}
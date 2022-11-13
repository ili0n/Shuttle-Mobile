package com.example.shuttlemobile.passenger;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.inbox.ChatActivity;
import com.example.shuttlemobile.inbox.ContactClickListener;
import com.example.shuttlemobile.inbox.ContactsAdapter;
import com.example.shuttlemobile.inbox.MockDataProvider;

import java.util.Objects;

public class PassengerInboxFragment extends Fragment implements ContactClickListener {
    public static PassengerInboxFragment newInstance() {
        return new PassengerInboxFragment();
    }

    String[] usernames;
    String[] lastMessages;
    int[] profiles;
    RecyclerView contacts;
    AppCompatActivity activity;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = ((AppCompatActivity) getActivity());
        activity.getSupportActionBar().setTitle(R.string.titleInbox);
        Log.println(Log.ASSERT,"init pass","assigned variables");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passenger_inbox, container, false);

        return view;
    }

    @Override
    public void onContactClicked(String username) {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putStringArray("messages", getResources().getStringArray(R.array.messages));
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        usernames = getResources().getStringArray(R.array.usernames);
        lastMessages = getResources().getStringArray(R.array.last_messages);

        profiles = (new MockDataProvider()).getImages();
        Log.println(Log.ASSERT,"init pass","assigned variables");
        contacts = getView().findViewById(R.id.passenger_contacts);

        Log.println(Log.ASSERT,"init pass","good context");

        ContactsAdapter adapter = new ContactsAdapter(getView().getContext(), usernames, lastMessages, profiles, this);
        Log.println(Log.ASSERT,"init pass","good adapter");
        contacts.setAdapter(adapter);
        Log.println(Log.ASSERT,"init pass","adapter pass");
        contacts.setLayoutManager(new LinearLayoutManager(getView().getContext()));
        Log.println(Log.ASSERT,"init pass","full pass");
    }
}
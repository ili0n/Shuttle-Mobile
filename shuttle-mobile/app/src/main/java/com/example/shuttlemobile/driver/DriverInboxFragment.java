package com.example.shuttlemobile.driver;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.inbox.ChatActivity;
import com.example.shuttlemobile.inbox.ContactClickListener;
import com.example.shuttlemobile.inbox.ContactsAdapter;
import com.example.shuttlemobile.inbox.MockDataProvider;



public class DriverInboxFragment extends Fragment  {
    private RecyclerView contacts;
    private String[] usernames;
    private String[] lastMessages;
    private int[] profiles;

    public DriverInboxFragment() {}

    public static DriverInboxFragment newInstance() {
        return new DriverInboxFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.titleInbox);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passenger_inbox, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernames = getResources().getStringArray(R.array.usernames);
        lastMessages = getResources().getStringArray(R.array.last_messages);

        profiles = (new MockDataProvider()).getImages();
        contacts = view.findViewById(R.id.passenger_contacts);


        ContactsAdapter adapter = new ContactsAdapter(getView().getContext(), usernames, lastMessages, profiles, new ContactClickListener() {
            @Override
            public void onContactClicked(String username) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                Bundle b = new Bundle();
                b.putStringArray("messages", getResources().getStringArray(R.array.messages));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
        contacts.setAdapter(adapter);
        contacts.setLayoutManager(new LinearLayoutManager(getView().getContext()));

    }
}

package com.example.shuttlemobile.inbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.shuttlemobile.R;

public class InboxActivity extends AppCompatActivity {
    String[] usernames;
    String[] lastMessages;
    int[] profiles;
    RecyclerView contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        usernames = getResources().getStringArray(R.array.usernames);
        lastMessages = getResources().getStringArray(R.array.last_messages);
        profiles = (new MockDataProvider()).getImages();
        contacts = findViewById(R.id.contacts);

        ContactsAdapter adapter = new ContactsAdapter(this,usernames,lastMessages, profiles);
        contacts.setAdapter(adapter);
        contacts.setLayoutManager(new LinearLayoutManager(this));

    }
}


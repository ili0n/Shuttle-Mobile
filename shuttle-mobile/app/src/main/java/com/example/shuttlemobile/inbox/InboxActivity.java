package com.example.shuttlemobile.inbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.shuttlemobile.R;

public class InboxActivity extends AppCompatActivity implements ContactClickListener {
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

        ContactsAdapter adapter = new ContactsAdapter(this, usernames, lastMessages, profiles, this);
        contacts.setAdapter(adapter);
        contacts.setLayoutManager(new LinearLayoutManager(this));


    }

    @Override
    public void onContactClicked(String username) {
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle b = new Bundle();
        b.putStringArray("messages", getResources().getStringArray(R.array.messages)); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
//        finish();
    }
}


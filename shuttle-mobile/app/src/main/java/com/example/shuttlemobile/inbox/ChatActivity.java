package com.example.shuttlemobile.inbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.shuttlemobile.R;

public class ChatActivity extends AppCompatActivity {
    String[] messages;
    RecyclerView chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Log.println(Log.ASSERT,"msg2","here");
        messages = getIntent().getStringArrayExtra("messages");

        chat =  findViewById(R.id.chat);

        Toolbar toolbar = findViewById(R.id.toolbar_driver);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Chat with person A");

        ChatAdapter adapter = new ChatAdapter(this,messages);
        chat.setAdapter(adapter);
        chat.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
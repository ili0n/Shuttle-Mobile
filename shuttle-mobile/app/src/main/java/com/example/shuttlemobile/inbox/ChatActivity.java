package com.example.shuttlemobile.inbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

        ChatAdapter adapter = new ChatAdapter(this,messages);
        chat.setAdapter(adapter);
        chat.setLayoutManager(new LinearLayoutManager(this));


    }
}
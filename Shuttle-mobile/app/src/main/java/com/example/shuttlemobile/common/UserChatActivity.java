package com.example.shuttlemobile.common;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.message.Chat;

public class UserChatActivity extends SimpleToolbarActivity {
    protected SessionContext session;
    protected Chat chat;
    public static final String PARAM_SESSION = "session";
    public static final String PARAM_CHAT = "chat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_user_chat);
    }

    private void initParams() {
        session = (SessionContext)getIntent().getSerializableExtra(PARAM_SESSION);
        chat = (Chat)getIntent().getSerializableExtra(PARAM_CHAT);

        if (session == null) {
            throw new NullPointerException("Missing intent parameter " + PARAM_SESSION);
        }
        if (chat == null) {
            throw new NullPointerException("Missing intent parameter " + PARAM_CHAT);
        }
    }
}
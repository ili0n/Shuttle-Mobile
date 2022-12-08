package com.example.shuttlemobile.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.driver.Driver;
import com.example.shuttlemobile.message.Chat;
import com.example.shuttlemobile.message.Message;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Activity for a chat screen.
 */
public class UserChatActivity extends SimpleToolbarActivity {
    protected SessionContext session;
    protected User other;
    protected Chat chat;
    public static final String PARAM_SESSION = "session";
    public static final String PARAM_CHAT = "chat";
    List<Message> messages = new ArrayList<>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        initParams();
        initMessages();
        assignSendClickCallback();
        initializeList();
    }

    private void initMessages() {
        // During early stages of the development, we wouldn't put this here,
        // but in the ListView's anonymous adapter. To show that sending messages
        // "works", an exception is made by making the messages list persist in-memory.

        Ride r = new Ride();
        r.setPassenger((Passenger)session.getUser());
        r.setDriver((Driver)other);

        messages.add(new Message(session.getUser(), other, "Hi", LocalDateTime.now(), r, Message.Type.RIDE));
        messages.add(new Message(other, session.getUser(), "Hey.", LocalDateTime.now(), r, Message.Type.RIDE));
        messages.add(new Message(session.getUser(), other, "Here's a longer message. The text bubble is larger now.", LocalDateTime.now(), r, Message.Type.RIDE));
        messages.add(new Message(other, session.getUser(), "Ok.\n\nBottom text", LocalDateTime.now(), r, Message.Type.RIDE));

    }

    private void assignSendClickCallback() {
        Button btnSend = findViewById(R.id.btn_u_chat_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        TextView txtMyMessage = findViewById(R.id.edit_u_chat_box);
        String message = txtMyMessage.getText().toString();

        // TODO: Some of the data is hard-coded.
        Message msg = new Message(
                session.getUser(),
                other,
                message,
                LocalDateTime.now(),
                null,
                Message.Type.RIDE
        );
        messages.add(msg);

        ((BaseAdapter)(listView.getAdapter())).notifyDataSetChanged();
        txtMyMessage.setText("");
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

        // TODO: Uncomment this once we have actual data.
        other = new Driver();
//        other = chat.getLastMessage().getOther(session.getUser());
    }

    private void initializeList() {
        listView = findViewById(R.id.list_u_messages);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return messages.size();
            }

            @Override
            public Object getItem(int i) {
                return messages.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public int getViewTypeCount() {
                return 2; // 0 for 'my' and 1 for 'other'.
            }

            @Override
            public int getItemViewType(int i) {
                Message m = (Message)getItem(i);
                if (m.getSender() == session.getUser()) {
                    return 0;
                } else if (m.getRecipient() == session.getUser()) {
                    return 1;
                }
                throw new IllegalStateException("User does not participate in this chat.");
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View v = null;
                Message m = (Message)getItem(i);
                int itemType = getItemViewType(i);
                LayoutInflater inflater = UserChatActivity.this.getLayoutInflater();

                TextView txtMessage = null;
                TextView txtDate = null;
                TextView txtTime = null;

                // Determine whether to use 'item_chat_me' or 'item_chat_other'.
                // Since view element of each chat bubble have a different ID based
                // on which user they belong to, we have to fetch them here as well.
                if (view == null) {
                    if (itemType == 0) {
                        v = inflater.inflate(R.layout.item_chat_me, null);
                        txtMessage = v.findViewById(R.id.txt_chat_me);
                        txtDate = v.findViewById(R.id.txt_chat_date_me);
                        txtTime = v.findViewById(R.id.txt_chat_time_me);
                    } else if (itemType == 1) {
                        v = inflater.inflate(R.layout.item_chat_other, null);
                        txtMessage = v.findViewById(R.id.txt_chat_other);
                        txtDate = v.findViewById(R.id.txt_chat_date_other);
                        txtTime = v.findViewById(R.id.txt_chat_time_other);
                    }

                } else {
                    v = view;

                    if (itemType == 0) {
//                        v = inflater.inflate(R.layout.item_chat_me, null);
                        txtMessage = v.findViewById(R.id.txt_chat_me);
                        txtDate = v.findViewById(R.id.txt_chat_date_me);
                        txtTime = v.findViewById(R.id.txt_chat_time_me);
                    } else if (itemType == 1) {
//                        v = inflater.inflate(R.layout.item_chat_other, null);
                        txtMessage = v.findViewById(R.id.txt_chat_other);
                        txtDate = v.findViewById(R.id.txt_chat_date_other);
                        txtTime = v.findViewById(R.id.txt_chat_time_other);
                    }
                }

                // TODO:
                // Use v.setTag()

                // null check necessary for some reason.
                if (txtMessage != null) {
                    txtMessage.setText(m.getMessage());
                }
                return v;
            }
        });
    }
}
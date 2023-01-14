package com.example.shuttlemobile.common;

import android.os.Bundle;
import android.util.Log;
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
import com.example.shuttlemobile.message.MessageDTO;
import com.example.shuttlemobile.message.SendMessageDTO;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.user.IUserService;
import com.example.shuttlemobile.user.User;
import com.example.shuttlemobile.util.SettingsUtil;
import com.example.shuttlemobile.util.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Activity for a chat screen.
 */
public class UserChatActivity extends SimpleToolbarActivity {
    protected User other;
    protected Chat chat;
    public static final String PARAM_SESSION = "session";
    public static final String PARAM_CHAT = "chat";

    List<MessageDTO> messages = new ArrayList<>();
    ListView listView;

    public static final String PARAM_OTHER_ID = "other_id";
    public static final String PARAM_RIDE_ID = "ride_id";
    public static final String PARAM_MSG_TYPE = "message_type";
    private Long otherId;
    private Long rideId; // Can be null.
    private Message.Type type;

    private TextView txtMyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        otherId = (Long)getIntent().getSerializableExtra(PARAM_OTHER_ID);
        rideId = (Long)getIntent().getSerializableExtra(PARAM_RIDE_ID);
        type = (Message.Type)getIntent().getSerializableExtra(PARAM_MSG_TYPE);
        txtMyMessage = findViewById(R.id.edit_u_chat_box);

        initButtonSend();
        initializeList();
    }

    private void initButtonSend() {
        Button btnSend = findViewById(R.id.btn_u_chat_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void addMessageToScreen(MessageDTO message) {
        messages.add(message);
        ((BaseAdapter)(listView.getAdapter())).notifyDataSetChanged();
        txtMyMessage.setText("");
    }

    private void sendMessage() {
        String message = txtMyMessage.getText().toString();

        final SendMessageDTO sendDto = new SendMessageDTO(
                message,
                type.toString(),
                rideId
        );
        Log.e("?send", sendDto.toString());


        IUserService.service.sendMessage(otherId, sendDto).enqueue(new Callback<MessageDTO>() {
            @Override
            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                if (response.code() == 200) {
                    addMessageToScreen(response.body());
                } else {
                    Log.e("?", response.toString());
                }
            }

            @Override
            public void onFailure(Call<MessageDTO> call, Throwable t) {

            }
        });
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
                MessageDTO m = (MessageDTO)getItem(i);
                Long myId = SettingsUtil.getUserJWT().getId();

                if (m.getSenderId().equals(myId)) {
                    return 0;
                } else if (m.getReceiverId().equals(myId)) {
                    return 1;
                }
                throw new IllegalStateException("User does not participate in this chat.");
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                View v = null;
                MessageDTO m = (MessageDTO)getItem(i);
                int itemType = getItemViewType(i);
                LayoutInflater inflater = UserChatActivity.this.getLayoutInflater();

                TextView txtMessage = null;
                TextView txtDate = null;
                TextView txtTime = null;

                // Determine whether to use 'item_chat_me' or 'item_chat_other'.
                // Since other view elements depend on the type of user (and his
                // respective layout), we have to initialize them there, as well.
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
                        txtMessage = v.findViewById(R.id.txt_chat_me);
                        txtDate = v.findViewById(R.id.txt_chat_date_me);
                        txtTime = v.findViewById(R.id.txt_chat_time_me);
                    } else if (itemType == 1) {
                        txtMessage = v.findViewById(R.id.txt_chat_other);
                        txtDate = v.findViewById(R.id.txt_chat_date_other);
                        txtTime = v.findViewById(R.id.txt_chat_time_other);
                    }
                }

                txtMessage.setText(m.getMessage());

                LocalDateTime ldt = LocalDateTime.parse(m.getTimeOfSending());
                txtDate.setText(ldt.format(DateTimeFormatter.ofPattern("DD/MM/YYYY")));
                txtTime.setText(ldt.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

                return v;
            }
        });
    }
}
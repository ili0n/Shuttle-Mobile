package com.example.shuttlemobile.common;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;

import com.example.shuttlemobile.admin.Admin;
import com.example.shuttlemobile.common.receiver.InboxFragmentMessageReceiver;
import com.example.shuttlemobile.driver.Driver;
import com.example.shuttlemobile.message.Message;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.message.Chat;
import com.example.shuttlemobile.passenger.Passenger;
import com.example.shuttlemobile.ride.Ride;
import com.example.shuttlemobile.user.User;
import com.example.shuttlemobile.util.NotificationUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Inbox fragment is shared between all users.
 */
public class InboxFragment extends GenericUserFragment {
    private ListView listView;
    private List<Chat> chats;
    private Activity activity;

    private BroadcastReceiver gotNewMessageReceiver;

    public static InboxFragment newInstance(SessionContext session) {
        InboxFragment fragment = new InboxFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GenericUserFragment.KEY_SESSION, session);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        activity = getActivity();
        initializeList();
        initListView();
        initReceiver();
    }

    private void initReceiver() {
        gotNewMessageReceiver = new InboxFragmentMessageReceiver(session, this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationUtil.DRIVER_NOTIFICATION_CHANNEL_ID);
        intentFilter.addAction(NotificationUtil.PASSENGER_NOTIFICATION_CHANNEL_ID);
        getActivity().registerReceiver(gotNewMessageReceiver, intentFilter);
    }

    private void initializeList() {
        Chat c = new Chat();
        List<Message> messages = new ArrayList<>();
        User other = new Driver();
        Ride r = new Ride();
        messages.add(new Message(session.getUser(), other, "Hi", LocalDateTime.now(), r, Message.Type.RIDE));
        messages.add(new Message(other, session.getUser(), "Hey.", LocalDateTime.now(), r, Message.Type.RIDE));
        messages.add(new Message(session.getUser(), other, "Here's a longer message. The text bubble is larger now.", LocalDateTime.now(), r, Message.Type.RIDE));
        messages.add(new Message(other, session.getUser(), "Ok.\n\nBottom text", LocalDateTime.now(), r, Message.Type.RIDE));
        c.setMessages(messages);

        Chat c2 = new Chat();
        List<Message> messages2 = new ArrayList<>();
        messages2.add(new Message(session.getUser(), other, "Hi", LocalDateTime.now(), r, Message.Type.RIDE));
        c2.setMessages(messages2);

        Chat c3 = new Chat();
        List<Message> messages3 = new ArrayList<>();
        messages3.add(new Message(new Admin(), session.getUser(), "I am the support.", LocalDateTime.now(), null, Message.Type.SUPPORT));
        c3.setMessages(messages3);

        chats = new ArrayList<>();
        chats.add(c2);
        chats.add(c2);
        chats.add(c);
        chats.add(c3);
        chats.add(c);
        chats.add(c2);

        // Put support chat at top.

        chats = chats.stream().sorted((chat1, chat2) -> {
            User other1 = chat1.getLastMessage().getOther(session.getUser());
            User other2 = chat2.getLastMessage().getOther(session.getUser());
            if (other1 instanceof Admin) {
                return -1;
            } else if (other2 instanceof Admin) {
                return 1;
            }
            return 0;
        }).collect(Collectors.toList());
    }

    private void initListView() {
        listView = getActivity().findViewById(R.id.list_u_inbox);
        listView.setAdapter(new EasyListAdapter<Chat>() {
            @Override
            public List<Chat> getList() {
                return chats;
            }

            @Override
            public LayoutInflater getLayoutInflater() {
                return InboxFragment.this.getLayoutInflater();
            }

            @Override
            public int getListItemLayoutId() {
                return R.layout.list_u_inbox;
            }

            @Override
            public void applyToView(View view, Chat obj) {
                ImageView otherPfp = view.findViewById(R.id.img_u_inbox_pfp);
                TextView otherName = view.findViewById(R.id.txt_u_inbox_name);
                TextView lastMsg = view.findViewById(R.id.txt_u_inbox_msg);
                TextView lastMsgDate = view.findViewById(R.id.txt_u_inbox_date);
                TextView lastMsgTime = view.findViewById(R.id.txt_u_inbox_time);
                TextView otherRoleBullet = view.findViewById(R.id.txt_u_inbox_role_bullet);

                final Message lastMsgObj = obj.getLastMessage();

                if (lastMsgObj != null) {
                    User o = lastMsgObj.getOther(session.getUser());

                    if (o instanceof Driver) {
                        otherRoleBullet.setTextColor(Color.RED);
                    } else if (o instanceof Passenger) {
                        otherRoleBullet.setTextColor(Color.GREEN);
                    } else if (o instanceof Admin) {
                        otherRoleBullet.setTextColor(Color.BLUE);
                    }

                    String lastMsgPrefix = "";
                    if (lastMsgObj.getSender() == session.getUser()) {
                        lastMsgPrefix = "You: ";
                    }

                    otherName.setText(o.getName() + " " + o.getLastName());
                    lastMsg.setText(lastMsgPrefix + lastMsgObj.getMessage());
                    lastMsgDate.setText(lastMsgObj.getDate().format(DateTimeFormatter.ofPattern("d/M/yy")));
                    lastMsgTime.setText(lastMsgObj.getDate().format(DateTimeFormatter.ofPattern("HH:mm")));
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Chat obj = (Chat)listView.getItemAtPosition(i);
                openChatActivity(obj);
            }
        });
    }

    public void dummyFetchNewData() {
        Message lastm = chats.get(1).getLastMessage();
        Message newMessage = new Message(
                lastm.getRecipient(),
                lastm.getSender(),
                "Hi " + LocalDateTime.now().getSecond(),
                LocalDateTime.now(),
                lastm.getRide(),
                lastm.getType());
        chats.get(1).getMessages().add(newMessage);

        // Run UI update async.
        // TODO: Is this worthless? UI can only be updated in the main thread.

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(() -> {
                    ((BaseAdapter)(listView.getAdapter())).notifyDataSetChanged();
                });
            }
        });

        // If I'm the recipient of this message, send a notification.

        if (newMessage.getRecipient() == session.getUser()) {
            sendNotification(newMessage);
        }
    }

    private void sendNotification(Message newMessage) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), NotificationUtil.PASSENGER_NOTIFICATION_CHANNEL_ID)
                .setContentTitle(newMessage.getSender().getName())
                .setContentText(newMessage.getMessage())
                .setSmallIcon(R.drawable.ic_baseline_inbox_24)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
        notificationManager.notify(112213, builder.build());
    }

    private void openChatActivity(Chat chat) {
        Intent intent = new Intent(getActivity(), UserChatActivity.class);
        intent.putExtra(UserChatActivity.PARAM_SESSION, session);
        intent.putExtra(UserChatActivity.PARAM_CHAT, chat);
        startActivity(intent);
    }
}
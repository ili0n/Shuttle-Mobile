package com.example.shuttlemobile.common;

import android.content.Intent;
import android.os.Bundle;

import com.example.shuttlemobile.message.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shuttlemobile.R;
import com.example.shuttlemobile.common.adapter.EasyListAdapter;
import com.example.shuttlemobile.message.Chat;
import com.example.shuttlemobile.passenger.subactivities.PassengerHistoryDetailsActivity;
import com.example.shuttlemobile.ride.Ride;

import java.util.ArrayList;
import java.util.List;

/**
 * Inbox fragment is shared between all users.
 */
public class InboxFragment extends GenericUserFragment {
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
        initializeList();
    }

    private void initializeList() {
        ListView listView = getActivity().findViewById(R.id.list_u_inbox);

        List<Chat> chats = new ArrayList<>();
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());
        chats.add(new Chat());

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

                final Message lastMsgObj = obj.getLastMessage();

                if (lastMsgObj != null) {

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

    private void openChatActivity(Chat chat) {
//        Intent intent = new Intent(getActivity(), PassengerHistoryDetailsActivity.class);
//        intent.putExtra(PassengerHistoryDetailsActivity.PARAM_SESSION, session);
//        intent.putExtra(PassengerHistoryDetailsActivity.PARAM_RIDE, ride);
//        startActivity(intent);
    }
}
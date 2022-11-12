package com.example.shuttlemobile.inbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuttlemobile.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapterViewHolder> {
    Context context;
    String[] usernames;
    String[] lastMessages;
    int[] profiles;
    ContactClickListener contactClickListener;

    public ContactsAdapter(Context context, String[] usernames, String[] lastMessages, int[] images, ContactClickListener listener) {
        this.context = context;
        this.usernames = usernames;
        this.lastMessages = lastMessages;
        this.profiles = images;
        contactClickListener = listener;

    }

    @NonNull
    @Override
    public ContactsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.contact_row, parent, false);
        return new ContactsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapterViewHolder holder, int position) {
        holder.chatUsername.setText(usernames[position]);
        holder.lastMessage.setText(lastMessages[position]);
        holder.profile.setImageResource(profiles[position]);
        String username = usernames[position];
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactClickListener.onContactClicked(username);
            }
        });

    }

    @Override
    public int getItemCount() {
        return profiles.length;
    }


}

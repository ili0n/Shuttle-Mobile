package com.example.shuttlemobile.inbox;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuttlemobile.R;

public class ContactsAdapterViewHolder extends RecyclerView.ViewHolder {

    TextView chatUsername;
    TextView lastMessage;
    ImageView profile;
    CardView cardView;

    public ContactsAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        chatUsername = itemView.findViewById(R.id.username);
        lastMessage = itemView.findViewById(R.id.lastMsg);
        profile = itemView.findViewById(R.id.profileImg);
        cardView = itemView.findViewById(R.id.contact);
    }
}

package com.example.shuttlemobile.inbox;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuttlemobile.R;

public class ChatAdapterViewHolder extends RecyclerView.ViewHolder {
    Button message;
    ConstraintLayout constraintLayout;

    public ChatAdapterViewHolder(@NonNull View itemView) {
        super(itemView);
        message = itemView.findViewById(R.id.chat_message);
        constraintLayout = itemView.findViewById(R.id.chat_layout);

    }
}

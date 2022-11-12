package com.example.shuttlemobile.inbox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuttlemobile.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapterViewHolder> {
    Context context;
    String[] messages;

    public ChatAdapter(Context context, String[] messages ) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.chat_message,parent,false);
        return new ChatAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapterViewHolder holder, int position) {
        String message = messages[position];

        String[] tokens = message.split("\\|");
        holder.message.setText(tokens[1]);

        ConstraintLayout layout = holder.constraintLayout;
        if(tokens[0].equals("0")){
            Log.println(Log.ASSERT,"msg0",message);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(layout);
            constraintSet.connect(R.id.chat_message,ConstraintSet.RIGHT,R.id.chat_layout,ConstraintSet.RIGHT,5);
            constraintSet.applyTo(layout);
        }
        else {
            Log.println(Log.ASSERT,"msg1",message);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(layout);
            constraintSet.connect(R.id.chat_message,ConstraintSet.LEFT,R.id.chat_layout,ConstraintSet.LEFT,5);
            constraintSet.applyTo(layout);
            holder.message.setBackgroundColor(context.getColor(R.color.teal_700));
        }
    }

    @Override
    public int getItemCount() {
        return messages.length;
    }


}

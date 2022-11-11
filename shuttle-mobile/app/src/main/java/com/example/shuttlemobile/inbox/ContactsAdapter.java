package com.example.shuttlemobile.inbox;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuttlemobile.R;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {
    Context context;
    String[] usernames;
    String[] lastMessages;
    int[] profiles;

    public  ContactsAdapter(Context context, String[] usernames, String[] lastMessages,int[] images){
        this.context = context;
        this.usernames = usernames;
        this.lastMessages = lastMessages;
        this.profiles = images;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.contact_row,parent,false);
        return new  ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        holder.chatUsername.setText(usernames[position]);
        holder.lastMessage.setText(lastMessages[position]);
        holder.profile.setImageResource(profiles[position]);
    }

    @Override
    public int getItemCount() {
        return profiles.length;
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {

        TextView chatUsername;
        TextView lastMessage;
        ImageView profile;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            chatUsername = itemView.findViewById(R.id.username);
            lastMessage = itemView.findViewById(R.id.lastMsg);
            profile = itemView.findViewById(R.id.profileImg);
        }
    }
}

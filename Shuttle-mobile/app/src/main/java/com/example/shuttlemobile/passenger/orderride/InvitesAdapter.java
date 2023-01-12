package com.example.shuttlemobile.passenger.orderride;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuttlemobile.R;

import java.util.ArrayList;

public class InvitesAdapter extends RecyclerView.Adapter<InvitesAdapter.ViewHolder>{

    private ArrayList<String> invites;

    public InvitesAdapter(ArrayList<String> invites, Context context) {
        this.invites = invites;
    }
    @NonNull
    @Override
    public InvitesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_item, parent, false);

        // at last we are returning our view holder
        // class with our item View File.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitesAdapter.ViewHolder holder, int position) {
        holder.invitee.setText(invites.get(position));
    }

    @Override
    public int getItemCount() {
        return invites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView invitee;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            invitee = itemView.findViewById(R.id.invite_text);
        }
    }
}

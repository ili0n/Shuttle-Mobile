package com.example.shuttlemobile.passenger.orderride;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shuttlemobile.R;

import java.util.ArrayList;

public class InvitesAdapter extends RecyclerView.Adapter<InvitesAdapter.ViewHolder> {
    private ArrayList<String> invites;

    public InvitesAdapter(ArrayList<String> invites, Context context) {
        this.invites = invites;
    }

    @NonNull
    @Override
    public InvitesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // this method is use to inflate the layout file
        // which we have created for our recycler view.
        // on below line we are inflating our layout file.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invite_item, parent, false);
        Log.println(Log.ASSERT, "onCreateViewHolder triggered",  "");
        // at last we are returning our view holder
        // class with our item View File.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InvitesAdapter.ViewHolder holder, int position) {
        // on below line we are setting text to our text view.
        holder.invite.setText(invites.get(position));
        setHolderOnClickListeners(holder);
        Log.println(Log.ASSERT, "onBindViewHolder triggered",  position+ "");
    }

    private void setHolderOnClickListeners(@NonNull ViewHolder holder) {
        setHolderShortClick(holder);
        setHolderLongClick(holder);
    }

    private void setHolderLongClick(@NonNull ViewHolder holder) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                invites.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                return false;
            }
        });
    }

    private void setHolderShortClick(@NonNull ViewHolder holder) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Long click to remove item", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return invites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // on below line we are creating variable.
        private TextView invite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // on below line we are initialing our variable.
            invite = itemView.findViewById(R.id.invite_text);
        }
    }
}

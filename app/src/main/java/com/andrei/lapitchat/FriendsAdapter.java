package com.andrei.lapitchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ItemHolder> {

    Context context;
    ArrayList<Friends> usersArrayList;

    public FriendsAdapter(Context context, ArrayList<Friends> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    public FriendsAdapter(ArrayList<Friends> usersArrayList) {
        this.usersArrayList = usersArrayList;
    }


    @NonNull
    @Override
    public FriendsAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, null);
        FriendsAdapter.ItemHolder itemHolder = new FriendsAdapter.ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Friends friends = usersArrayList.get(position);
        holder.textStatus.setText(friends.getDate());
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder {
        public TextView textStatus;


        public ItemHolder(View itemView) {
            super(itemView);

            textStatus = itemView.findViewById(R.id.user_single_status);
        }
    }
}

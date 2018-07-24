package com.andrei.lapitchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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
        holder.textUserName.setText(friends.getUserName());

        if (friends.isOnline()) {
            holder.imageOnline.setVisibility(View.VISIBLE);
        } else {
            holder.imageOnline.setVisibility(View.INVISIBLE);
        }

        Picasso.get().load(friends.getThumb_image()).placeholder(R.mipmap.default_avatar).into(holder.imageUser);
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }


    class ItemHolder extends RecyclerView.ViewHolder {
        TextView textStatus;
        TextView textUserName;
        CircleImageView imageUser;
        ImageView imageOnline;


        ItemHolder(View itemView) {
            super(itemView);

            textStatus = itemView.findViewById(R.id.user_single_status);
            textUserName = itemView.findViewById(R.id.user_single_name);
            imageUser = itemView.findViewById(R.id.user_single_image);
            imageOnline = itemView.findViewById(R.id.user_single_online_icon);
        }
    }
}

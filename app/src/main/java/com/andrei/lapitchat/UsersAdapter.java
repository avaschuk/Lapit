package com.andrei.lapitchat;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ItemHolder> {

    Context context;
    ArrayList<Users> usersArrayList;

    public UsersAdapter(Context context, ArrayList<Users> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    public UsersAdapter(ArrayList<Users> usersArrayList) {
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_single_layout, null);
        UsersAdapter.ItemHolder itemHolder = new UsersAdapter.ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Users user = usersArrayList.get(position);

        holder.setName(user.getName());
        holder.setStatus(user.getStatus());
        holder.setImage(user.getThumb_image());
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        TextView userNameView;
        TextView userStatusView;
        CircleImageView userSingleImage;

        View mView;

        public ItemHolder(View itemView) {
            super(itemView);
            userNameView = itemView.findViewById(R.id.user_single_name);
            userStatusView = itemView.findViewById(R.id.user_single_status);
            userSingleImage = itemView.findViewById(R.id.user_single_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profileIntent = new Intent(context, ProfileActivity.class);
                    profileIntent.putExtra("user_id", UsersActivity.user_id_list.get(getLayoutPosition()));
                    profileIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(profileIntent);
                }
            });

            mView = itemView;

        }

        public void setName(String name) {
            userNameView.setText(name);
        }

        public void setStatus(String status) {
            userStatusView.setText(status);
        }

        public void setImage(String imageUrl) {
            Picasso.get().load(imageUrl).placeholder(R.mipmap.default_avatar).into(userSingleImage);
        }

    }
}

package com.andrei.lapitchat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
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

        if (friends.getOnline().equals("true")) {
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    CharSequence options[] = new CharSequence[] {"Open Profile", "Send message"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Select Options");
                    builder.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            //Click Event fir each item

                            switch (i) {
                                case 0:
                                    Intent profileIntent = new Intent(context, ProfileActivity.class);
                                    profileIntent.putExtra("user_id", FriendsFragment.list_user_friend_key.get(getLayoutPosition()));
                                    context.startActivity(profileIntent);
                                    break;
                                case 1:
                                    Intent chatIntent = new Intent(context, ChatActivity.class);
                                    chatIntent.putExtra("user_id", FriendsFragment.list_user_friend_key.get(getLayoutPosition()));
                                    chatIntent.putExtra("user_name", FriendsFragment.friendsArrayList.get(getLayoutPosition()).getUserName());
                                    context.startActivity(chatIntent);
                                    break;
                            }
                        }
                    });

                    builder.show();
                }
            });
        }
    }
}

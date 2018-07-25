package com.andrei.lapitchat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ItemHolder> {

    Context context;
    private ArrayList<Messages> mMessageList;

    public MessageAdapter(Context context, ArrayList<Messages> mMessageList) {
        this.context = context;
        this.mMessageList = mMessageList;
    }

    public MessageAdapter(ArrayList<Messages> mMessageList) {
        this.mMessageList = mMessageList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_single_layout, null);
        MessageAdapter.ItemHolder itemHolder = new MessageAdapter.ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Messages messages = mMessageList.get(position);

        holder.setMessageText(messages.getMessage());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        public TextView messageText;
        public CircleImageView profileImage;

        public ItemHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.message_text_layout);
            profileImage = itemView.findViewById(R.id.message_profile_layout);
        }


        public void setMessageText(String message) {
            messageText.setText(message);
        }

        public void setProfileImage(String image) {
            Picasso.get().load(image).placeholder(R.mipmap.default_avatar).into(profileImage);
        }
    }
}

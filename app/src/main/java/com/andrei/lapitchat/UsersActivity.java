package com.andrei.lapitchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mUsersList;

    private DatabaseReference mUsersDatabase;

    private FirebaseRecyclerAdapter<Users, UsersViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mToolbar = findViewById(R.id.users_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All users");

        mUsersList = findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = mUsersDatabase.orderByChild("name");

        FirebaseRecyclerOptions<Users> options =
                new FirebaseRecyclerOptions.Builder<Users>()
                        .setQuery(query, Users.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int position, @NonNull Users model) {
                usersViewHolder.setName(model.getName());
                usersViewHolder.setStatus(model.getStatus());
                usersViewHolder.setImage(model.getThumb_image());

                final String user_id = getRef(position).getKey();


                usersViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent profileIntent = new Intent(UsersActivity.this, ProfileActivity.class);
                        profileIntent.putExtra("user_id", user_id);
                        startActivity(profileIntent);

                    }
                });
            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_single_layout, parent, false);
                return new UsersViewHolder(view);
            }
        };

        mUsersList.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    private static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public UsersViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setName(String name) {
            TextView userNameView = mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);
        }

        public void setStatus(String status) {
            TextView userStatusView = mView.findViewById(R.id.user_single_status);
            userStatusView.setText(status);
        }

        public void setImage(String imageUrl) {
            CircleImageView userSingleImage = mView.findViewById(R.id.user_single_image);
            Picasso.get().load(imageUrl).placeholder(R.mipmap.default_avatar).into(userSingleImage);
        }

    }
}

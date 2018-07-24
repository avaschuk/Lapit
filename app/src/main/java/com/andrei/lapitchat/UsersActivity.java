package com.andrei.lapitchat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private RecyclerView mUsersList;

    private DatabaseReference mUsersDatabase;
    private FirebaseAuth mAuth;
    private String user_id;

    private UsersAdapter usersAdapter;

    private ArrayList<Users> usersArrayList;

    public static ArrayList<String> user_id_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        init();

        mUsersDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (!dataSnapshot.getKey().equals(user_id)) {
                    String name = dataSnapshot.hasChild("name") ? dataSnapshot.child("name").getValue().toString() : null;
                    String thumb_image = dataSnapshot.hasChild("thumb_image") ? dataSnapshot.child("thumb_image").getValue().toString() : null;
                    String status = dataSnapshot.hasChild("status") ? dataSnapshot.child("status").getValue().toString() : null;
                    String image = dataSnapshot.hasChild("image") ? dataSnapshot.child("image").getValue().toString() : null;

                    Users user = new Users(name, status, image, thumb_image);
                    usersArrayList.add(user);
                    user_id_list.add(dataSnapshot.getKey());
                    usersAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        user_id = mAuth.getCurrentUser().getUid();
        mToolbar = findViewById(R.id.users_toolbar);
        usersArrayList = new ArrayList<>();
        user_id_list = new ArrayList<>();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All users");

        mUsersList = findViewById(R.id.users_list);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(this, usersArrayList);
        mUsersList.setAdapter(usersAdapter);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
    }

}

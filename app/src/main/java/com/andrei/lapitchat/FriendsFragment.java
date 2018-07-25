package com.andrei.lapitchat;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class FriendsFragment extends Fragment {

    private RecyclerView mFriendsList;

    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mUsersDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;
    private View mMainView;

    public static ArrayList<Friends> friendsArrayList;
    public static ArrayList<String> list_user_friend_key;
    private FriendsAdapter friendsAdapter;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_friends, container, false);
        init();

        mFriendsDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String date = dataSnapshot.child("date").getValue().toString();

                final String list_user_id= dataSnapshot.getKey();

                mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String userName = dataSnapshot.child("name").getValue().toString();
                        String userStatus = dataSnapshot.child("status").getValue().toString();
                        String userThumb = dataSnapshot.hasChild("thumb_image") ? dataSnapshot.child("thumb_image").getValue().toString() : "default";
                        String userOnline = dataSnapshot.hasChild("online") ? dataSnapshot.child("online").getValue().toString() : "false";

                        System.out.println("list_user_friend_key");
                        System.out.println(list_user_friend_key);
                        System.out.println("dataSnapshot.getKey()");
                        System.out.println(dataSnapshot.getKey());


                        if (list_user_friend_key.contains(dataSnapshot.getKey())) {
                            int position = list_user_friend_key.indexOf(dataSnapshot.getKey().toString());
                            System.out.println("position");
                            System.out.println(position);
                            System.out.println("friendsArrayList.get(position)");
                            System.out.println(friendsArrayList.get(position));
                            friendsArrayList.get(position).setOnline(userOnline);
                            System.out.println("friendsArrayList.get(position)");
                            System.out.println(friendsArrayList.get(position));
                            friendsAdapter.notifyDataSetChanged();
                        } else {
                            list_user_friend_key.add(dataSnapshot.getKey().toString());
                            Friends friends = new Friends(date, userName, userStatus, userThumb, userOnline);
                            friendsArrayList.add(friends);
                            friendsAdapter.notifyDataSetChanged();
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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

        return mMainView;
    }

    public void init() {
        mFriendsList = mMainView.findViewById(R.id.friends_list);
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        friendsArrayList = new ArrayList<>();
        list_user_friend_key = new ArrayList<>();
        friendsAdapter = new FriendsAdapter(getContext(), friendsArrayList);

        mFriendsList.setHasFixedSize(true);
        mFriendsList.setLayoutManager(new LinearLayoutManager(getContext()));
        mFriendsList.setAdapter(friendsAdapter);
    }
}

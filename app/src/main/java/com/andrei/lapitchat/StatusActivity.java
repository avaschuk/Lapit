package com.andrei.lapitchat;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    private TextInputLayout mStatus;
    private Button mSaveBtn;

    //Firebase
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;

    //Progress
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        //Firebase
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

        mToolbar = findViewById(R.id.status_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account Status");


        String statusValue = getIntent().getStringExtra("status_value");
        mStatus = findViewById(R.id.status_input);
        mStatus.getEditText().setText(statusValue);
        mSaveBtn = findViewById(R.id.status_save_btn);


        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Progress
                mProgress = new ProgressDialog(StatusActivity.this);
                mProgress.setTitle("Saving changes");
                mProgress.setMessage("Please wait while we save the changes");
                mProgress.show();

                String status = mStatus.getEditText().getText().toString();


                mDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mProgress.dismiss();
                        } else {
                            mProgress.hide();
                            Toast.makeText(StatusActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}

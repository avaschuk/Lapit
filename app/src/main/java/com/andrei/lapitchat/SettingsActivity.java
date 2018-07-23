package com.andrei.lapitchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;


    //Android layout
    private CircleImageView mImage;
    private TextView mName;
    private TextView mStatus;
    private Button mStatusBtn;
    private Button mImageBtn;

    public static final int GALLERY_PICK = 1;

    //Storage reference
    private StorageReference mImageStorage;

    //Progress bar
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mImage = findViewById(R.id.settings_image);
        mName = findViewById(R.id.settings_name);
        mStatus = findViewById(R.id.settings_status);
        mStatusBtn = findViewById(R.id.settings_status_btn);
        mImageBtn = findViewById(R.id.settings_image_btn);

        mImageStorage = FirebaseStorage.getInstance().getReference();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider
                .getCredential("kobrintown@gmail.com", "e12123232");
        mCurrentUser.reauthenticate(credential);
        System.out.println("mCurrentUser.zzq()");
        System.out.println(mCurrentUser.zzq());
        System.out.println("mCurrentUser.zzr()");
        System.out.println(mCurrentUser.zzr());

        String current_uid = mCurrentUser.getUid();

        System.out.println("FirebaseDatabase.getInstance().getReference().getDatabase()");
        System.out.println(FirebaseDatabase.getInstance().getReference());

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        System.out.println("current_uid");
        System.out.println(current_uid);
        System.out.println("mUserDatabase.toString()");
        System.out.println(mUserDatabase.child("name"));

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("onDataChange");

                System.out.println(dataSnapshot.toString());
                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                mName.setText(name);
                mStatus.setText(status);

                if (!image.equals("default")) {
                    Picasso.get().load(image).placeholder(R.mipmap.default_avatar).into(mImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("onCancelled");
                System.out.println(databaseError);
            }
        });

        mStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String status_value = mStatus.getText().toString();
                Intent statusIntent = new Intent(getApplicationContext(), StatusActivity.class);
                statusIntent.putExtra("status_value", status_value);
                startActivity(statusIntent);
            }
        });

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);*/

                //startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

                // start picker to get image for cropping and then use the image in cropping activity
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(SettingsActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {
            Uri imageUrl = data.getData();

            CropImage.activity(imageUrl)
                    .setAspectRatio(1, 1)
                    .start(this);



            //Toast.makeText(this, imageUrl, Toast.LENGTH_LONG).show();
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mProgressDialog = new ProgressDialog(SettingsActivity.this);
                mProgressDialog.setTitle("Uploading");
                mProgressDialog.setMessage("Please wait while we upload the image");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.show();

                Uri resultUri = result.getUri();

                File thumb_filePath = new File(resultUri.getPath());

                final String current_user_id = mCurrentUser.getUid();
                Bitmap thumb_bitmap = null;
                try {
                    thumb_bitmap = new Compressor(this)
                            .setMaxWidth(200)
                            .setMaxHeight(200)
                            .setQuality(75)
                            .compressToBitmap(thumb_filePath);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] thump_byte = baos.toByteArray();

                final StorageReference thumb_filepath = mImageStorage.child("profile_images").child("thumbs").child(current_user_id + ".jpg");

                final StorageReference filePath = mImageStorage.child("profile_images").child(current_user_id + ".jpg");

                filePath.putFile(resultUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {


                            UploadTask uploadTask = thumb_filepath.putBytes(thump_byte);

                            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if (!task.isSuccessful()) {
                                        throw task.getException();
                                    }

                                    // Continue with the task to get the download URL
                                    return filePath.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()) {
                                        Uri downloadUri = task.getResult();
                                        Uri thumb_downloadUri = task.getResult();

                                        Map updateMap = new HashMap();
                                        updateMap.put("image", downloadUri.toString());
                                        updateMap.put("thumb_image", thumb_downloadUri.toString());

                                        mUserDatabase.updateChildren(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    mProgressDialog.dismiss();
                                                    Toast.makeText(SettingsActivity.this, "Success uploading", Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        });
                                    } else {
                                        // Handle failures
                                        // ...
                                        mProgressDialog.dismiss();
                                        Toast.makeText(SettingsActivity.this, "Error in uploading thumbnail", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(SettingsActivity.this, "Error in uploading image", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

}

package com.example.hackthonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.View.OnClickListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.widget.EditText;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.UUID;


public class WelcomeActivity extends AppCompatActivity {
    private String username;
    private EditText usernameText;
    private ImageView profileImage;
    private StorageReference profileImageRef;
    private String userId;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storagereference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        username = getIntent().getStringExtra("username");
        profileImage = findViewById(R.id.editProfileImage);
        usernameText = findViewById(R.id.editUserName);
        profileImageRef = FirebaseStorage.getInstance().getReference().child("profileImages");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        usernameText.setText(username);
        storage = FirebaseStorage.getInstance();
        storagereference = storage.getReference();
        profileImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                choosePicture();
            }
        });
    }

        private void choosePicture() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, 1);
        }
        protected void onActivityResult( int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imageUri = data.getData();
                profileImage.setImageURI(imageUri);
                uploadPicture();
            }
        }

    private void uploadPicture() {
        final ProgressDialog pd= new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference mountainsRef = storagereference.child("images/"+ randomKey);

        mountainsRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                          @Override
                                          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                              pd.dismiss();
                                              Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG).show();
                                          }
                                      })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed",Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                        double progressPrecent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentages: " + (int)progressPrecent+ "%");
                    }
                });
    }


}



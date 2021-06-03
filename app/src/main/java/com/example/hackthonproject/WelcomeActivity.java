package com.example.hackthonproject;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


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
        storage= FirebaseStorage.getInstance();
        storagereference= storage.getReference();
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
            }
        }

    }



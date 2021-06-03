package com.example.hackthonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.ContactsContract;
import android.util.Log;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.UUID;


public class WelcomeActivity extends AppCompatActivity {
    private static String username;
    private TextView usernameText;
    private ImageView profileImage;
    private ImageView realProfileImage;
    private StorageReference profileImageRef;
    private String userId;
    public Uri imageUri;
    private FirebaseStorage storage;
    private StorageReference storagereference;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private String phoneNumber;
    private EditText phoneNumberText;
    private ImageButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        username = getIntent().getStringExtra("username");
        profileImage = findViewById(R.id.editProfileImage);
        realProfileImage = findViewById(R.id.profileImage);
        usernameText = findViewById(R.id.textUserName);
        next=findViewById(R.id.welcome_next);
        profileImageRef = FirebaseStorage.getInstance().getReference().child("profileImages");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        username = getIntent().getStringExtra("username");
        usernameText.setText(username);
        phoneNumberText = findViewById(R.id.editTextPhone);
        storage = FirebaseStorage.getInstance();
        storagereference = storage.getReference();
        profileImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                choosePicture();
            }
        });
        next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = String.valueOf(phoneNumberText.getText());
                updatePhoneNumber();
                ToAddCoursesActivity();
            }
        });
    }

    private void ToAddCoursesActivity() {
        startActivity(new Intent(this,AddCourses.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userId = user.getUid();
    }

    private void choosePicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            realProfileImage.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading Image...");
        pd.show();
        final String randomKey = UUID.randomUUID().toString();
        StorageReference mountainsRef = storagereference.child("images/" + randomKey);

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
                        Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                        double progressPrecent = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                        pd.setMessage("Percentages: " + (int) progressPrecent + "%");
                    }
                });
    }

    private void updatePhoneNumber() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance("https://hackthonproject-1d1d6-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        rootRef.child("Users").child(userId).child("phoneNumber").setValue(phoneNumber);
        rootRef.child("Users").child(userId).child("profileImage").setValue(imageUri.toString());

    }
}
package com.example.hackthonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;
    private EditText userEmail,userPassword,username;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        userEmail=findViewById(R.id.inputEmailRegister);
        userPassword=findViewById(R.id.inputPasswordRegister);
        username=findViewById(R.id.inputUsernameRegister);

        mAuth=FirebaseAuth.getInstance();
        rootRef= FirebaseDatabase.getInstance("https://hackthonproject-1d1d6-default-rtdb.europe-west1.firebasedatabase.app/").getReference();

        loadingBar=new ProgressDialog(this);

    }

    public void onRegister(View v){
        String email=userEmail.getText().toString();
        String password=userPassword.getText().toString();
        final String name=username.getText().toString();

        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"enter email..",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"enter password..",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"enter username..",Toast.LENGTH_SHORT).show();
            return;
        }
        // no empty fields
        loadingBar.setTitle("Creating New Account");
        loadingBar.setCanceledOnTouchOutside(true);
        loadingBar.show();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadingBar.dismiss();
                if(task.isSuccessful()){
                    //create database
                    String userID=mAuth.getCurrentUser().getUid();

                    rootRef.child("Users").child(userID).child("username").setValue(name);
                    rootRef.child("Users").child(userID).child("firstTime").setValue("yes");


                    Toast.makeText(SignupActivity.this,"Account created successfully",Toast.LENGTH_LONG).show();

                    ToLoginActivity();
                    finish();
                }else{
                    Toast.makeText(SignupActivity.this,"Error: "+task.getException().toString(),Toast.LENGTH_LONG).show();
                }

            }

        });


    }

    private void ToLoginActivity() {
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}
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
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

public class WelcomeActivity extends IntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);



        Log.d("Debug","gggggggggggggggggggggggg");
        addSlide(new FragmentSlide.Builder()
                .background(R.color.teal_200)
                .backgroundDark(R.color.teal_700)
                .fragment(R.layout.fragment_first, R.style.Theme_Design_Light)
                .build());
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new Add_Courses().commit());

        Log.d("Debug","hhhhhhhhhhhhhhhhhhhhhhhhh");
    }


}
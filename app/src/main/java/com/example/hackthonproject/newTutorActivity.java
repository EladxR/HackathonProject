package com.example.hackthonproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class newTutorActivity extends AppCompatActivity {

    private EditText titleText;
    private String course;
    private EditText availability;
    private EditText descriptionText;
    private EditText price;
    private String username;
    private EditText locationText;
    FirebaseAuth mAuth;
    FirebaseUser user;
    MaterialSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);
        titleText=findViewById(R.id.EditTextTitle);
        descriptionText=findViewById(R.id.EditTextDescription);
        availability=findViewById(R.id.editTextAvailability);
        locationText= findViewById(R.id.editTextLocation);
        price = findViewById(R.id.editTextPrice);

        spinner = (MaterialSpinner) findViewById(R.id.newRequestSpinnerChooseCourse);
        spinner.setItems("Choose Course","Calculus 1", "Linear Algebra 1", "Calculus 2", "Linear Algebra 2", "Algorithms",
                "Data Structures",
                "Discrete mathematics",
                "Software 1",
                "Statistics");
        course = "Choose Course"; //first course
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                course = item;

            }
        });


        username=getIntent().getStringExtra("username");

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
    }

    public void onAddRequest(View v){
        if (titleText.getText().toString().equals("")){
            Toast.makeText(this,"please enter title",Toast.LENGTH_SHORT).show();
            return;
        }
        if(course.equals("Choose Course")){
            Toast.makeText(this,"please choose course",Toast.LENGTH_SHORT).show();
            return;
        }

        //create tutors and save in database
        //Request myRequest=new Request(titleText.getText().toString(),course,dateText.getText().toString(),timeText.getText().toString(),descriptionText.getText().toString(),username);
        DatabaseReference rootRef= FirebaseDatabase.getInstance("https://hackthonproject-1d1d6-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        String myKey= UUID.randomUUID().toString();
        rootRef.child("Tutors").child(course).child("tutors "+myKey).child("title").setValue(titleText.getText().toString());
        rootRef.child("Tutors").child(course).child("tutors "+myKey).child("course").setValue(course);
        rootRef.child("Tutors").child(course).child("tutors "+myKey).child("availability").setValue(availability);
        rootRef.child("Tutors").child(course).child("tutors "+myKey).child("description").setValue(descriptionText.getText().toString());
        rootRef.child("Tutors").child(course).child("tutors "+myKey).child("price").setValue(price);
        rootRef.child("Tutors").child(course).child("tutors "+myKey).child("username").setValue(username);
        rootRef.child("Tutors").child(course).child("tutors "+myKey).child("userID").setValue(user.getUid());
        rootRef.child("Tutors").child(course).child("tutors "+myKey).child("location").setValue(locationText.getText().toString());
        finish();



    }
}
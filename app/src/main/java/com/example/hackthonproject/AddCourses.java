package com.example.hackthonproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class AddCourses extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference rootRef;
    String course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        course="";

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinnerChooseCourse);
        spinner.setItems("Calculus", "Linear algebra ", "Algorithms");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                //Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                course=item;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth= FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser();
        rootRef= FirebaseDatabase.getInstance("https://hackthonproject-1d1d6-default-rtdb.europe-west1.firebasedatabase.app/").getReference();


    }

    public void onAddCourse(View v){
        //String course= String.valueOf(courseText.getText());
        if(course.equals("")){
            Toast.makeText(AddCourses.this, "please choose course first", Toast.LENGTH_LONG).show();
        }else {
            rootRef.child("Users").child(user.getUid()).child("course").setValue(course);
            Toast.makeText(AddCourses.this, course, Toast.LENGTH_LONG).show();
        }

    }


}
package com.example.hackthonproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddCourses extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;
    DatabaseReference rootRef;
    List<String> chosenCourses;
    ListView listViewCourses;
    ArrayAdapter<String> lvAdapter;
    String course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        listViewCourses=findViewById(R.id.ListViewCourses);
        chosenCourses=new ArrayList<>();
        lvAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,chosenCourses);
        listViewCourses.setAdapter(lvAdapter);

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.spinnerChooseCourse);
        spinner.setItems("Calculus", "Linear algebra ", "Algorithms","1","2","3","4","5","6","7");
        course="Calculus"; //first course
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                course =item;

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

    public void onAddCourse(View v){ // added selected course
        if(chosenCourses.contains(course)){
            Toast.makeText(AddCourses.this,"course already chosen",Toast.LENGTH_SHORT).show();
        }else {
            chosenCourses.add(course);
            lvAdapter.notifyDataSetChanged();
        }
       /* if(course.equals("")){
            Toast.makeText(AddCourses.this, "please choose course first", Toast.LENGTH_LONG).show();
        }else {
            rootRef.child("Users").child(user.getUid()).child("course").setValue(course);
            Toast.makeText(AddCourses.this, course, Toast.LENGTH_LONG).show();
        }*/

    }


    public void onFinish(View view) {
        //TODO finish activity to MAIN
        rootRef.child("Users").child(user.getUid()).child("courses").setValue(chosenCourses);
        rootRef.child("Users").child(user.getUid()).child("courses").setValue(chosenCourses);

    }
}
package com.example.hackthonproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Date;

public class NewRequest extends AppCompatActivity {

    private EditText titleText;
    private String course;
    private EditText dateText;
    private EditText timeText;
    private EditText descriptionText;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);
        titleText=findViewById(R.id.EditTextTitle);
        descriptionText=findViewById(R.id.EditTextDescription);

        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.newRequestSpinnerChooseCourse);
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

    public void onAddRequest(View v){
        if (titleText.getText().toString().equals("")){
            Toast.makeText(this,"please enter title",Toast.LENGTH_SHORT).show();
            return;
        }
        if(course.equals("Choose Course")){
            Toast.makeText(this,"please choose course",Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO : check date and time entered

        //create request and save in database
        Request myRequest=new Request(titleText.getText().toString(),course,dateText.getText().toString(),timeText.getText().toString(),descriptionText.getText().toString(),username);
        DatabaseReference rootRef= FirebaseDatabase.getInstance("https://hackthonproject-1d1d6-default-rtdb.europe-west1.firebasedatabase.app/").getReference();
        rootRef.child("Requests").child(course).push().setValue(myRequest);

    }
}
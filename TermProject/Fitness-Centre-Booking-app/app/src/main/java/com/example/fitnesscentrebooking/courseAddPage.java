package com.example.fitnesscentrebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

public class courseAddPage extends AppCompatActivity {
    TextView textDescription;
    TextView textName;
    TextView textCapacity;
    TextView textTime;
    TextView textDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add_page);
        textDescription = findViewById(R.id.textDescripton_editCourse);
        textName = findViewById(R.id.textcourseName_editcourse);
        textTime = findViewById(R.id.editTextTime_editCourse);
        textDate = findViewById(R.id.editTextDate_editcourse);
        textCapacity = findViewById(R.id.textCapacity_editCourse);
    }

    public void addData(View view) {
        String key = FirebaseDatabase.getInstance().getReference().push().getKey();
        String name = textName.getText().toString();
        String description = textDescription.getText().toString();
        String time = textTime.getText().toString();
        String date = textDate.getText().toString();
        String capacity = textCapacity.getText().toString().trim();
        System.out.println(key+"Key from the addData");

        Course newCourse = new Course(name, description, date, time, Integer.parseInt(capacity), key);
        FirebaseDatabase.getInstance().getReference().child("courses").child(key).setValue(newCourse);
       finish();
    }

    public void cancelprocess(View view) {
        finish();
    }

    public void editCourse(View view){

    }

}
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
    static String key2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add_page);
        textDescription = findViewById(R.id.textDescripton_addCourse);
        textName = findViewById(R.id.textcourseName_addCourse);
        textTime = findViewById(R.id.editTextTime_addCourse);
        textDate = findViewById(R.id.editTextDate_addCourse);
        textCapacity = findViewById(R.id.textCapacity_addCourse);
    }

    public void addData(View view) {
        String key = FirebaseDatabase.getInstance().getReference().push().getKey();
        String name = textName.getText().toString();
        String description = textDescription.getText().toString();
        String time = textTime.getText().toString();
        String date = textDate.getText().toString();
        String capacity = textCapacity.getText().toString().trim();
        System.out.println(key+"Key from the addData");
        if(key2.equals("")){
            Course newCourse = new Course(name, description, date, time, Integer.parseInt(capacity), key);
            FirebaseDatabase.getInstance().getReference().child("courses").child(key).setValue(newCourse);
        }else {
            Course newCourse = new Course(name, description, date, time, Integer.parseInt(capacity), key);
            FirebaseDatabase.getInstance().getReference().child("courses").child(key2).setValue(newCourse);
        }
        finish();
    }

    public void cancelprocess(View view) {
        finish();
    }



}
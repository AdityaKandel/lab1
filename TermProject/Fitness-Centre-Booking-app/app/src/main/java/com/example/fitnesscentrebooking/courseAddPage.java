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
        textDescription = findViewById(R.id.textDescripton);
        textName = findViewById(R.id.textcourseName);
        textTime = findViewById(R.id.editTextTime);
        textDate = findViewById(R.id.editTextDate);
        textCapacity = findViewById(R.id.textCapacity_Addcourse);
    }

    public void addData(View view) {
        String key = FirebaseDatabase.getInstance().getReference().push().getKey();
        String name = textName.getText().toString();
        String description = textDescription.getText().toString();
        String time = textTime.getText().toString();
        String date = textDate.getText().toString();
        String capacity = textCapacity.getText().toString().trim();

        Course newCourse = new Course(name, description, date, time, Integer.parseInt(capacity));
        FirebaseDatabase.getInstance().getReference().child("courses").child(key).setValue(newCourse);
        setContentView(R.layout.activity_main_page);
    }

    public void cancelprocess(View view) {
      System.out.println("Look you can find it");
    }
}
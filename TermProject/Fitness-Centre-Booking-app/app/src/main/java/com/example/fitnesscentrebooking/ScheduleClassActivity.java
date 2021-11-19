package com.example.fitnesscentrebooking;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScheduleClassActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    User user;
    Spinner difficulty_dropdown;
    TextView dateText;
    TextView timeText;
    TextView capacityText;
    String difficulty;
    String courseName;
    String id;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_class_activity);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            courseName = extras.getString("courseName");
            System.out.println(courseName);
            id = extras.getString("key");

        }

        user =  LoginPage.getUser();
      //  System.out.println("is it instance of 1" + (LoginPage.getUser() instanceof User));
         dateText = findViewById(R.id.dateField_ScheduleClass);
         timeText= findViewById(R.id.timeField_ScheduleClass);
         capacityText = findViewById(R.id.capacityField_ScheduleClass);



        difficulty_dropdown = (Spinner) findViewById(R.id.Ddifficultyspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.difficulty_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        difficulty_dropdown.setAdapter(adapter);
        difficulty_dropdown.setOnItemSelectedListener(this);
    }


    public void cancelprocess(View view) {
        finish();
    }

    public void ScheduleClass(View view){
        DateFormat givenFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat outputformat = new SimpleDateFormat("MMMM dd, yyyy");
        String date = "";
        try {
            Date dateformated = givenFormat.parse((dateText.getText().toString()));
            date = outputformat.format(dateformated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String time = timeText.getText().toString().trim();
        int Capacity = Integer.parseInt(capacityText.getText().toString().trim());



        System.out.println("course added");
        if(id!=null){
            System.out.println(id+"adding course");
            Course newCourse = new Course(courseName,date, time, difficulty,Capacity, LoginPage.getUser().getUsername(), id);
            FirebaseDatabase.getInstance().getReference().child("scheduledClass").child(id).child("class").setValue(newCourse);
        }else{
            String key =  FirebaseDatabase.getInstance().getReference().push().getKey();;
            Course newCourse = new Course(courseName,date, time, difficulty,Capacity, LoginPage.getUser().getUsername(), key);
            FirebaseDatabase.getInstance().getReference().child("scheduledClass").child(key).child("class").setValue(newCourse);
        }
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        difficulty = difficulty_dropdown.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        difficulty = "Beginner";
    }
}

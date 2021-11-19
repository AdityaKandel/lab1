package com.example.fitnesscentrebooking;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ScheduleClassActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    User user;
    Spinner difficulty_dropdown;
    TextView dateText;
    TextView timeText;
    TextView capacityText;
    String difficulty;
    String courseName;
    String id;
    List<Course> courseList;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_class_activity);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            courseName = extras.getString("courseName");
            System.out.println(courseName);
            id = extras.getString("key");
        }
        courseList =new ArrayList<>();

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

        String collidedUsername  = find(date);

        if(id!=null){
                System.out.println(id+"adding course");
                Course newCourse = new Course(courseName,date, time, difficulty,Capacity, LoginPage.getUser().getUsername(), id);
                FirebaseDatabase.getInstance().getReference().child("scheduledClass").child(id).child("class").setValue(newCourse);
                finish();
            }else{
                if(collidedUsername.equals("")){
                String key =  FirebaseDatabase.getInstance().getReference().push().getKey();;
                Course newCourse = new Course(courseName,date, time, difficulty,Capacity, LoginPage.getUser().getUsername(), key);
                FirebaseDatabase.getInstance().getReference().child("scheduledClass").child(key).child("class").setValue(newCourse);
                finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Schedule conflict with "+collidedUsername,Toast.LENGTH_SHORT).show();
                }
            }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        difficulty = difficulty_dropdown.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        difficulty = "Beginner";
    }
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase.getInstance().getReference("scheduledClass").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Course course = postSnapshot.child("class").getValue(Course.class);
                    System.out.println(postSnapshot.child("class").getValue(Course.class).toString());
                    if (course.getName().equals(courseName)) {
                        System.out.println("+course.getName()" + " mycourse name:"+courseName);
                        courseList.add(course);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
    public String find(String date){
        for (int i = 0; i < courseList.size(); i++) {
            if(courseList.get(i).getDate().equals(date)){
                return courseList.get(i).getUserName();
            }
        }
        return "";
    }

    public void validateTextField(){
        //todo
    }
}

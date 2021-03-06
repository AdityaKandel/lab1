package com.example.fitnesscentrebooking;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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


public class ScheduleAddClassActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    User user;
    Spinner difficulty_dropdown;
    TextView dateText;
    TextView StarttimeText;
    TextView EndtimeText;
    TextView capacityText;
    String difficulty;
    String courseName;
    String id;
    List<Course> courseList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.schedule_class_activity);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            courseName = extras.getString("courseName");
            System.out.println(courseName);
            id = extras.getString("key");
        }
        courseList =new ArrayList<>();

        user =  LoginPage.getUser();
        dateText = findViewById(R.id.dateField_ScheduleClass);
        StarttimeText= findViewById(R.id.StarttimeField_ScheduleClass2);
        EndtimeText= findViewById(R.id.EndtimeField_ScheduleClass);

        capacityText = findViewById(R.id.capacityField_ScheduleClass);

        if(id!=null){
            TextView title = (TextView) findViewById(R.id.title_ScheduleClass_Activity);
            Button scheduleBtn = (Button) findViewById(R.id.schedule_btn) ;
            scheduleBtn.setText("Save");
            title.setText("Edit Class");
        }

        difficulty_dropdown = (Spinner) findViewById(R.id.Ddifficultyspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.difficulty_levels, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        difficulty_dropdown.setAdapter(adapter);
        difficulty_dropdown.setOnItemSelectedListener(this);
    }

    public void cancelprocess(View view) {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void ScheduleClass(View view){
        DateFormat givenFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat outputformat = new SimpleDateFormat("MMM EEEE, yyyy");
        String date = "";
        try {
            Date dateformated = givenFormat.parse((dateText.getText().toString()));
            date = outputformat.format(dateformated);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String startTime = StarttimeText.getText().toString().trim();
        String Capacity = capacityText.getText().toString().trim();
        String endTime = EndtimeText.getText().toString().trim();
        String collidedUsername  = find(date);
    if(validateTextField(dateText, StarttimeText, capacityText,EndtimeText )){
        if(id!=null){
                System.out.println(id+"adding course");
                Course newCourse = new Course(courseName,date, startTime,endTime, difficulty,Integer.parseInt(Capacity), LoginPage.getUser().getUsername(), id, 0);
                FirebaseDatabase.getInstance().getReference().child("scheduledClass").child(id).child("class").setValue(newCourse);
                finish();
            }else{
                if(collidedUsername.equals("")){
                String key =  FirebaseDatabase.getInstance().getReference().push().getKey();;
                Course newCourse = new Course(courseName,date, startTime,endTime, difficulty,Integer.parseInt(Capacity), LoginPage.getUser().getUsername(), key, 0);
                FirebaseDatabase.getInstance().getReference().child("scheduledClass").child(key).child("class").setValue(newCourse);
                finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Schedule conflict with "+collidedUsername,Toast.LENGTH_SHORT).show();
                }
            }}
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean validateTextField(TextView dateTextView, TextView timeTextView, TextView capacityTextView, TextView endtimeText){
        //todo
        String date  = dateTextView.getText().toString().trim();
        String startTime = timeTextView.getText().toString().trim();
        String capacity = capacityTextView.getText().toString().trim();
        String endTime = endtimeText.getText().toString().trim();
        //Check if the feilds are empty
        if(startTime.equals("") || date.equals("")|| capacity.equals("") || endTime.equals("")){
            return false;
        }
        if(Integer.parseInt(capacity)<=0){
            capacityText.setError("Invalid Capacity limit");
            return false;
        }
        //validate StartTime
        String[] splitedTime = startTime.trim().split(":");
        System.out.println((splitedTime.length>2)+" time has extra :" + (startTime.trim().toString().chars().filter(ch -> ch == ':').count()==1));
        if(splitedTime.length>2){
            timeTextView.setError("Invalid Time");
            return false;
        }
        System.out.println((Integer.parseInt(splitedTime[0])>12 || Integer.parseInt(splitedTime[1])>59)+" time is not right");
        if(Integer.parseInt(splitedTime[0])>12 || Integer.parseInt(splitedTime[1])>59 || startTime.trim().toString().chars().filter(ch -> ch == ':').count()>1){
            timeTextView.setError("Invalid Time");
            return false;
        }

        //validate StartTime
        String[] splitedENDTime = endTime.trim().split(":");
        if(splitedENDTime.length>2){
            endtimeText.setError("Invalid Time");
            return false;
        }
        if(Integer.parseInt(splitedENDTime[0])>12 || Integer.parseInt(splitedENDTime[1])>59 || endTime.trim().toString().chars().filter(ch -> ch == ':').count()>1){
            endtimeText.setError("Invalid Time");
            return false;
        }
        if(Integer.parseInt(splitedTime[0])>Integer.parseInt(splitedENDTime[0]) || (Integer.parseInt(splitedTime[0])==Integer.parseInt(splitedENDTime[0]) && Integer.parseInt(splitedTime[1])>Integer.parseInt(splitedENDTime[1]))){
            timeTextView.setError("Start time cannot be greater than End time");
            return false;
        }


        String DATE_FORMAT = "MM/dd/yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);

        } catch (ParseException e) {
            dateTextView.setError("Invalid Date");
            System.out.println("Date is incorrect");
            return false;
        }


        return true;
    }
}

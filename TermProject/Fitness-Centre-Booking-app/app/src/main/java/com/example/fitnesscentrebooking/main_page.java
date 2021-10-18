package com.example.fitnesscentrebooking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


import android.content.Intent;
import android.os.Bundle;
import android.sax.RootElement;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class main_page extends AppCompatActivity {
    protected User user;
    protected ArrayList<Course> courses;
    protected TextView text_Username;
    protected TextView text_Role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        user = MainActivity.getUser();
        text_Username = (TextView) findViewById(R.id.textUserName_main);
        text_Role = (TextView) findViewById(R.id.textRole_main);

        setUserData(user.getUsername(), user.getRole());
        updateUI();
        System.out.println("getting value");



    }


    public void updateUI() {
        switch (user.getRole()) {
            case "member":
                Button editCousrse = (Button) findViewById(R.id.editCourse);
                editCousrse.setVisibility(View.GONE);
                break;
            case "admin":
                editCousrse = (Button) findViewById(R.id.editCourse);
                editCousrse.setVisibility(View.VISIBLE);
                break;
            case "instructor":
                break;
        }
    }



    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            courses = (ArrayList)dataSnapshot.child("Coursesdata").getValue();
            if(courses==null){
                System.out.println("none found");
                courses = new ArrayList<>(0);
            }
            OnUpdateClassUI();

        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message
        }
    };

    public void setUserData(String name, String role){
        text_Username.setText("Username: "+name);
        text_Role.setText("Role: "+role);
        FirebaseDatabase.getInstance().getReference().child("courses").addValueEventListener(listener);

    }

    public void onAddCourse(View view){
        Course course = new Course();
        courses.add(course);
        FirebaseDatabase.getInstance().getReference().child("courses").child("Coursesdata").setValue(courses);
        OnUpdateClassUI();
    }


    public void onCreateCourse(){
        Course newCourse = new Course();

    }

    public void enrollCourse(View view) {
        Course course = new Course();
        courses.add(course);
        OnUpdateClassUI();
       // user.setCourses(course);
    }

    public void OnUpdateClassUI(){
        LinearLayout courseList = findViewById(R.id.ListCourses);
        int childCount = courseList.getChildCount();
        for (int i=0;i<(courses.size()-childCount);i++) {

                     System.out.println("new section to add to ");
            LinearLayout.LayoutParams buttons2Parm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    (int) (200 * (getResources().getDisplayMetrics().density)), 1.0f);
            Button newButton2 = new Button(this);
            newButton2.setLayoutParams(buttons2Parm);
                     LinearLayout newRow = new LinearLayout(this);
                     newRow.addView(newButton2);
                     newRow.setOrientation(LinearLayout.HORIZONTAL);
                     newRow.addView(createNewCourseUI());
                     courseList.addView(newRow);

        }
    }

    public CardView createNewCourseUI(){
        LinearLayout.LayoutParams cardViewParm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
        LinearLayout.LayoutParams buttonsParm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (200 * (getResources().getDisplayMetrics().density)), 1.0f);

        buttonsParm.setMargins(10,0,10,0);
        Button newButton = new Button(this);
        newButton.setLayoutParams(buttonsParm);
        CardView newCourse = new CardView(this);
        newCourse.setLayoutParams(cardViewParm);
        newCourse.addView(newButton);
        return newCourse;
    }
}
package com.example.fitnesscentrebooking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CoursePage extends AppCompatActivity{
    protected User user;
    FloatingActionButton addcourse;
    DatabaseReference courseDatabaseRef;
    List courseList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.class_page_activity);
        user = LoginPage.getUser();
        addcourse = (FloatingActionButton) findViewById(R.id.addCourse);
        updateUI();
        Navigation navi = new Navigation();
        navi.setNavigationView(this);
        courseDatabaseRef = FirebaseDatabase.getInstance().getReference("courses");
        courseList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.recycleView);
    }

    public void updateUI() {
        switch (user.getroleNum()) {
            case "2":
                addcourse.setVisibility(View.GONE);
                break;
            case "0":
                addcourse.setVisibility(View.VISIBLE);
                break;
            case "1":
                addcourse.setVisibility(View.GONE);
                break;
        }
    }

    ActivityResultLauncher<Intent> intentLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){

                }
            });


    public void onAddCourse(View view){
        System.out.println("add courses");
       Intent intent = new Intent(getApplicationContext(), courseAddPage.class);
        intentLaucher.launch(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();

        courseDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Course course = postSnapshot.getValue(Course.class);
                    courseList.add(course);
                }
                CourseList productAdapter = new CourseList(CoursePage.this, courseList);
                listView.setAdapter(productAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
     public void ShecduleClass(){
            //display course creating class

     }

}
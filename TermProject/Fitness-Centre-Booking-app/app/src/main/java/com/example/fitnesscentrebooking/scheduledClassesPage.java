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
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class scheduledClassesPage extends AppCompatActivity{
    protected User user;
    DatabaseReference courseDatabaseRef;
    List courseList;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.schedule_page_activity);
        user = LoginPage.getUser();
        updateUI();
        Navigation navi = new Navigation();
        navi.setNavigationView(this);
        courseDatabaseRef = FirebaseDatabase.getInstance().getReference("scheduledClass");
        courseList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.recycleView);

        SearchView searchBar = findViewById(R.id.searchBar);
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                find(s);
                return false;
            }
        });
    }

    public void updateUI() {
        switch (user.getroleNum()) {
            case "2":
                break;
            case "0":
                break;
            case "1":
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


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseDatabase.getInstance().getReference("scheduledClass").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courseList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Course course = postSnapshot.child("class").getValue(Course.class);
                    System.out.println(course.getId() + "testing" + course.getUserName().toLowerCase());
                        courseList.add(course);
                }
                scheduleList productAdapter = new scheduleList(scheduledClassesPage.this, courseList);
                listView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void find(String searchValue) {
        if(!searchValue.equals("")) {
            FirebaseDatabase.getInstance().getReference("scheduledClass").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    courseList.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        Course course = postSnapshot.child("class").getValue(Course.class);
                        System.out.println(course.getId() + "testing" + course.getUserName().toLowerCase());
                        if (course.getUserName().toLowerCase().contains(searchValue.toLowerCase())) {
                            courseList.add(course);
                        }
                    }
                    scheduleList productAdapter = new scheduleList(scheduledClassesPage.this, courseList);
                    listView.setAdapter(productAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            onStart();
        }
    }
}
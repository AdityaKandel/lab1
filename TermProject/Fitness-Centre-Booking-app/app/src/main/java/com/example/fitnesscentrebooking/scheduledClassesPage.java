package com.example.fitnesscentrebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class scheduledClassesPage extends AppCompatActivity{
    private User user;
    private DatabaseReference courseDatabaseRef;
    private List courseList;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.schedule_page_activity);
        user = LoginPage.getUser();

        Navigation navi = new Navigation();
        navi.setNavigationView(this);
        courseDatabaseRef = FirebaseDatabase.getInstance().getReference("scheduledClass");
        courseList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.recycleView_Myclass);

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
    private void find(String searchValue) {
        if(!searchValue.equals("")) {
            FirebaseDatabase.getInstance().getReference("scheduledClass").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    courseList.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        Course course = postSnapshot.child("class").getValue(Course.class);
                        if ((course.getUserName().toLowerCase().contains(searchValue.toLowerCase())&& LoginPage.getUser().getroleNum().equals("1")) ||
                                course.getName().toLowerCase().contains(searchValue.toLowerCase())||
                                (course.getDate().toLowerCase().contains(searchValue.toLowerCase()) && LoginPage.getUser().getroleNum().equals("2"))) {
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
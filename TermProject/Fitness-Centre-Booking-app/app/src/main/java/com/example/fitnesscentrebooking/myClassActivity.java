package com.example.fitnesscentrebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class myClassActivity extends AppCompatActivity {
    private ListView listView;
      ArrayList<Course> enrolledList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
        Navigation navi = new Navigation();
        navi.setNavigationView(this);

        listView= findViewById(R.id.recycleView_Myclass);
        enrolledList = new ArrayList<>();
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

    private void find(String searchValue) {

        if(!searchValue.equals("")) {
            FirebaseDatabase.getInstance().getReference("enrolledClass").child("class").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    enrolledList.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        Course course = postSnapshot.getValue(Course.class);
                        if (course.getUserName().equals(LoginPage.getUser().getUsername()) && (course.getDate().toLowerCase().contains(searchValue) || course.getName().toLowerCase().contains(searchValue))) {
                            enrolledList.add(course);
                        }
                    }
                    enrollList productAdapter = new enrollList(myClassActivity.this, enrolledList);
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


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("enrolledClass").child("class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                enrolledList.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Course course = postSnapshot.getValue(Course.class);
                    if(course.getUserName().equals(LoginPage.getUser().getUsername())){
                        enrolledList.add(course);
                    }
                }
                System.out.println(enrolledList.size()+"enrolled class size");
                enrollList productAdapter = new enrollList(myClassActivity.this, enrolledList);
                listView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
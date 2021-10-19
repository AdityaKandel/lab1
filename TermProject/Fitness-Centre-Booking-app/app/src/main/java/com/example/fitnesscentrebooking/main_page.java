package com.example.fitnesscentrebooking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class main_page extends AppCompatActivity {
    protected User user;
    //protected ArrayList<Course> courses;
    protected TextView text_Username;
    private DatabaseReference mDatabase;
    protected TextView text_Role;
    RecyclerView recyclerView;
    FirebaseRecyclerOptions<Course> courses;
    FirebaseRecyclerAdapter<Course, CourserViewUi> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("courses");
        user = MainActivity.getUser();
        text_Username = (TextView) findViewById(R.id.textUserName_main);
        text_Role = (TextView) findViewById(R.id.textRole_main);
        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        setUserData(user.getUsername(), user.getRole());
        updateUI();
        System.out.println("getting value");
        OnUpdateClassUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null) {
            adapter.stopListening();
        }
    }

    public void updateUI() {
        switch (user.getRole()) {
            case "member":
                Button editCousrse = (Button) findViewById(R.id.addCourse);
                editCousrse.setVisibility(View.GONE);
                break;
            case "admin":
                editCousrse = (Button) findViewById(R.id.addCourse);
                editCousrse.setVisibility(View.VISIBLE);
                break;
            case "instructor":
                break;
        }
    }





    public void setUserData(String name, String role){
        text_Username.setText("Username: "+name);
        text_Role.setText("Role: "+role);
       // FirebaseDatabase.getInstance().getReference().child("courses").addValueEventListener(listener);

    }


    ActivityResultLauncher<Intent> addCourseLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        System.out.println("Cancelation of adding course");
                    }
                }

            });

    public void onAddCourse(View view){
      //  Intent intent = new Intent(getApplicationContext(), addcourse.class);
       // addCourseLauncher.launch(intent);
        setContentView(R.layout.activity_course_add_page);
    }


    public void removeCourse(View view){

    }
    public void enrollCourse(View view) {

    }

    public void OnUpdateClassUI(){
        courses = new FirebaseRecyclerOptions.Builder<Course>().setQuery(mDatabase,Course.class).build();
        adapter = new FirebaseRecyclerAdapter<Course, CourserViewUi>(courses) {
                @Override
                protected void onBindViewHolder(@NonNull CourserViewUi holder, int position, @NonNull Course model) {
                    holder.courseName.setText(model.getName());
                  //  holder.capacity.setText(model.getCapacity());
                    holder.description.setText(model.getDescription());
                    //holder.time.setText(model.getTime());
                }
                @NonNull
                @Override
                public CourserViewUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_view,parent, false);

                    return new CourserViewUi(v);
                }
            };
            adapter.startListening();
            recyclerView.setAdapter(adapter);
        }
}
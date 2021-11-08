package com.example.fitnesscentrebooking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomePage extends AppCompatActivity{
    protected User user;
    protected TextView text_Username;
    private DatabaseReference mDatabase;
    protected TextView text_Role;
    Button btn_listUser;
    FloatingActionButton addcourse;
    FirebaseRecyclerOptions<CourseType> courses;
    RecyclerView recyclerViewCourseList;
    FirebaseRecyclerAdapter<CourseType, CourseViewUi> adapterCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_page);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("courses");
        user = MainActivity.getUser();
        addcourse = (FloatingActionButton) findViewById(R.id.addCourse);
        recyclerViewCourseList = findViewById(R.id.recycleView);
        recyclerViewCourseList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        updateUI();
        Navigation navi = new Navigation();
        navi.setNavigationView(this);

    }

    public void updateUI() {
        switch (user.getRole()) {
            case "Member":
                addcourse.setVisibility(View.GONE);
                break;
            case "admin":
                viewCourseType();
                addcourse.setVisibility(View.VISIBLE);
                break;
            case "Instructor":
                addcourse.setVisibility(View.GONE);
                viewCourseType();
                break;
        }
    }

    public void setUserData(String name, String role){
        text_Username.setText("Welcome "+name.substring(0,1).toUpperCase()+name.substring(1));
        text_Role.setText("Role: "+role);
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


    public void viewUsers(View view){

            Intent intent = new Intent(getApplicationContext(), UserControlPage.class);
            intentLaucher.launch(intent);
    }
        public void viewCourseType(){
            FirebaseRecyclerView<CourseType, CourseViewUi> test = new FirebaseRecyclerView<>("courses", recyclerViewCourseList, R.layout.course_view, CourseType.class, CourseViewUi.class);
        }

     public void ShecduleClass(){
            //display course creating class
         ( (Instructor)user).addCourse();
     }

}
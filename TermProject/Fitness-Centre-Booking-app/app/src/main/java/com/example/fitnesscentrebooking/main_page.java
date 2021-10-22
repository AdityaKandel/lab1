package com.example.fitnesscentrebooking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class main_page extends AppCompatActivity {
    protected User user;
    protected TextView text_Username;
    private DatabaseReference mDatabase;
    protected TextView text_Role;
    Button btn_listUser;
    FirebaseRecyclerOptions<CourseType> courses;
    RecyclerView recyclerViewCourseList;
    FirebaseRecyclerAdapter<CourseType, CourserViewUi> adapterCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("courses");
        user = MainActivity.getUser();
        text_Username = (TextView) findViewById(R.id.textUserName_main);
        text_Role = (TextView) findViewById(R.id.textRole_main);
        recyclerViewCourseList = findViewById(R.id.recycleView);
        btn_listUser = findViewById(R.id.viewUsers_main_page);
        recyclerViewCourseList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        setUserData(user.getUsername(), user.getRole());
        updateUI();
        System.out.println("getting value");


    }

    public void updateUI() {
        switch (user.getRole()) {
            case "Member":
                Button editCousrse = (Button) findViewById(R.id.addCourse);
                editCousrse.setVisibility(View.GONE);
                btn_listUser.setVisibility(View.GONE);
                break;
            case "admin":
                OnUpdateClassUI();
                editCousrse = (Button) findViewById(R.id.addCourse);
                editCousrse.setVisibility(View.VISIBLE);
                btn_listUser.setVisibility(View.VISIBLE);
                break;
            case "Instructor":
                 editCousrse = (Button) findViewById(R.id.addCourse);
                editCousrse.setVisibility(View.GONE);
                btn_listUser.setVisibility(View.GONE);
                OnUpdateClassUI();
                break;
        }
    }

    public void setUserData(String name, String role){
        text_Username.setText("Welcome "+name.substring(0,1).toUpperCase()+name.substring(1));
        text_Role.setText("Role: "+role);
    }

    ActivityResultLauncher<Intent> addCourseLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        text_Username.setText(data.getStringExtra("username"));
                    }
                }
            });


    ActivityResultLauncher<Intent> editCourselauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        text_Username.setText(data.getStringExtra("username"));
                    }
                }
            });

    public void onAddCourse(View view){
       Intent intent = new Intent(getApplicationContext(), courseAddPage.class);
       addCourseLauncher.launch(intent);
    }

    public void OnUpdateClassUI(){

       /* https://firebaseopensource.com/projects/firebase/firebaseui-android/database/readme/* For recycler view*/
        courses = new FirebaseRecyclerOptions.Builder<CourseType>().setQuery(mDatabase, CourseType.class).build();
        adapterCourse = new FirebaseRecyclerAdapter<CourseType, CourserViewUi>(courses) {
            String key;
                @Override
                protected void onBindViewHolder(@NonNull CourserViewUi holder, int position, @NonNull CourseType model) {
                    holder.courseName.setText(model.getName());
                    holder.description.setText(model.getDescription());
                    holder.setKey(model.getId());
                }
                @NonNull
                @Override
                public CourserViewUi onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_view,parent, false);
                    return new CourserViewUi(v,key);
                }
            };
        adapterCourse.startListening();
        recyclerViewCourseList.setAdapter(adapterCourse);
        }

        public void viewUsers(View view){
            Intent intent = new Intent(getApplicationContext(), UserList.class);
          startActivity(intent);
        }

}
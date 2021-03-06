package com.example.fitnesscentrebooking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class courseAddPage extends AppCompatActivity {
    private TextView textDescription;
    private TextView textName;
    private TextView textTitle;
    private Button addBtn;
    static String key2;
    private CourseList list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_add_page);
        textDescription = findViewById(R.id.textDescripton_addCourse);
        textTitle = findViewById(R.id.title_addCourse);
        textName = findViewById(R.id.textcourseName_addCourse);
        addBtn= findViewById(R.id.add_addcourse);
        updateUI();
    }

    public void addData(View view) {
        boolean isValid = true;
        if(textName.getText().toString().equals("")){
            textName.setError("Fill out the Course name");
            isValid = false;
        }else if(textDescription.getText().toString().equals("")){textDescription.setError("Fill out the Description");isValid=false;}
        if(isValid) {
            String key = FirebaseDatabase.getInstance().getReference().push().getKey();
            String name = textName.getText().toString();
            String description = textDescription.getText().toString();
            if (key2 == null || key2.equals("")) {
                Course newCourse = new Course(name, description, key);
                FirebaseDatabase.getInstance().getReference().child("courses").child(key).setValue(newCourse);
                Toast.makeText(getApplicationContext(), "Course added", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println("editing: " + key2);
                Course newCourse = new Course(name, description, key2);
                FirebaseDatabase.getInstance().getReference().child("courses").child(key2).setValue(newCourse);
                Toast.makeText(getApplicationContext(), "Course Saved", Toast.LENGTH_SHORT).show();
                key2 = "";
            }
            finish();
        }
    }

    public void cancelprocess(View view) {
        finish();
        key2="";
    }

    public void updateUI(){
        if(key2==null || key2.equals("")){
            textTitle.setText("Add Course Type");
            addBtn.setText("Add");
        }else{
            textTitle.setText("Edit Course");
            addBtn.setText("Save");
        }
    }


}
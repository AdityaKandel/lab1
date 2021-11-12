package com.example.fitnesscentrebooking;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Random;

public class CourseList extends ArrayAdapter<Course> {
    private Activity context;
    List<Course> courseList;
    private String courseId;
    public CourseList(Activity context, List<Course> courses){
        super(context, R.layout.course_view,courses);
        this.context = context;
        this.courseList = courses;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
       View listViewItem = inflater.inflate(R.layout.course_view, null, true);

        TextView description = (TextView) listViewItem.findViewById(R.id.description);
        TextView name = (TextView) listViewItem.findViewById(R.id.courseName);
        Course course = courseList.get(position);

       //set values
        description.setText(course.getDescription());
        name.setText(course.getName());
        courseId = course.getId();
        //setBackground
        setGradientColor((ImageView) listViewItem.findViewById(R.id.background_courseView));

        //setButtonOperations;
        TextView editBtn = (TextView) listViewItem.findViewById(R.id.editCourse_courseview);
        TextView removeBtn = (TextView) listViewItem.findViewById(R.id.remove_courseview);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCourse();
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCourse();
            }
        });
        updateUI(listViewItem);

        return listViewItem;
    }


    public void setGradientColor(ImageView imageView){
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {colorpicker(), colorpicker()} );
        gd.setCornerRadius(100f);

        imageView.setBackground(gd);

    }
    public int colorpicker(){
        Random r = new Random();
        String[] colors = new String[]{ "#f2b705", "#f6b20b", "#faad12", "#fda718", "#ffa21e", "#ff9d24", "#ff9729", "#ff922f", "#ff8d34", "#ff8739", "#ff823e", "#ff7c44",
                "#ff7749", "#ff724e", "#ff6d53", "#ff6858", "#ff635d", "#ff5e62", "#ff5a67", "#ff566c", "#ff5272", "#ff4f77", "#ff4c7c", "#fd4981", "#f94785", "#f5458a",
                "#f0448f", "#eb4394", "#e64398", "#e0439c", "#da43a1", "#d444a5", "#cd45a8", "#c647ac", "#be48af", "#b64ab2", "#ae4bb5", "#a54db8", "#9c4eba", "#9250bc",
                "#cd9dfd", "#e199f4", "#f294ea", "#ff91de", "#ff8fd1", "#ff8ec3", "#ff8eb5", "#ff90a6", "#ff9498", "#ff998a", "#ffa07d", "#ffa772", "#ffae67", "#ffb65f", "#febe59" };
        return Color.parseColor(colors[r.nextInt(colors.length)]);
    }

    public void removeCourse(){
        System.out.println(courseId+"id of the course");
        FirebaseDatabase.getInstance().getReference().child("courses").child(courseId).removeValue();
        Toast.makeText(getContext(), "Course deleted",Toast.LENGTH_SHORT).show();

    }

    public void EditCourse(){
        Intent intent = new Intent(context, courseAddPage.class);
        courseAddPage.key2=courseId;
        context.startActivity(intent);
        Toast.makeText(getContext(), "Course deleted",Toast.LENGTH_SHORT).show();
    }
    public void updateUI(View context){
        TextView schedule = ((TextView) context.findViewById(R.id.enroll));

        switch (LoginPage.getUser().getroleNum()){
            case "2":
                ((TextView) context.findViewById(R.id.remove_courseview)).setVisibility(View.GONE);
                ((TextView) context.findViewById(R.id.editCourse_courseview)).setVisibility(View.GONE);
                break;
            case "1":
                ((TextView) context.findViewById(R.id.remove_courseview)).setVisibility(View.GONE);
                ((TextView) context.findViewById(R.id.editCourse_courseview)).setVisibility(View.GONE);
                schedule.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        scheduleClass();
                    }
                });
                break;
            case "0":
                schedule.setVisibility(View.GONE);

                break;
        }
    }
    public void scheduleClass(){
        Intent intent = new Intent(context, ScheduleClass.class);
        context.startActivity(intent);
    }
}
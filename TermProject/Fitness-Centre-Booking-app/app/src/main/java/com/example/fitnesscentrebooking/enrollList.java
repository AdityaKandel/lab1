package com.example.fitnesscentrebooking;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class enrollList extends ArrayAdapter<Course> {
    private Activity context;
    private List<Course> courseList;
    private String courseId;
    private Course Scheduledcourse;

    public enrollList(Activity context, List<Course> courses) {
        super(context, R.layout.schedule_class_view, courses);
        this.context = context;
        this.courseList = courses;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        System.out.println(" infalitng layout ");
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.schedule_class_view, null, true);

        TextView time = (TextView) listViewItem.findViewById(R.id.time_Schedule_view);
        TextView capacity = (TextView) listViewItem.findViewById(R.id.capacity_Schedule_view);
        TextView date = (TextView) listViewItem.findViewById(R.id.date_schedule_view2);
        TextView difficulty = (TextView) listViewItem.findViewById(R.id.difficulty_Schedule_view);
        TextView CourseName = (TextView) listViewItem.findViewById(R.id.className_shedule_class_view);
        Scheduledcourse = courseList.get(position);

        //set values
        time.setText("Time: " + Scheduledcourse.getTime());
        CourseName.setText(Scheduledcourse.getName());
        date.setText(Scheduledcourse.getDate());
        capacity.setText("Capacity: " + Integer.toString(Scheduledcourse.getCapacity()));
        difficulty.setText("Difficulty: " + Scheduledcourse.getDifficulty());
        courseId = Scheduledcourse.getId();
        //setBackground
        setGradientColor(listViewItem.findViewById(R.id.cardView_ScheduleView));

        //setButtonOperations;
        TextView editBtn = (TextView) listViewItem.findViewById(R.id.edit_Schedule_view);
        final TextView EnrollBtn = (TextView) listViewItem.findViewById(R.id.enroll_Schedule_view2);
        EnrollBtn.setTag(position);
        EnrollBtn.setText("Unenroll");
        EnrollBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.enrollbtn));
        TextView removeBtn = (TextView) listViewItem.findViewById(R.id.cancel_Schedule_view);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        EnrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enroll(position);
            }

        });
        updateUI(listViewItem);
        return listViewItem;
    }


    private void setGradientColor(CardView imageView) {
        int newColor = colorpicker();
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{newColor, newColor});
        gd.setCornerRadius(100f);

        imageView.setBackground(gd);

    }
    private void updateUI(View context) {
        context.findViewById(R.id.edit_Schedule_view).setVisibility(View.GONE);
        context.findViewById(R.id.cancel_Schedule_view).setVisibility(View.GONE);
    }
    private int colorpicker() {
        Random r = new Random();
        String[] colors = new String[]{"#f2b705", "#f6b20b", "#faad12", "#fda718", "#ffa21e", "#ff9d24", "#ff9729", "#ff922f", "#ff8d34", "#ff8739", "#ff823e", "#ff7c44",
                "#ff7749", "#ff724e", "#ff6d53", "#ff6858", "#ff635d", "#ff5e62", "#ff5a67", "#ff566c", "#ff5272", "#ff4f77", "#ff4c7c", "#fd4981", "#f94785", "#f5458a",
                "#f0448f", "#eb4394", "#e64398", "#e0439c", "#da43a1", "#d444a5", "#cd45a8", "#c647ac", "#be48af", "#b64ab2", "#ae4bb5", "#a54db8", "#9c4eba", "#9250bc",
                "#cd9dfd", "#e199f4", "#f294ea", "#ff91de", "#ff8fd1", "#ff8ec3", "#ff8eb5", "#ff90a6", "#ff9498", "#ff998a", "#ffa07d", "#ffa772", "#ffae67", "#ffb65f", "#febe59"};
        return Color.parseColor(colors[r.nextInt(colors.length)]);
    }

    private void enroll(int positon) {
                    FirebaseDatabase.getInstance().getReference("enrolledClass").child("class").child(courseList.get(positon).getId()).removeValue();

    }
}

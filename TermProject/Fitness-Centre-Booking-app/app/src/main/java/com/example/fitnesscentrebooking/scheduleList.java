package com.example.fitnesscentrebooking;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class scheduleList extends ArrayAdapter<Course> {
    private Activity context;
    private List<Course> courseList;
    private List<Course> enrolledCourse;
    private Course Scheduledcourse;


    public scheduleList(Activity context, List<Course> courses) {
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
        capacity.setText("Capacity: " + Integer.toString(Scheduledcourse.getEnrolled()) + "/" + Integer.toString(Scheduledcourse.getCapacity()));
        difficulty.setText("Difficulty: " + Scheduledcourse.getDifficulty());
        final String courseId = Scheduledcourse.getId();
        //setBackground
        setGradientColor(listViewItem.findViewById(R.id.cardView_ScheduleView));

        //setButtonOperations;
        TextView editBtn = (TextView) listViewItem.findViewById(R.id.edit_Schedule_view);

        final TextView EnrollBtn = (TextView) listViewItem.findViewById(R.id.enroll_Schedule_view2);
        EnrollBtn.setTag(position);
        TextView removeBtn = (TextView) listViewItem.findViewById(R.id.cancel_Schedule_view);
        EnrollBtn.setVisibility(View.GONE);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCourse(position);
            }
        });
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCourse(position);
            }
        });
        EnrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof scheduledClassesPage) {
                    enroll(position, EnrollBtn, listViewItem);
                } else if (context instanceof myClassActivity) {
                    enroll(courseId);
                }
            }

        });
        test2(listViewItem, position, EnrollBtn);


        return listViewItem;

    }

    private void enroll(String courseId) {
        FirebaseDatabase.getInstance().getReference("enrolledClass").child("class").child(courseId).removeValue();
    }


    private void setGradientColor(CardView imageView) {
        int newColor = colorpicker();
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{newColor, newColor});
        gd.setCornerRadius(100f);

        imageView.setBackground(gd);

    }

    private int colorpicker() {
        Random r = new Random();
        String[] colors = new String[]{"#f2b705", "#f6b20b", "#faad12", "#fda718", "#ffa21e", "#ff9d24", "#ff9729", "#ff922f", "#ff8d34", "#ff8739", "#ff823e", "#ff7c44",
                "#ff7749", "#ff724e", "#ff6d53", "#ff6858", "#ff635d", "#ff5e62", "#ff5a67", "#ff566c", "#ff5272", "#ff4f77", "#ff4c7c", "#fd4981", "#f94785", "#f5458a",
                "#f0448f", "#eb4394", "#e64398", "#e0439c", "#da43a1", "#d444a5", "#cd45a8", "#c647ac", "#be48af", "#b64ab2", "#ae4bb5", "#a54db8", "#9c4eba", "#9250bc",
                "#cd9dfd", "#e199f4", "#f294ea", "#ff91de", "#ff8fd1", "#ff8ec3", "#ff8eb5", "#ff90a6", "#ff9498", "#ff998a", "#ffa07d", "#ffa772", "#ffae67", "#ffb65f", "#febe59"};
        return Color.parseColor(colors[r.nextInt(colors.length)]);
    }

    private void removeCourse(int position) {
        FirebaseDatabase.getInstance().getReference().child("scheduledClass").child(courseList.get(position).getId()).removeValue();
        Toast.makeText(getContext(), "Course deleted", Toast.LENGTH_SHORT).show();

    }

    private void EditCourse(int position) {
        Intent intent = new Intent(context, ScheduleAddClassActivity.class);
        System.out.println(Scheduledcourse.getId() + "editing");
        intent.putExtra("key", Scheduledcourse.getId());
        intent.putExtra("courseName", Scheduledcourse.getName());
        context.startActivity(intent);
        Toast.makeText(getContext(), "Course edited", Toast.LENGTH_SHORT).show();
    }

    private void updateUI(View context, int position, final TextView enrolBTn) {
        switch (LoginPage.getUser().getroleNum()) {
            case "0":
                break;
            case "1":
                if (!LoginPage.getUser().getUsername().equals(courseList.get(position).getUserName())) {
                    context.findViewById(R.id.edit_Schedule_view).setVisibility(View.GONE);
                    context.findViewById(R.id.cancel_Schedule_view).setVisibility(View.GONE);
                }
                break;
            case "2":
                context.findViewById(R.id.edit_Schedule_view).setVisibility(View.GONE);
                context.findViewById(R.id.cancel_Schedule_view).setVisibility(View.GONE);
                enrolBTn.setVisibility(View.VISIBLE);
                if (  courseList.get(position) != null &&   courseList.get(position).getCapacity() ==   courseList.get(position).getEnrolled()) {
                    ((TextView) context.findViewWithTag(position)).setText("FULL CAPACITY");
                }
                test(position, enrolBTn, context);
                break;
        }
    }

    public void test(int position, final TextView enrollBtn, View listView) {
        for (Course postSnapshot : enrolledCourse) {
            Course newCourse = postSnapshot;
            if (newCourse != null && LoginPage.getUser().getUsername().equals(newCourse.getUserName()) && courseList.get(position).getName().equals(newCourse.getName()) && courseList.get(position).getTime().equals(newCourse.getTime())) {
                ((TextView) listView.findViewWithTag(position)).setBackground(ContextCompat.getDrawable(context, R.drawable.enrollbtn));
                ((TextView) listView.findViewWithTag(position)).setText("Unenroll");
                System.out.println("updating Ui for enroll btn");
            }

        }
    }

    public void test2(View context, int position, final TextView enrollBTn) {
        enrolledCourse = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("enrolledClass").child("class").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                enrolledCourse.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Course newCourse = postSnapshot.getValue(Course.class);
                    enrolledCourse.add(newCourse);
                }
                updateUI(context, position, enrollBTn);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void enroll(int position, final TextView EnrollBtn, View listView) {
        System.out.println("Schedule Activity");
        if (((TextView) listView.findViewWithTag(position)).getText().toString().equals("Unenroll")) {
            EnrollBtn.setBackground(ContextCompat.getDrawable(context, R.drawable.gradient));
            EnrollBtn.setText("Enroll");

            Course course = courseList.get(position);
            course.setEnrolled(course.getEnrolled() - 1);
            FirebaseDatabase.getInstance().getReference("scheduledClass").child(course.getId()).child("class").child("enrolled").setValue(course.getEnrolled());
            ((TextView) listView.findViewById(R.id.capacity_Schedule_view)).setText("Capacity: " + course.getEnrolled() + "/" + course.getCapacity());
            for (Course postSnapshot : enrolledCourse) {
                Course newCourse = postSnapshot;
                if (LoginPage.getUser().getUsername().equals(newCourse.getUserName()) && courseList.get(position).getName().equals(newCourse.getName()) && courseList.get(position).getTime().equals(newCourse.getTime())) {
                    FirebaseDatabase.getInstance().getReference("enrolledClass").child("class").child(postSnapshot.getId()).removeValue();
                    System.out.println("deleting enrolled course");
                    break;
                }
            }

        } else {
            boolean check = false;
            for (Course postSnapshot : enrolledCourse) {
                Course newCourse = postSnapshot;
                if (newCourse != null && courseList.get(position).getUserName().equals(newCourse.getUserName()) && courseList.get(position).getName().equals(newCourse.getName()) && courseList.get(position).getTime().equals(newCourse.getTime())) {
                    check = true;
                }
            }
            if (!check && EnrollBtn.getText().toString().equals("Enroll")) {
                Course course = courseList.get(position);
                if (course.getEnrolled() < course.getCapacity()) {
                    course.setEnrolled(course.getEnrolled() + 1);
                    FirebaseDatabase.getInstance().getReference("scheduledClass").child(course.getId()).child("class").child("enrolled").setValue(course.getEnrolled());
                    ((TextView) listView.findViewById(R.id.capacity_Schedule_view)).setText("Capacity: " + course.getEnrolled() + "/" + course.getCapacity());
                    String key = FirebaseDatabase.getInstance().getReference().push().getKey();
                    Course newCourseEnroll = new Course(course.getName(), course.getDate(), course.getTime(), course.getDifficulty(), course.getCapacity(), LoginPage.getUser().getUsername(), key, 0);
                    FirebaseDatabase.getInstance().getReference().child("enrolledClass").child("class").child(key).setValue(newCourseEnroll);
                    ((TextView) listView.findViewWithTag(position)).setBackground(ContextCompat.getDrawable(context, R.drawable.enrollbtn));
                    ((TextView) listView.findViewWithTag(position)).setText("Unenroll");
                    System.out.println("enrolling into course");
                }
            }

        }

    }
}
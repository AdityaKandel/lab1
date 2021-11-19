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

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class scheduleList extends ArrayAdapter<Course> {
    private Activity context;
    List<Course> courseList;
    private String courseId;
    private Course Scheduledcourse;
    public scheduleList(Activity context, List<Course> courses){
        super(context, R.layout.schedule_class_view,courses);
        this.context = context;
        this.courseList = courses;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.schedule_class_view, null, true);

        TextView time = (TextView) listViewItem.findViewById(R.id.time_Schedule_view);
        TextView capacity = (TextView) listViewItem.findViewById(R.id.capacity_Schedule_view);
        TextView date = (TextView) listViewItem.findViewById(R.id.date_schedule_view2);
        TextView difficulty = (TextView) listViewItem.findViewById(R.id.difficulty_Schedule_view);
        TextView CourseName = (TextView) listViewItem.findViewById(R.id.className_shedule_class_view);
         Scheduledcourse = courseList.get(position);

        //set values
        time.setText(Scheduledcourse.getTime());
        CourseName.setText(Scheduledcourse.getName());
        date.setText(Scheduledcourse.getDate());
        capacity.setText(Integer.toString(Scheduledcourse.getCapacity()));
        difficulty.setText(Scheduledcourse.getDifficulty());
        courseId = Scheduledcourse.getId();
        //setBackground
      //  setGradientColor((ImageView) listViewItem.findViewById(R.id.car));

        //setButtonOperations;
        TextView editBtn = (TextView) listViewItem.findViewById(R.id.edit_Schedule_view);
        TextView removeBtn = (TextView) listViewItem.findViewById(R.id.cancel_Schedule_view);
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
        FirebaseDatabase.getInstance().getReference().child("scheduledClass").child(courseId).removeValue();
        Toast.makeText(getContext(), "Course deleted",Toast.LENGTH_SHORT).show();

    }

    public void EditCourse(){
        Intent intent = new Intent(context, ScheduleClassActivity.class);
        System.out.println(Scheduledcourse.getId() +"editing");
        intent.putExtra("key", Scheduledcourse.getId());
        intent.putExtra("courseName", Scheduledcourse.getName());
        System.out.println(Scheduledcourse.getName()+ "course name");

        context.startActivity(intent);
        Toast.makeText(getContext(), "Course edited",Toast.LENGTH_SHORT).show();
    }
    public void updateUI(View context){
//        TextView schedule = ((TextView) context.findViewById(R.id.enroll));
//
//        switch (LoginPage.getUser().getroleNum()){
//            case "2":
//                ((TextView) context.findViewById(R.id.remove_courseview)).setVisibility(View.GONE);
//                ((TextView) context.findViewById(R.id.editCourse_courseview)).setVisibility(View.GONE);
//                break;
//            case "1":
//                ((TextView) context.findViewById(R.id.remove_courseview)).setVisibility(View.GONE);
//                ((TextView) context.findViewById(R.id.editCourse_courseview)).setVisibility(View.GONE);
//                schedule.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        scheduleClass();
//                    }
//                });
//                break;
//            case "0":
//                schedule.setVisibility(View.GONE);
//
//                break;
//        }
    }

}
package com.example.fitnesscentrebooking;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Random;

import kotlin.comparisons.UComparisonsKt;


public class CourserViewUi extends RecyclerView.ViewHolder {
    TextView description, courseName, capacity, time;
    Button enroll,remove,editCourse;
    ImageView imageView;
    User user;
    String key;
    Context context;
    public CourserViewUi(@NonNull View itemView, String key) {
        super(itemView);
        context = itemView.getContext();
        imageView = itemView.findViewById(R.id.background_courseView);
        description = itemView.findViewById(R.id.description);
        courseName = itemView.findViewById(R.id.courseName);
        capacity = itemView.findViewById(R.id.capacity);
        enroll = itemView.findViewById(R.id.enroll);
        remove = itemView.findViewById(R.id.remove_courseview);

        editCourse= itemView.findViewById(R.id.editCourse_courseview);
        editCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {System.out.println("need to edit the corurse!!");setEditCourse(); }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeCourse();
            }
        });
        setGradientColor();
        user =MainActivity.getUser();
        updateUI();

    }
    public void setGradientColor(){
        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[] {colorpicker(), colorpicker()} );
        gd.setCornerRadius(100f);

        imageView.setBackground(gd);

    }

    public void setKey(String key) {
        System.out.println("key is obtained" + key);
        this.key = key;
    }

    public void updateUI(){
        switch (user.getRole()){
            case "member":
                remove.setVisibility(View.GONE);
                editCourse.setVisibility(View.GONE);
                    break;
            case"admin":
                enroll.setVisibility(View.GONE);
                break;

        }
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
        System.out.println(key+"id of the course");
        FirebaseDatabase.getInstance().getReference().child("courses").child(key).removeValue();
    }

    public void setEditCourse(){

        Intent intent = new Intent(context, courseAddPage.class);
        courseAddPage.key2=key;
        context.startActivity(intent);


    }

}

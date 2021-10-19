package com.example.fitnesscentrebooking;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Random;


public class CourserViewUi extends RecyclerView.ViewHolder {
    TextView description, courseName, capacity, time;
    Button enroll,remove;
    ImageView imageView;
    User user;
    String key;
    public CourserViewUi(@NonNull View itemView, String key) {
        super(itemView);
        imageView = itemView.findViewById(R.id.background_courseView);
        description = itemView.findViewById(R.id.description);
        courseName = itemView.findViewById(R.id.courseName);
        capacity = itemView.findViewById(R.id.capacity);
        enroll = itemView.findViewById(R.id.enroll);
        remove = itemView.findViewById(R.id.remove_courseview);
        System.out.println("blah blah"+ key);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                test5();
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
        gd.setCornerRadius(0f);

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
                    break;

        }
    }
    public int colorpicker(){
        Random r = new Random();
        String[] colors = new String[]{ "#39add1",
                "#3079ab",
                "#c25975",
                "#e15258",
                "#f9845b",
                "#838cc7",
                "#7d669e",
                "#53bbb4",
                "#51b46d",
                "#e0ab18",
                "#637a91",
                "#f092b0",
                "#b7c0c7"  };
        return Color.parseColor(colors[r.nextInt(colors.length)]);
    }

    public void test5(){
        System.out.println(key+"id of the course");
        FirebaseDatabase.getInstance().getReference().child("courses").child(key).removeValue();
    }
}

package com.example.fitnesscentrebooking;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class CourserViewUi extends RecyclerView.ViewHolder {
    TextView description, courseName, capacity, time;
    Button enroll;
    public CourserViewUi(@NonNull View itemView) {
        super(itemView);
        description = itemView.findViewById(R.id.description);
        courseName = itemView.findViewById(R.id.courseName);
        capacity = itemView.findViewById(R.id.capacity);
        enroll = itemView.findViewById(R.id.enroll);
    }

    public void test(View view){}


}

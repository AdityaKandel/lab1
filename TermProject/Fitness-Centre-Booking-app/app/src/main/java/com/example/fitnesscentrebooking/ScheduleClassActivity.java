package com.example.fitnesscentrebooking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ScheduleClassActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shedule_class_activity);

    }


    public void cancelprocess(View view) {
        finish();
    }

    public void ScheduleClass(View view){

    }
}

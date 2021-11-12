package com.example.fitnesscentrebooking;

import android.app.Activity;
import android.os.Bundle;

public class HomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page_activity);
        Navigation navi = new Navigation();
        navi.setNavigationView(this);
    }
}

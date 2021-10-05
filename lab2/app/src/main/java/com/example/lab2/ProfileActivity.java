package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }
    public void SetTeamIcon(View view){
        Intent returnIntent = new Intent();
        ImageView selectImage = (ImageView) view;
        returnIntent.putExtra("imageID",selectImage.getId());
        setResult(RESULT_OK, returnIntent);
        finish();

    }
}
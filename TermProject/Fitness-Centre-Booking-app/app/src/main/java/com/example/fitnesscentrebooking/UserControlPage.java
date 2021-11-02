package com.example.fitnesscentrebooking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class UserControlPage extends AppCompatActivity  {

    RecyclerView recyclerView;
    FloatingActionButton createUserBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_view);
        createUserBtn = findViewById(R.id.createUserBtn_UsercontrolPage);
        recyclerView = findViewById(R.id.recyclerView_userlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FirebaseRecyclerView<User,userListView> test = new FirebaseRecyclerView<>("UsersList",recyclerView,R.layout.user_view_single,User.class,userListView.class);
        Navigation navi = new Navigation();
        navi.setNavigationView(this);

    }
    ActivityResultLauncher<Intent> intentLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){

                }
            });

    public void CreateUser(View view){
        Intent intent = new Intent(getApplicationContext(), CreateUser.class);
        intentLaucher.launch(intent);
    }
}
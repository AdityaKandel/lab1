package com.example.fitnesscentrebooking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserControlPage extends AppCompatActivity {

    RecyclerView recyclerView;
    ListView listView;
    List<User> userlist;
    FloatingActionButton createUserBtn;
    DatabaseReference userDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_view);
        createUserBtn = findViewById(R.id.createUserBtn_UsercontrolPage);
        listView = findViewById(R.id.listView_userlist);
        userlist = new ArrayList<>();
        userDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        Navigation navi = new Navigation();
        navi.setNavigationView(this);

    }

    ActivityResultLauncher<Intent> intentLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            });

    public void CreateUser(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationPage.class);
        intentLaucher.launch(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userlist.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    System.out.println(postSnapshot.child("userData").getValue());
                    User user = postSnapshot.child("userData").getValue(User.class);
                    userlist.add(user);
                }
                UserList productAdapter = new UserList(UserControlPage.this, userlist);
                listView.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}
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
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_user_list_view);
        createUserBtn = findViewById(R.id.createUserBtn_UsercontrolPage);
        listView = findViewById(R.id.listView_userlist);
        userlist = new ArrayList<>();
        userDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");
        Navigation navi = new Navigation();
        navi.setNavigationView(this);
        SearchView searchBar = findViewById(R.id.searchBar);
       searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String s) {
             find(s);
              return false;
           }
       });
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

    private void find(String username){
        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userlist.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    User user = postSnapshot.child("userData").getValue(User.class);
                    if(user.getUsername().contains(username)){
                        userlist.add(user);}
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
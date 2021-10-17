package com.example.fitnesscentrebooking;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText textUsername;
    EditText textPassword;
    private FirebaseAuth mAuth;
    String username;
    String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        textPassword = findViewById(R.id.textPassword_Login);
        textUsername = findViewById(R.id.textUsername_Login);
    }



    ActivityResultLauncher<Intent> CreateUserLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        textUsername.setText(data.getStringExtra("username"));
                    }
                }

            });

    ActivityResultLauncher<Intent> mainPageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){
                @Override
                public void onActivityResult(ActivityResult result){
                    if(result.getResultCode() == Activity.RESULT_OK){
                      //  Intent data = result.getData();
                       // textUsername.setText(data.getStringExtra("username"));
                    }
                }

            });
    public void onCreateNewUser(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateUser.class);
        CreateUserLauncher.launch(intent);
    }


    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String Database_password=dataSnapshot.child("password").getValue(String.class);
            if(Database_password.equals(password)){
                Intent intent = new Intent(getApplicationContext(), main_page.class);
                CreateUserLauncher.launch(intent);
            }

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            // Getting Post failed, log a message

        }
    };
    public void onLogin(View view){
         username =textUsername.getText().toString().toLowerCase() ;
         password = textPassword.getText().toString();
        FirebaseDatabase.getInstance().getReference().child("Users").child(username).addValueEventListener(listener);

    }
}


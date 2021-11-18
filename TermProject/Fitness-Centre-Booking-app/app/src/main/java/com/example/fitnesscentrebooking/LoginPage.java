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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginPage extends AppCompatActivity {
    protected EditText textUsername;
    protected EditText textPassword;
    protected String username;
    protected String password;
   protected static User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textPassword = (EditText) findViewById(R.id.textPassword_Login);
        textUsername = (EditText) findViewById(R.id.textUsername_Login);

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
                       // Intent data = result.getData();
                      //  textUsername.setText(data.getStringExtra("username"));
                    }
                }

            });
    public void onCreateNewUser(View view) {
        Intent intent = new Intent(getApplicationContext(), RegistrationPage.class);
        CreateUserLauncher.launch(intent);
    }


    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            String Database_password=dataSnapshot.child("password").getValue(String.class);
                if(Database_password!=null) {
                    if (Database_password.equals(password)) {
                        user = dataSnapshot.child("userData").getValue(User.class);
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        mainPageLauncher.launch(intent);
                        Toast.makeText(getApplicationContext(),"Logged in Successfully",Toast.LENGTH_SHORT).show();
                    }else{
                        textPassword.setError("Password or Username are Incorrect");
                        Toast.makeText(getApplicationContext(),"Password or Username are Incorrect",Toast.LENGTH_SHORT).show();
                    }
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

        boolean illegalChar = false;
        if(
                username.contains(".")
                        || username.contains("#")
                        || username.contains("$")
                        || username.contains("[")
                        || username.contains("]")
        ){illegalChar = true;}

         if(!(password.equals("") || username.equals("")) && (!illegalChar)){
             FirebaseDatabase.getInstance().getReference().child("Users").child(username).addValueEventListener(listener);
             System.out.println("should login");
        }else{
             System.out.println("username not found");
             textPassword.setError("Password or Username are Incorrect");
         }
    }

    public static User getUser(){
        return user;
    }

    public void logout(){
        setContentView(R.layout.activity_main);}
    }


package com.example.fitnesscentrebooking;

import android.app.people.PeopleManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Locale;

public class CreateUser extends AppCompatActivity {
    protected EditText text_Email;
    protected  EditText text_Username;
    protected  EditText text_Password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent returnIntent = new Intent();
        setContentView(R.layout.registration_page);
        mAuth = FirebaseAuth.getInstance();
        text_Email = findViewById(R.id.textEmail);
        text_Username = findViewById(R.id.textUsername);
        text_Password = findViewById(R.id.textPassword);
    }


    public void onAddUser(View view){
        String email = text_Email.getText().toString();
        String username = text_Username.getText().toString().toLowerCase();
        String password  = text_Password.getText().toString();
        User newMember = new Member(username,email);
        FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("password").setValue(password);
        FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("userData").setValue(newMember);

        Intent returnIntent = new Intent();
     //  EditText textUsername_Login = (getText()) view;
        //returnIntent.putExtra("imageID",selectImage.getId());
        returnIntent.putExtra("username",text_Username.getText().toString());
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void CheckFieldvalidity(){


    }
}

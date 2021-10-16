package com.example.fitnesscentrebooking;

import android.app.people.PeopleManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class CreateUser extends AppCompatActivity {
    protected EditText text_Email;
    protected  EditText text_Username;
    protected  EditText text_Password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent returnIntent = new Intent();
        setContentView(R.layout.registration_page);

        text_Email = findViewById(R.id.textEmail);
        text_Username = findViewById(R.id.textUsername);
        text_Password = findViewById(R.id.textPassword);
    }

    public void onAddUser(View view){
        System.out.println(text_Email.getText().toString());
        System.out.println(text_Username.getText().toString());
        System.out.println(text_Password.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("User").child(text_Username.getText().toString()).child("password").setValue(text_Password.getText().toString());
        FirebaseDatabase.getInstance().getReference().child("User").child(text_Username.getText().toString()).child("email").setValue(text_Email.getText().toString());

        Intent returnIntent = new Intent();
     //  EditText textUsername_Login = (getText()) view;
        //returnIntent.putExtra("imageID",selectImage.getId());
        returnIntent.putExtra("username",text_Username.getText().toString());
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}

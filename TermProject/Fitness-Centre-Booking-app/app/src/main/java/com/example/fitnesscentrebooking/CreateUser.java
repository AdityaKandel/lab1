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

import java.util.Arrays;
import java.util.Locale;

public class CreateUser extends AppCompatActivity {
    protected EditText text_Email;
    protected  EditText text_Username;
    protected  EditText text_Password;
    protected EditText text_ConfrimPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent returnIntent = new Intent();
        setContentView(R.layout.registration_page);
        mAuth = FirebaseAuth.getInstance();
        text_Email = (EditText) findViewById(R.id.textEmail);
        text_Username = (EditText) findViewById(R.id.textUsername);
        text_Password = (EditText) findViewById(R.id.textPassword);
        text_ConfrimPassword = (EditText) findViewById(R.id.textConfirmPassword);
    }


    public void onAddUser(View view){
        String email = text_Email.getText().toString();
        String username = text_Username.getText().toString().toLowerCase();
        String password  = text_Password.getText().toString();
        String confrimPassword = text_ConfrimPassword.getText().toString();
        User newMember = new Member(username,email);
        if(CheckFieldvalidity(username, email, password,confrimPassword)) {
            FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("password").setValue(password);
            FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("userData").setValue(newMember);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("username", text_Username.getText().toString());
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    public boolean CheckFieldvalidity(String username, String email, String password, String confirmPassword){
        boolean isValid = true;
        //Check if username is valid
        if(!username.matches("[a-zA-Z]+")){
            text_Username.setError("Enter Letter only-no space allowed");
            isValid = false;
        }

        //check if the email is valid
        String[] split1 = email.split("@");
        if(split1.length<2){ text_Email.setError("Not a Valid Email"); isValid= false;}
        String[] split2 = split1[1].split("\\.");
        if(split2.length!=2 || !split2[1].matches("[a-zA-Z]+") || !split2[0].matches("[a-zA-Z]+")){
            text_Email.setError("Not a Valid Email");
          isValid= false;
        }

        //check if the password is valid
        if(password.length()<8){
            text_Password.requestFocus();
            text_Password.setError("Minimum Length Needs to Be 8");
            isValid = false;
        }
        if(!password.equals(confirmPassword)){
            text_ConfrimPassword.setError("Password Not Match");
            isValid = false;
        }
        return isValid;

    }
}

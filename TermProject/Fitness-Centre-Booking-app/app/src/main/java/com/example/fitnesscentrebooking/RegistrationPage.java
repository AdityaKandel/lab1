package com.example.fitnesscentrebooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegistrationPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected EditText text_Email;
    protected  EditText text_Username;
    protected  EditText text_Password;
    protected EditText text_ConfrimPassword;
    protected Spinner roleSelection_dropdown;
    private String roleSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent returnIntent = new Intent();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.registration_page);
        text_Email = (EditText) findViewById(R.id.textEmail);
        text_Username = (EditText) findViewById(R.id.textUsername);
        text_Password = (EditText) findViewById(R.id.textPassword);
        text_ConfrimPassword = (EditText) findViewById(R.id.textConfirmPassword);

        /*https://developer.android.com/guide/topics/ui/controls/spinner#java*/
        roleSelection_dropdown = (Spinner) findViewById(R.id.Ddifficultyspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        roleSelection_dropdown.setAdapter(adapter);
        roleSelection_dropdown.setOnItemSelectedListener(this);
        ////////////////////////////////////////////////////////////
        roleSelected = "member";
    }


    public void onAddUser(View view){
        String email = text_Email.getText().toString();
        String username = text_Username.getText().toString().toLowerCase();
        String password  = text_Password.getText().toString();
        String confrimPassword = text_ConfrimPassword.getText().toString();
        String key = FirebaseDatabase.getInstance().getReference().push().getKey();
        Instructor instructor = new Instructor();
        Member member = new Member();
        if(roleSelected.equals("Member")){
             member = new Member(username,email, "2",key, roleSelected.toString());

            if(CheckFieldValidity(username, email, password,confrimPassword)) {
                FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("password").setValue(password);
                FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("userData").setValue(member);
                FirebaseDatabase.getInstance().getReference().child("UsersList").child(key).setValue(member);

            }

        }else if(roleSelected.equals("Instructor")){
            System.out.println("Instructor added");
            instructor = new Instructor(username,email, "1",key, roleSelected.toString());
            if(CheckFieldValidity(username, email, password,confrimPassword)) {
                FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("password").setValue(password);
                FirebaseDatabase.getInstance().getReference().child("Users").child(username).child("userData").setValue(instructor);
                FirebaseDatabase.getInstance().getReference().child("UsersList").child(key).setValue(instructor);

            }
        }
        Intent returnIntent = new Intent();
        returnIntent.putExtra("username", text_Username.getText().toString());
        setResult(RESULT_OK, returnIntent);
        Toast.makeText(getApplicationContext(),"Account Created Successfully",Toast.LENGTH_SHORT).show();
        finish();


    }

    public boolean CheckFieldValidity(String username, String email, String password, String confirmPassword){
        boolean isValid = true;
        //Check if username is valid
        if(!username.matches("[a-zA-Z]+")){
            text_Username.setError("Enter Letter only-no space allowed");
            isValid = false;
        }

        //check if the email is valid
        if(!text_Email.getText().toString().equals("")) {
            String[] split1 = email.split("@");
            if (split1.length < 2) {
                text_Email.setError("Not a Valid Email");
                isValid = false;
            }
            String[] split2 = split1[1].split("\\.");
            if (split2.length != 2 || !split2[1].matches("[a-zA-Z]+") || !split2[0].matches("[a-zA-Z]+")) {
                text_Email.setError("Not a Valid Email");
                isValid = false;
            }
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
    public void onLog(View view){
        finish();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        roleSelected = roleSelection_dropdown.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

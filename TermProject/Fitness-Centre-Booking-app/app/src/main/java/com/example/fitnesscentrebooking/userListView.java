package com.example.fitnesscentrebooking;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class userListView extends RecyclerView.ViewHolder {
    TextView userField;
    TextView deleteUserBtn;
    String id;
    String username;
    public userListView(@NonNull View itemView) {
        super(itemView);
        userField = itemView.findViewById(R.id.userField_userSlview);
        deleteUserBtn = itemView.findViewById(R.id.deleteUser_userSLview);
        deleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });
    }

    public void setId(String id){
        this.id = id;
    };

    public void setUsername(String username){
        this.username = username;
    }

    public void deleteUser(){
    System.out.println("user deleted:" + username);
        FirebaseDatabase.getInstance().getReference().child("Users").child(username).removeValue();
        FirebaseDatabase.getInstance().getReference().child("UsersList").child(id).removeValue();
        Toast.makeText(itemView.getContext(),"User deleted",Toast.LENGTH_SHORT).show();

    }



}
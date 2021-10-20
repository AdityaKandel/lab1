package com.example.fitnesscentrebooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class userListView extends RecyclerView.ViewHolder {
    TextView userField;
    Button deleteUserBtn;
    String id;
    String username;
    public userListView(@NonNull View itemView) {
        super(itemView);
        userField = itemView.findViewById(R.id.userField_userSlview);
        deleteUserBtn = itemView.findViewById(R.id.deleteUser_userSlview);
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
        System.out.println("user Id:" + id);
        FirebaseDatabase.getInstance().getReference().child("Users").child(username).removeValue();
        FirebaseDatabase.getInstance().getReference().child("UsersList").child(id).removeValue();
    }



}
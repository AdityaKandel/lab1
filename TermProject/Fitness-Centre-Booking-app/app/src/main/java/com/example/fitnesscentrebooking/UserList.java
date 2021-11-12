package com.example.fitnesscentrebooking;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class UserList extends ArrayAdapter<User> {
    private Activity context;
    List<User> userList;
    User user;

    public UserList(Activity context, List<User> users) {
        super(context, R.layout.activity_user_list_view, users);
        this.context = context;
        this.userList = users;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.user_view_single, null, true);

        TextView userField = (TextView) listViewItem.findViewById(R.id.userField_userSlview);

        user = userList.get(position);

        //set values
        userField.setText("Name: " + user.getUsername() + " | Role: " + user.getRole());

        //setBackground


        //setButtonOperations;
        TextView deleteBtn = (TextView) listViewItem.findViewById(R.id.deleteUser_userSLview);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser();
            }
        });

        return listViewItem;
    }

    public void deleteUser() {
        FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUsername()).removeValue();

    }

}
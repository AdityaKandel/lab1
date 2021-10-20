package com.example.fitnesscentrebooking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class UserList extends AppCompatActivity {
    FirebaseRecyclerOptions<User> users;
    FirebaseRecyclerAdapter<User, userListView> adapterUsers;
    RecyclerView recyclerViewUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list_view);
        recyclerViewUserList = findViewById(R.id.recyclerView_userlist);
        recyclerViewUserList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        viewUsers();
    }

    public void viewUsers(){

        users = new FirebaseRecyclerOptions.Builder<User>().setQuery(FirebaseDatabase.getInstance().getReference().child("UsersList"),User.class).build();
        adapterUsers = new FirebaseRecyclerAdapter<User, userListView>(users) {
            @Override
            protected void onBindViewHolder(@NonNull userListView holder, int position, @NonNull User model) {
                    System.out.println(model.getUsername()+"testing the user list username");
                    holder.userField.setText("User: "+model.getUsername()+" | Role: "+model.getRole());
                    holder.setUsername(model.getUsername());
                    holder.setId(model.getId());
            }
            @NonNull
            @Override
            public userListView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view_single,parent, false);
                return new userListView(v);
            }
        };
        adapterUsers.startListening();
        recyclerViewUserList.setAdapter(adapterUsers);
    }

    public void goback(View view){
        finish();
    }
}

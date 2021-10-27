package com.example.fitnesscentrebooking;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.InvocationTargetException;

public class FirebaseRecyclerView<T,E extends RecyclerView.ViewHolder> extends AppCompatActivity {
    protected FirebaseRecyclerOptions<T> options;
    protected FirebaseRecyclerAdapter<T, E> adapter;
    protected RecyclerView recyclerView;
    protected DatabaseReference ref;
    protected int layout;
     Class<T> gettingclass;
     Class<E> itemViewClass;

    public FirebaseRecyclerView(String userList, RecyclerView recyclerView_userlist, int activity_user_list_view, Class<T> userClass, Class<E> userListViewClass) {
        Userlist(userList,recyclerView_userlist,activity_user_list_view,userClass,userListViewClass);
    }

    protected void Userlist(String dataReference, RecyclerView recyclerViewID, int layoutId,Class<T> v, Class<E> itemViewClass) {
        System.out.println((recyclerViewID==null)+ "ref is null");
        recyclerView = recyclerViewID;
        this.ref = FirebaseDatabase.getInstance().getReference(dataReference);
        this.gettingclass = v;
        this.itemViewClass = itemViewClass;
        viewUsers();
        this.layout = layoutId;
    }
    public void viewUsers(){
        System.out.println(ref==null);
        options = new FirebaseRecyclerOptions.Builder<T>().setQuery(ref, gettingclass).build();
        System.out.println(options.toString());

        adapter = new FirebaseRecyclerAdapter<T, E>(options) {
            @Override
            protected void onBindViewHolder(@NonNull E holder, int position, @NonNull T model) {
                System.out.println(model.getClass()==User.class);

                if (model.getClass() == CourseType.class){
                    CourseType courseType =  (CourseType)model;
                    CourseViewUi holderCourse = (CourseViewUi)holder;
                    holderCourse.description.setText(courseType.getDescription());
                    holderCourse.courseName.setText(courseType.getName());
                    ((CourseViewUi) holder).setKey(courseType.getId());
                }else if (model.getClass() == User.class){
                    User user =  (User) model;
                    userListView holderUser = (userListView)holder;
                    holderUser.userField.setText("Name: "+user.getUsername()+" | Role: "+user.getRole());
                    ((userListView) holder).setUsername(user.getUsername());
                    ((userListView) holder).setId(user.getId());
                }
            }
            @NonNull
            @Override
            public E onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent, false);
                System.out.println(v==null);
                try {
                    System.out.println("works for users");
                    return itemViewClass.getDeclaredConstructor(View.class).newInstance(v); //<<---
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                System.out.println("no for users");

                return null;
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }
}

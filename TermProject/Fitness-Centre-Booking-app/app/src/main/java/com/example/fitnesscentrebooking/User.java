package com.example.fitnesscentrebooking;

import java.util.ArrayList;
import java.util.List;

public class User {

    protected String username;
    protected String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected String id;
    protected List<Course> courses;
    protected String role;

    public User(){
        username= "";
        email = "";
        courses = new ArrayList<>();
        id = "";
    }
    public User(String username, String email, String role,String id){
        this.email = email;
        this.username = username;
        this.role = role;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Course getCourses(int i) {
        return courses.get(i);
    }

    public void setCourses(Course course) {
        courses.add(course);
    }

    public void setRole(String role){
        this.role = role;
    }

    public  String getRole(){
        return role;
    }


}

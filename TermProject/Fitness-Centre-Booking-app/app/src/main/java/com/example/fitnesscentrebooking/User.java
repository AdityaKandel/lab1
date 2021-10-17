package com.example.fitnesscentrebooking;

import java.util.ArrayList;
import java.util.List;

public class User {

    protected String username;
    protected String email;
    protected List<Course> courses;

    public User(){
        username= "";
        email = "";
        courses = new ArrayList<>();
    }
    public User(String username, String email){
        this.email = email;
        this.username = username;
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


}

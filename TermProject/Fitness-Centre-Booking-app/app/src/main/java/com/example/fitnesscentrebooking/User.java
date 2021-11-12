package com.example.fitnesscentrebooking;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String username;
    private String email;
    private String id;



    //public List<CourseType> course;
    private String roleNum;

    private String roleName;


    public User(){
        username= "";
        email = "";
        roleNum="";
        roleName="";
        id = "";
    }
    public User(String username, String email, String roleNum,String id,String roleName){
        this.email = email;
        this.username = username;
        this.roleNum = roleNum;
        this.id = id;
        this.roleName = roleName;
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

//    public CourseType getCourses(int i) {
//        return course.get(i);
//    }

//    public void setCourses(CourseType courseType) {
//        course.add(courseType);
//    }

    public String getroleNum() {
        return roleNum;
    }

    public void setroleNum(String roleNum) {
        this.roleNum = roleNum;
    }
    public String getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public void setId(String id) {
        this.id = id;
    }

}

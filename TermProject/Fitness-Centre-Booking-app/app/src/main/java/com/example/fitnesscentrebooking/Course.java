package com.example.fitnesscentrebooking;

public class Course {
    private String name;
    private String description;
    private String date;
    private String userName;
    private String time;
    private String difficulty;
    private int capacity;
    private String id;

    public int getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(int enrolled) {
        this.enrolled = enrolled;
    }

    private int enrolled;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }



    public Course(String name, String description, String id){
        this.name = name;
        this.description =description;
        this.id =id;
    }
    public Course(String name, String date, String time, String difficulty, int capacity, String userName, String id, int enrolled){
        this.date = date;
        this.difficulty = difficulty;
        this.time = time;
        this.capacity = capacity;
        this.userName =userName;
        this.name = name;
        this.id = id;
        this.enrolled = enrolled;
    }
    public Course(){

    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

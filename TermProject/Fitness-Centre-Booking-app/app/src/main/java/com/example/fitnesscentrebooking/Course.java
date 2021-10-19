package com.example.fitnesscentrebooking;

public class Course {
    protected String date;
    protected String name;
    protected String description;
    protected String time;
    protected int capacity;
    protected String id;

    public Course(String name, String description,String date, String time, int capacity, String id){
        this.date = date;
        this.name = name;
        this.description =description;
        this.time = time;
        this.capacity = capacity;
        this.id =id;
    }
    public Course(){

    }
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}

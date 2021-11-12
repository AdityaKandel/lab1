package com.example.fitnesscentrebooking;

public class Course {
    private String name;
    private String description;
    private String date;
    private String time;
    private String difficulty;
    private String id;

    public Course(String name, String description, String id){
        this.name = name;
        this.description =description;
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

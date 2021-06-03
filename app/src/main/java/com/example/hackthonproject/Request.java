package com.example.hackthonproject;

import java.util.Date;

public class Request {

    private String title;
    private String course;
    private String date;
    private String time;
    private String description;
    private String username;

    public Request(String title, String course, String date, String time, String description,String username) {
        this.title = title;
        this.course = course;
        this.date = date;
        this.time = time;
        this.description = description;
        this.username=username;
    }

    public String getTitle() {
        return title;
    }

    public String getCourse() {
        return course;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}




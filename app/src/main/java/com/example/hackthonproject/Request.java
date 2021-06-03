package com.example.hackthonproject;

import java.util.Date;

public class Request {

    private String title;
    private String course;
    private Date date;
    private String time;
    private String description;

    public Request(String title, String course, Date date, String time, String description) {
        this.title = title;
        this.course = course;
        this.date = date;
        this.time = time;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getCourse() {
        return course;
    }

    public Date getDate() {
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}




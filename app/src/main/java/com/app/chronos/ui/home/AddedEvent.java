package com.app.chronos.ui.home;

import com.google.firebase.database.Exclude;

import java.time.LocalDate;

public class AddedEvent {

    public AddedEvent(){
        //safe
    }


    @Exclude
    private String key;
    private String title;
    private String location;
    private String time;
    private String description;
    private String date;
    private String monthyear;


    public AddedEvent ( String title, String location, String timebutton, String description, String date, String monthyear ) {
        this.title = title;
        this.location = location;
        this.time = timebutton;
        this.description = description;
        this.date = date;
        this.monthyear = monthyear;
    }

    public AddedEvent ( String toString, String toString1, String toString2, String toString3 ) {
    }

    public String getTitle () {
        return title;
    }

    public void setTitle ( String title ) {
        this.title = title;
    }

    public String getLocation () {
        return location;
    }

    public void setLocation ( String location ) {
        this.location = location;
    }



    public String getDescription () {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }
    public String getTime () {
        return time;
    }

    public void setTime ( String timebutton ) {
        this.time = timebutton;
    }

    public String getDate () {
        return date;
    }

    public void setDate ( String date ) {
        this.date = date;
    }

    public String getMonthyear () {
        return monthyear;
    }

    public void setMonthyear ( String monthyear ) {
        this.monthyear = monthyear;
    }

    public String getKey () {
        return key;
    }

    public void setKey ( String key ) {
        this.key = key;
    }
}

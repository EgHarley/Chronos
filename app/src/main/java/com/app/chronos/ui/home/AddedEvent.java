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
    private String timeStart;
    private String timeEnd;
    private String description;
    private String date;
    private String monthyear;
    private String reminder;
    private String voiceNotif;


    public AddedEvent ( String title, String location, String timebutton, String timebuttonEnd, String description, String date, String monthyear,String reminder,String Vnotif) {
        this.title = title;
        this.location = location;
        this.timeStart = timebutton;
        this.timeEnd = timebuttonEnd;
        this.description = description;
        this.date = date;
        this.monthyear = monthyear;
        this.reminder = reminder;
        this.voiceNotif = Vnotif;
    }

    public AddedEvent ( String toString, String toString1, String toString2, String toString3, String toString4,String toString5,String toString6) {
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
    public String getTimeStart () {
        return timeStart;
    }

    public void setTimeStart ( String timebutton ) {
        this.timeStart = timebutton;
    }
    public String getTimeEnd () {
        return timeEnd;
    }

    public void setTimeEnd ( String timebuttonEnd ) {
        this.timeEnd = timebuttonEnd;
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

    public String getReminder () {
        return reminder;
    }

    public void setReminder ( String reminder ) {
        this.reminder = reminder;
    }

    public String getVoiceNotif () {
        return voiceNotif;
    }

    public void setVoiceNotif ( String voiceNotif ) {
        this.voiceNotif = voiceNotif;
    }
}

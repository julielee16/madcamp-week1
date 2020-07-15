package com.example.firstproject.ui.main;

public class Mood {
    int myYear = 0;
    int myMonth = 0;
    int myDate = 0;
    String myDay;
    String myMood;

    public Mood(int year, int month, int date, String day, String mood) {
        this.myYear = year;
        this.myMonth = month;
        this.myDate = date;
        this.myDay = day;
        this.myMood = mood;
    }

    public int getYear() {
        return myYear;
    }

    public void setYear(int year) {
        myYear = year;
    }

    public int getMonth() {
        return myMonth;
    }

    public void setMonth(int month) {
        myMonth = month;
    }
    public int getDate() {
        return myDate;
    }

    public void setDate(int date) {
        myDate = date;
    }

    public String getDay() {
        return myDay;
    }

    public void setDay(String day) {
        myDay = day;
    }

    public String getMood() {
        return myMood;
    }

    public void setMood(String mood) {
        myMood = mood;
    }
}
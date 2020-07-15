package com.example.firstproject.ui.main;

public class Mood {
    int myYear = 0;
    int myMonth = 0;
    int myDate = 0;
    String myDay;
    String myMood;
    String myPicturePath;
    String myText;

    public Mood(int year, int month, int date, String day, String mood, String pic, String diary) {
        this.myYear = year;
        this.myMonth = month;
        this.myDate = date;
        this.myDay = day;
        this.myMood = mood;
        this.myPicturePath = pic;
        this.myText = diary;
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

    public String getPicture() {
        return myPicturePath;
    }

    public void setPicture(String path) {
        myPicturePath = path;
    }

    public String getText() {
        return myText;
    }

    public void setText(String text) {
        myText= text;
    }
}
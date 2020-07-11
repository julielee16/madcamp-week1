package com.example.firstproject.ui.main;

public class Person {
    String myName = "";
    String myNickname = "";
    String myNumber = "";
    String myEmail = "";

    public String getName() {
        return myName;
    }

    public void setName(String name) {
        myName = name;
    }

    public String getNickname() {
        return myNickname;
    }

    public void setNickname(String nickname) {
        myNickname = nickname;
    }

    public String getPhoneNum() {
        return myNumber;
    }

    public void setPhoneNum(String number) {
        myNumber = number;
    }

    public String getEmail() {
        return myEmail;
    }

    public void setEmail(String email) {
        myEmail = email;
    }
}

package com.example.project.Login;

public class User {
    private String uid,phoneNumber;


    public User(String uid,String phoneNumber) {
        this.uid=uid;
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

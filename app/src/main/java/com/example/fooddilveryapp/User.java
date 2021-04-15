package com.example.fooddilveryapp;

public class User {

    private int userPhoneNumber;
    private int id;
    private String userEmail;
    private String userFullName;
    private String userPassword;

    public User() {
    }

    public User(int userPhoneNumber, int id, String userEmail, String userFullName, String userPassword) {
        this.userPhoneNumber = userPhoneNumber;
        this.id = id;
        this.userEmail = userEmail;
        this.userFullName = userFullName;
        this.userPassword = userPassword;
    }

    public int getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(int userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
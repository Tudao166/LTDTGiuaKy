package com.example.testgiuaky2.model;

public class User {
    private String password;
    private String fullName;
    private String email;
    private int isActive;
    private String picture;

    private boolean gender;


    public User() {
    }

    public User(String password, String fullName, String email, int isActive, String picture) {
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.isActive = isActive;
        this.picture = picture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }



    public User(String fullName, String password,  String email, Boolean gender) {
        this.gender = gender;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

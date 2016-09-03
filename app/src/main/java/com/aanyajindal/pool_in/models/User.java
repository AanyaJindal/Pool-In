package com.aanyajindal.pool_in.models;

/**
 * Created by aanyajindal on 03/09/16.
 */
public class User
{
    String name;
    String email;
    String college;
    String location;

    public User(String name, String email, String college, String location) {
        this.name = name;
        this.email = email;
        this.college = college;
        this.location = location;

    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

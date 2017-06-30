package com.aanyajindal.pool_in.models;

public class User {
    String name;
    String email;
    String contact;
    String year;
    String branch;
    String location;
    String dplink;

    public User(String name, String email, String contact, String year, String branch, String location, String dplink) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.year = year;
        this.branch = branch;
        this.location = location;
        this.dplink = dplink;
    }

    public User() {
    }

    public String getDplink() {
        return dplink;
    }

    public void setDplink(String dplink) {
        this.dplink = dplink;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

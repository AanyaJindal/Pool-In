package com.aanyajindal.pool_in.models;

public class Item {

    String name;
    String user;
    String username;
    String desc;
    String mode;
    String cat;
    String tags;
    String date;

    public Item(String name, String user, String username, String desc, String mode, String cat, String tags, String date) {
        this.name = name;
        this.user = user;
        this.username = username;
        this.desc = desc;
        this.mode = mode;
        this.cat = cat;
        this.tags = tags;
        this.date = date;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

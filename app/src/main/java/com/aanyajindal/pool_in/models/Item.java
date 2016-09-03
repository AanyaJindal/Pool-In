package com.aanyajindal.pool_in.models;

public class Item {

    String name;
    String user;
    String desc;
    String mode;
    String cat;
    String tags;
    String date;

    public Item(String name, String user, String desc, String mode, String cat,String tags,String date) {
        this.name = name;
        this.user = user;
        this.desc = desc;
        this.mode = mode;
        this.cat = cat;
        this.tags = tags;
        this.date = date;
    }

    public Item() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}

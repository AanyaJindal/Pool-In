package com.aanyajindal.pool_in.models;

/**
 * Created by aanyajindal on 03/09/16.
 */
public class Item {

    String name;
    String user;
    String desc;
    String mode;
    String cat;

    public Item(String name, String user, String desc, String mode, String cat) {
        this.name = name;
        this.user = user;
        this.desc = desc;
        this.mode = mode;
        this.cat = cat;
    }

    public Item() {
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

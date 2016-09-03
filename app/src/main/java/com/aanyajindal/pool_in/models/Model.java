package com.aanyajindal.pool_in.models;

/**
 * Created by aanyajindal on 04/09/16.
 */
public class Model {
    String authorId;
    String date;
    String body;

    public Model(String authorId, String date, String body) {
        this.authorId = authorId;
        this.date = date;
        this.body = body;
    }

    public Model() {
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

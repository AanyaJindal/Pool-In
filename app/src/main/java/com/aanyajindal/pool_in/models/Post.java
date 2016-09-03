package com.aanyajindal.pool_in.models;

/**
 * Created by aanyajindal on 04/09/16.
 */
public class Post {

    String title;
    String date;
    String body;
    String authorId;
    String tags;

    public Post(String title, String date, String body, String authorId, String tags) {
        this.title = title;
        this.date = date;
        this.body = body;
        this.authorId = authorId;
        this.tags = tags;
    }

    public Post() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

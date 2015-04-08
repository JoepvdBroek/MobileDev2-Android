package com.android.eindopdracht;

/**
 * Created by Joep on 2-4-2015.
 */
public class Review {

    //private int id;
    private String body;
    private String author;
    private int rating;

    public Review() {

    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getRating() {
        return (rating / 10);
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

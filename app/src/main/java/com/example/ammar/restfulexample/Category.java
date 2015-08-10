package com.example.ammar.restfulexample;

/**
 * Created by ammar on 8/10/15.
 */
public class Category {
    public final String title;
    public final int pk;

    public Category(String title, int pk) {
        this.title = title;
        this.pk = pk;
    }

    @Override
    public String toString() {
        return title;
    }
}

package com.example.ammar.restfulexample;

/**
 * Created by ammar on 8/10/15.
 */
public class Article {
    public final String title;
    public final String imageUrl;
    public final String body;
    public final String articleUrl;
    public final String articleSource;
    public final String articleDate;

    public Article(String title, String imageUrl, String body, String url, String articleSource,String articleDate) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.body = body;
        articleUrl = url;
        this.articleSource = articleSource;
        this.articleDate = articleDate;
    }

    @Override
    public String toString() {
        return title;
    }
}

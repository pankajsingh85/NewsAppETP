package com.pankaj.newsapp;
public class News {
    private final String title;
    private final String url;
    private final String urlToImage;
    private final String author;

    public News(String title, String url, String urlToImage, String author) {
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getAuthor() {
        return author;
    }
}
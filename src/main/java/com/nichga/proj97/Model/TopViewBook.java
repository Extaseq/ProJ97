package com.nichga.proj97.Model;

public final class TopViewBook {
    private final String url;

    private final String title;

    private final String author;

    public TopViewBook(String url, String title, String author) {
        this.url = url;
        this.title = title;
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}

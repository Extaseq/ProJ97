package com.nichga.proj97.Model;

import java.util.Set;

public final class DisplayBook {
    private final String bookId;
    private final String title;
    private final String author;
    private final String description;
    private final String type;
    private final String tags;
    public final int available;
    public final int view;


    public DisplayBook(String bookId, String title, String author, String description, String type, String tags, int available, int view) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.description = description;
        this.type = type;
        this.tags = tags;
        this.available = available;
        this.view = view;
    }

    public String getBookId() {
        return bookId;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public String getDescription() {
        return description;
    }
    public String getType() {
        return type;
    }
    public String getTags() {
        return tags;
    }
    public int getAvailable() {
        return available;
    }
    public int getView() {
        return view;
    }

}

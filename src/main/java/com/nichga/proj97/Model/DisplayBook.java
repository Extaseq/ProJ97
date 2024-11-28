package com.nichga.proj97.Model;

import javafx.scene.image.Image;

import java.util.Objects;

public final class DisplayBook {
    private final String bookId;
    private final String title;
    private final String author;
    private final String publisher;
    private final String genre;
    private final String publishedYear;
    private final String isbn;
    private final int available;
    private final Image image;


    public DisplayBook(String bookId, String title, String author,
                       String publisher, String genre, String publishedYear,
                       String isbn, int available, String cover_url) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.isbn = isbn;
        this.available = available;
        this.image = new Image(Objects.requireNonNullElse(cover_url, "https://i.imgur.com/VwiPLSU.png"));
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

    public String getPublisher() {
        return publisher;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public String getISBN() {
        return isbn;
    }

    public int getAvailable() {
        return available;
    }

    public Image getImage() {
        return image;
    }
}

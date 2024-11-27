package com.nichga.proj97.Model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class DisplayBook {
    private final String bookId;
    private final String title;
    private final String author;
    private final String publisher;
    private final String genre;
    private final String publishedYear;
    private final String isbn;
    private final int copies_available;
    private final Image image;
    private final List<String> tags = new ArrayList<>();


    public DisplayBook(String bookId, String title, String author,
                       String publisher, String genre, String publishedYear,
                       String isbn, int copies_available, String cover_url) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.publishedYear = publishedYear;
        this.isbn = isbn;
        this.copies_available = copies_available;
        if (cover_url != null) {
            this.image = new Image(cover_url);
        } else {
            this.image = new Image("https://i.imgur.com/VwiPLSU.png");
        }
        if (genre != null) {
            this.tags.addAll(Arrays.asList(genre.replaceAll("[\\[\\]\"]", "").split("&")));
        }
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

    public int getCopiesAvailable() {
        return copies_available;
    }

    public Image getImage() {
        return image;
    }

    public List<String> getTags() {
        return tags;
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }
}

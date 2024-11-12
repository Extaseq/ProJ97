package com.nichga.proj97;

import java.util.Objects;

/**
 * Represents a document with a title, author, and number of copies.
 */
public class Documents {

    public String[] tag;
    private String title;

    private String author;

    private String type;

    private int numberOfCopies;

    private int currentCopies;

    private int timesBorrowed;

    {
        timesBorrowed = 0;
        currentCopies = 1;
        numberOfCopies = 1;
    }

    Documents() {}

    Documents(String title, String author, String type) {
        this.title = title;
        this.author = author;
        this.type = type;
    }
    Documents(String title, String author, String type,String[] tags, int numberOfCopies, int timesBorrowed) {
        this.title = title;
        this.author = author;
        this.type = type;
        this.numberOfCopies = numberOfCopies;
        this.currentCopies = numberOfCopies;
        this.timesBorrowed = timesBorrowed;
        this.tag = tags;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    void setAuthor(String author) {
        this.author = author;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    void setCurrentCopies(int currentCopies) {
        this.currentCopies = currentCopies;
    }

    public int getCurrentCopies() {
        return currentCopies;
    }

    void setTimesBorrowed(int timesBorrowed) {
        this.timesBorrowed = timesBorrowed;
    }

    public int getTimesBorrowed() {
        return timesBorrowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Documents that = (Documents) o;
        return title.equals(that.title) && author.equals(that.author) && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, type);
    }

    /**
     * Displays basic information about the document, including the title and author.
     * <p>
     * This method should be overridden in derived classes to provide specific
     * information related to the type of document being represented (e.g.,
     * Book, Magazine, Thesis).
     * </p>
     *
     * @see Book#displayInfo()
     * @see Magazine#displayInfo()
     * @see Thesis#displayInfo()
     */
    public void displayInfo() {
        System.out.println("Title: " + getTitle());
        System.out.println("Author: " + getAuthor());
    }
}

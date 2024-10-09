package com.nichga.proj97;

import java.util.List;
import java.util.Objects;

/**
 * Represents a book in the library system, extending the abstract class {@link Documents}.
 * <p>
 * A book has a publisher and a publication year in addition to the common attributes of a document.
 * This class provides methods to set and retrieve the publisher and publication year of the book.
 * </p>
 */
public class Book extends Documents {

    private String publisher;

    private int publicationYear;

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public Book() {}

    /**
     * Constructs a Book instance using the provided information.
     *
     * @param docInfoList a list of strings containing the book's title, author, publisher, and publication year.
     *                    The expected order is: title, author, publisher, publication year.
     *                    Must contain at least four elements.
     * @throws IllegalArgumentException if the provided list contains insufficient information.
     */
    public Book(List<String> docInfoList) {
        if (docInfoList.size() >= 4) {
            this.setType("Book");
            this.setTitle(docInfoList.get(0));
            this.setAuthor(docInfoList.get(1));
            this.publisher = docInfoList.get(2);
            this.publicationYear = Integer.parseInt(docInfoList.get(3));
            if (docInfoList.size() > 4) {
                this.setNumberOfCopies(Integer.parseInt(docInfoList.get(4)));
                this.setCurrentCopies(Integer.parseInt(docInfoList.get(5)));
                this.setTimesBorrowed(Integer.parseInt(docInfoList.get(6)));
            }
        } else {
            throw new IllegalArgumentException("Insufficient information provided for Book.");
        }
    }

    /**
     * Returns a string representation of the book's properties in the order they are declared,
     * separated by commas.
     *
     * @return a string containing all properties of the book
     */
    String getInformation() {
        return String.join(",",
                getTitle(),
                getAuthor(),
                publisher,
                String.valueOf(publicationYear),
                String.valueOf(getNumberOfCopies()),
                String.valueOf(getCurrentCopies()),
                String.valueOf(getTimesBorrowed())
        );
    }

    void editInformation() {
        
    }

    /**
     * Displays information about the book, including its publisher and publication year.
     * This method overrides the {@link Documents#displayInfo()} method to include additional
     * book-specific details.
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Publisher: " + publisher);
        System.out.println("Publication Year: " + publicationYear);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return this.getTitle().equals(book.getTitle()) &&
                this.getAuthor().equals(book.getAuthor()) &&
                this.publisher.equals(book.publisher) &&
                this.publicationYear == book.publicationYear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getAuthor(), publisher, publicationYear);
    }
}
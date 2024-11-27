package com.nichga.proj97.Database;

import com.nichga.proj97.Model.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class BookRepository extends GenericRepository {
    public BookRepository() {
        super("books");
    }

    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();

        public static final Column BOOK_ID          = new Column(1,   "book_id");
        public static final Column TITLE            = new Column(2,   "title");
        public static final Column AUTHOR           = new Column(4,   "author");
        public static final Column PUBLISHER        = new Column(8,   "publisher");
        public static final Column GENRE            = new Column(16,  "genre");
        public static final Column PUBLISHED_YEAR   = new Column(32,  "published_year");
        public static final Column ISBN             = new Column(64,  "isbn");
        public static final Column COPIES_AVAILABLE = new Column(128, "copies_available");

        private Column(int idx, String name) {
            super(idx, name);
            columns.add(this);
        }
    }

    /**
     * Creates a PreparedStatement for the given SQL query.
     *
     * @param sql The SQL query string.
     * @return the prepared statement, or null if an error occurs.
     */
    private PreparedStatement createStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean insertBook(Book book) {
        String book_id = book.getId();
        String title = book.getVolumeInfo().getTitle();
        String author = book.getVolumeInfo().getAuthors().toString();
        String publisher = book.getVolumeInfo().getPublisher();
        String genre = (book.getVolumeInfo().getCategories() != null)?book.getVolumeInfo().getCategories().toString() : "Unknown Genre";
        String published_year = String.valueOf(book.getVolumeInfo().getPublishedDate().getYear());
        String isbn = "null";
        if (book.getVolumeInfo().getIndustryIdentifiers() != null) {
            isbn = book.getVolumeInfo().getIndustryIdentifiers().getFirst().getIdentifier();
        }
        String cover_url = book.getVolumeInfo().getImageLinks().getThumbnail();
        String sql = "INSERT INTO books (book_id, title, author, publisher, genre, published_year, isbn, copies_available, cover_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 100, ?)";
        return executeUpdate(createStatement(sql), book_id, title, author, publisher, genre, published_year, isbn, cover_url) > 0;
    }

    public int getTotalBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public boolean adjustAfterBorrow(String bookId) {
        String subquerry = "SELECT copies_available FROM books WHERE book_id = ?";
        int available = 0;
        try (ResultSet rs = executeQuery(createStatement(subquerry),bookId)) {
            if (rs.next()) {
                available = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Borrow book error at get number of copies");
        }
        String new_available = String.valueOf(available);
        String sql = "UPDATE books SET copies_available = ? WHERE book_id = ?";
        return executeUpdate(createStatement(sql), new_available, bookId) > 0;
    }
}

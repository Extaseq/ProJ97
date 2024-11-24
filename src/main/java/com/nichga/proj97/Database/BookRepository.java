package com.nichga.proj97.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository extends GenericRepository {
    public BookRepository() {
        super("books");
    }
    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();

        public static final Column BOOK_ID      = new Column(1,  "book_id");
        public static final Column TITLE        = new Column(2,"title");
        public static final Column AUTHOR_ID    = new Column(4,  "author");
        public static final Column PUB          = new Column(8, "publisher");
        public static final Column Genre        = new Column(16,"genre");
        public static final Column PUB_YEAR     = new Column(32, "published_year");
        public static final Column ISBN         = new Column(64,  "isbn");
        public static final Column COPY_AVA     = new Column(128,  "copies_available");
        private Column(int idx, String name) {
            super(idx, name);
            columns.add(this);
        }
    }
    private PreparedStatement createStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public boolean adjustInfoAfterBorrow(String bookId) {
        String sql = "SELECT copies_available FROM " + tableName + " WHERE book_id = ?";
        try (PreparedStatement stmt = createStatement(sql)) {
            stmt.setString(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int availableCopies = rs.getInt("copies_available");
                if (availableCopies > 0) {
                    String updateSql = "UPDATE " + tableName + " SET copies_available = ? WHERE book_id = ?";
                    try (PreparedStatement updateStmt = createStatement(updateSql)) {
                        updateStmt.setInt(1, availableCopies - 1);
                        updateStmt.setString(2, bookId);
                        updateStmt.executeUpdate();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public ResultSet getBookById(String bookId) {
        String sql = "SELECT * FROM " + tableName + " WHERE book_id = ?";
        PreparedStatement stmt = createStatement(sql);
        return executeQuery(stmt, bookId);
    }
    public int addNewBook(String year, String... args) {
        if (args.length != 5) {
            return 0;
        }
        String sql = "INSERT INTO " + tableName + " (" +
                BookRepository.Column.columns.stream()
                        .map(GenericColumn::getName)
                        .collect(Collectors.joining(", ")) +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, 100)";
        Object[] params = new Object[]{args[0], args[1], args[2], args[3], "Unknown Genre", year, args[4]};
        return executeUpdate(createStatement(sql), params);
    }
}

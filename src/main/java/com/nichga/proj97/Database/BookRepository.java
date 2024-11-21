package com.nichga.proj97.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository extends GenericRepository {
    public BookRepository() {
        super("books");
    }
    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();
        public static final Column AUTHOR_ID = new Column(1,  "author_id");
        public static final Column BOOK_ID  = new Column(2,  "book_id");
        public static final Column COPY_AVA   = new Column(4,  "copies_available");
        public static final Column ISBN     = new Column(8,  "isbn");
        public static final Column PUB_YEAR     = new Column(16, "published_year");
        public static final Column PUB_ID = new Column(32, "published_id");
        public static final Column TITLE = new Column(64,"title");
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
}

package com.nichga.proj97.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class ReadingHistoryRepository extends GenericRepository {
    public ReadingHistoryRepository() {
        super("readinghistory");
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

    public boolean addNewHistory(String member_id, String book_id) {
        String sql = "INSERT INTO " + tableName
            + "(member_id, book_id, rating, comment) "
            + "VALUES (?, ?, null, null)";
        return executeUpdate(createStatement(sql), member_id, book_id) > 0;
    }

    public boolean addNewComment(String member_id, String book_id, String comment) {
        String sql = "UPDATE " + tableName
            + " SET comment = ? "
            + "WHERE member_id = ? AND book_id = ?";
        return executeUpdate(createStatement(sql), member_id, book_id, comment) > 0;
    }

    public boolean addNewRating(String rating, String member_id, String book_id) {
        String sql = "UPDATE " + tableName
            + " SET rating = ? "
            + "WHERE member_id = ? AND book_id = ?";
        return executeUpdate(createStatement(sql), member_id, book_id, rating) > 0;
    }
}

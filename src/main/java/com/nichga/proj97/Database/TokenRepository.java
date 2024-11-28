package com.nichga.proj97.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class TokenRepository extends GenericRepository {
    public TokenRepository() {
        super("tokens");
    }

    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();

        public static final Column TOKEN      = new Column(1, "token");
        public static final Column CREATED_AT = new Column(2, "created_at");
        public static final Column EXPIRES_AT = new Column(4, "expires_at");

        private Column(int index, String name) {
            super(index, name);
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

    public boolean pushToken(String generatedToken, String memberID, String bookID) {
        String sql = "INSERT INTO " + tableName
            + " (token, member_id, book_id) VALUES (?, ?, ?)";
        return executeUpdate(createStatement(sql), generatedToken, memberID, bookID) > 0;
    }

    public String[] getTokenInfo(String token) {
        String sql = "SELECT member_id, book_id FROM "
                + tableName + " WHERE token = ?";
        try (ResultSet rs = executeQuery(createStatement(sql), token)) {
            if (rs.next()) {
                String[] tokenInfo = new String[2];
                tokenInfo[0] = rs.getString(1);
                tokenInfo[1] = rs.getString(2);
                return tokenInfo;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean deleteToken(String token) {
        String sql = "DELETE FROM " + tableName + " WHERE token = ?";
        return executeUpdate(createStatement(sql), token) > 0;
    }

    public String getToken(String memberID, String bookID) {
        String sql = "SELECT token FROM " + tableName + " WHERE member_id = ? AND book_id = ?";
        try (ResultSet rs = executeQuery(createStatement(sql), memberID, bookID)) {
            if (rs.next()) {
                String token = rs.getString(1);
                return token;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean existToken(String memberID, String bookID) {
        String sql = "SELECT COUNT(*) FROM " + tableName
                + " WHERE member_id = ? AND book_id = ?";
        try (ResultSet rs = executeQuery(createStatement(sql), memberID, bookID)) {
            if (rs.next()) {
                if(rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

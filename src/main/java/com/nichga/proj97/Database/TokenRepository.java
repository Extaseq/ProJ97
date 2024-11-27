package com.nichga.proj97.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class TokenRepository extends GenericRepository {
    public TokenRepository() {
        super("Tokens");
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

    public boolean pushToken(String generatedToken) {
        String sql = "INSERT INTO" + tableName
            + " (token) VALUES (?)";
        return executeUpdate(createStatement(sql), generatedToken) > 0;
    }

    public boolean deleteToken(String token) {
        String sql = "DELETE FROM" + tableName + " WHERE token = ?";
        return executeUpdate(createStatement(sql), token) > 0;
    }
}

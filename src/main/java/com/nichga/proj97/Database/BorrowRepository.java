package com.nichga.proj97.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class BorrowRepository extends GenericRepository {
    public BorrowRepository() {
        super("borrow");
    }

    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();

        public static final Column BORROW_ID   = new Column(1,   "borrow_id");
        public static final Column MEMBER_ID   = new Column(2,   "member_id");
        public static final Column BOOK_ID     = new Column(4,   "book_id");
        public static final Column BORROW_DATE = new Column(8,   "borrow_date");
        public static final Column DUE_DATE    = new Column(16,  "due_date");
        public static final Column RETURN_DATE = new Column(32,  "return_date");

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

    public int getTotalBorrows() {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public int getTotalOverdue() {
        String sql = "SELECT COUNT(*) FROM " + tableName
            + "WHERE return_date IS NULL OR return_date > due_date";
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public boolean createBorrowRequest(String memberID, String bookID) {
        if (memberID == null || bookID == null) {
            return false;
        }
        String sql = "INSERT INTO " + tableName + "( "
            + Column.columns.stream()
                .filter(column -> column != Column.BORROW_ID)
                .map(GenericColumn::getName)
                .collect(Collectors.joining(", "))
            + ") VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), null)";
        return executeUpdate(createStatement(sql), memberID, bookID) > 0;
    }
}

package com.nichga.proj97.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class FinesRepository extends GenericRepository {
    public FinesRepository() {
        super("fines");
    }

    private final String FINE_FEE = "50.0";

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

    public boolean addFine(String borrowId) {
        String sql = "INSERT INTO" + tableName
            + "(borrow_id) VALUES(?)";
        return executeUpdate(createStatement(sql), borrowId) > 0;
    }

    public boolean increaseFineAmount(String borrowId) {
        String sql = "UPDATE" + tableName + "SET amount = "
            + "amount + ? WHERE borrow_id = ?";
        return executeUpdate(createStatement(sql), FINE_FEE, borrowId) > 0;
    }

    public boolean markAsPaid(String borrowId) {
        String sql = "UPDATE" + tableName + "SET paid = "
            + "true WHERE borrow_id = ?";
        return executeUpdate(createStatement(sql), borrowId) > 0;
    }
}

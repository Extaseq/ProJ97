package com.nichga.proj97.Database;

import java.sql.*;

/**
 * This class provides generic methods for interacting with a specific table in the database.
 * It is designed to be extended by other repository classes that interact with specific tables.
 */
public class GenericRepository {

    protected Connection connection;
    protected String tableName;

    /**
     * Constructor to initialize a connection to the database and set the table name.
     *
     * @param tableName The name of the table this repository will interact with.
     */
    public GenericRepository(String tableName) {
        this.connection = DatabaseConnector.getInstance().getConnection();
        this.tableName = tableName;
    }

    /**
     * Executes a SELECT query using the provided prepared statement and arguments.
     * The result is returned as a ResultSet.
     *
     * @param stmt  The prepared statement that represents the SQL SELECT query.
     * @param args  The arguments to be set in the prepared statement.
     * @return A ResultSet containing the results of the query.
     */
    protected ResultSet executeQuery(PreparedStatement stmt, String... args) {
        ResultSet rs = null;
        try {
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error here: " + e.getMessage());
        }
        return rs;
    }

    /**
     * Executes an UPDATE, INSERT, or DELETE query using the provided prepared statement and arguments.
     * The number of rows affected by the query is returned.
     *
     * @param stmt  The prepared statement that represents the SQL UPDATE/INSERT/DELETE query.
     * @param args  The arguments to be set in the prepared statement.
     * @return The number of rows affected by the query.
     */
    protected int executeUpdate(PreparedStatement stmt, String... args) {
        int rowAffected = 0;
        try {
            for (int i = 0; i < args.length; i++) {
                stmt.setString(i + 1, args[i]);
            }
            rowAffected = stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowAffected;
    }
}

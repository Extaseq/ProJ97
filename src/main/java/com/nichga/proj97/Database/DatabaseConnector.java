package com.nichga.proj97.Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Singleton class that manages the connection to the database using HikariCP.
 * It provides a connection pool to manage database connections efficiently.
 */
public final class DatabaseConnector {
    private static DatabaseConnector instance = null;
    private final HikariDataSource dataSource;

    /**
     * Private constructor to initialize the database connection pool with the specified configuration.
     * The configuration includes database URL, username, password, and connection pool settings.
     */
    private DatabaseConnector() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://nichga.mysql.database.azure.com/library");
        config.setUsername("kinastomes");
        config.setPassword("TestNigga12!");
        config.setMaximumPoolSize(100);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);
        dataSource = new HikariDataSource(config);
    }

    /**
     * Returns the singleton instance of the DatabaseConnector class.
     * If the instance does not exist, it will be created.
     *
     * @return The singleton instance of DatabaseConnector.
     */
    public static synchronized DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    /**
     * Retrieves a database connection from the connection pool.
     *
     * @return A Connection object to the database, or null if unable to get a connection.
     */
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Error getting connection: " + e.getMessage());
            return null;
        }
    }

    /**
     * Closes the database connection pool and releases all resources.
     * This should be called when the application is shutting down.
     */
    public void closeConnection() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}

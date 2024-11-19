package Database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {
    private static DatabaseConnector instance = null;
    private HikariDataSource dataSource;

    private DatabaseConnector() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://nichga.mysql.database.azure.com/library");
        config.setUsername("kinastomes");
        config.setPassword("TestNigga12!");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);

        dataSource = new HikariDataSource(config);
    }

    public static synchronized DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Error getting connection: " + e.getMessage());
            return null;
        }
    }

    public void closeConnection() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}

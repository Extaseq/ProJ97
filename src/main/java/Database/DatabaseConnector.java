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
        config.setJdbcUrl("jdbc:mysql://localhost:3306/library");
        config.setUsername("root");
        config.setPassword("Briantake12!");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);

        // Tạo HikariDataSource
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

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}

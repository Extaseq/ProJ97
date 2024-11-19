package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/library";

    private static final String USER = "root";

    private static final String PASSWORD = "Briantake12!";

    private static DatabaseConnector instance = null;

    private DatabaseConnector() {
    }

    public static synchronized DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error re-establishing connection: " + e.getMessage());
        }
        return null;
    }
}

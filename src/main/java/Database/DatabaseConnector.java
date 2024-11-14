package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/library";

    private static final String USER = "root";

    private static final String PASSWORD = "Briantake12!";

    private Connection connection = null;

    private static DatabaseConnector instance = null;

    private DatabaseConnector() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to Library Database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static synchronized DatabaseConnector getInstance() {
        if (instance == null) {
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
            System.out.println("Disconnected to the Library Database.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

package Database;

import java.sql.*;

public class GenericRepository {
    protected Connection connection;

    protected String tableName;

    public GenericRepository(String tableName) {
        this.connection = DatabaseConnector.getInstance().getConnection();
        this.tableName = tableName;
    }

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

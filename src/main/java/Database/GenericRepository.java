package Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public abstract class GenericRepository {
    protected Connection connection;

    protected String tableName = "";

    public GenericRepository(String tableName) {
        this.connection = DatabaseConnector.getInstance().getConnection();
        this.tableName = tableName;
    }

    protected abstract ResultSet executeQuery(PreparedStatement stmt, String... args) throws SQLException;

    protected abstract int executeUpdate(PreparedStatement stmt, String... args);
}

package Database;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserRepository extends GenericRepository {
    public UserRepository() {
        super("UserAccounts");
    }

    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();

        public static final Column ACCOUNT_ID    = new Column(1,   "account_id");
        public static final Column MEMBER_ID     = new Column(2,   "member_id");
        public static final Column USERNAME      = new Column(4,   "username");
        public static final Column PASSWORD_HASH = new Column(8,   "password_hash");
        public static final Column SALT          = new Column(16,  "salt");
        public static final Column CREATED_TIME  = new Column(32,  "created_time");
        public static final Column UPDATED_TIME  = new Column(64,  "updated_time");
        public static final Column STATUS        = new Column(128, "status");

        private Column(int index, String name) {
            super(index, name);
            columns.add(this);
        }
    }

    /**
     * Creates a PreparedStatement for the given SQL query.
     *
     * @param sql the SQL query string.
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

    /**
     * Retrieves user data based on specified attributes.
     *
     * @param findAttribute the number representation of the attributes to search by.
     * @param resultAttribute the number representation of the attributes to retrieve.
     * @param args the values to match for the search attributes.
     * @return a ResultSet containing the matching user data.
     */
    public ResultSet getUserInfoBy(long findAttribute, long resultAttribute, String... args) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(String.join(", ", Column.getAttributes(Column.columns, resultAttribute)));
        sql.append(" FROM ").append(tableName).append(" WHERE ");
        String[] findColumns = Column.getAttributes(Column.columns, findAttribute).toArray(new String[0]);
        sql.append(String.join(" = ? AND ", findColumns)).append(" = ?");

        System.out.println(sql);

        return executeQuery(createStatement(sql.toString()), args);
    }

    public int addNewUser(String... args) {
        String sql = "INSERT INTO " + tableName + " (" +
                Column.columns.stream()
                        .filter(column -> column != Column.ACCOUNT_ID) // Skip the primary key column
                        .map(GenericColumn::getName)
                        .collect(Collectors.joining(", ")) +
                ") VALUES (?, ?, ?, ?, NOW(), NOW(), 'active')";
        return executeUpdate(createStatement(sql), args);
    }

    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE username = ?";
        try (ResultSet rs = executeQuery(createStatement(sql), username)) {
            if (rs.next()) {
                return rs.getInt("COUNT(*)") == 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean changePassword(String username, String newPasswordHash) {
        String sql = "UPDATE " + tableName + " SET password_hash = ? WHERE username = ?";
        return executeUpdate(createStatement(sql), newPasswordHash, username) > 0;
    }

    public boolean verifyPassword(String username, String passwordHash) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE username = ? AND password_hash = ?";
        try (ResultSet rs = executeQuery(createStatement(sql), username, passwordHash)) {
            if (rs.next()) {
                return rs.getInt("COUNT(*)") == 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String[] getAuthInformation(String username) {
        long findAttr = Column.getNumberRepresentation(Column.columns, "username");
        long resAttr = Column.getNumberRepresentation(Column.columns, "password_hash", "salt");
        try (ResultSet rs = getUserInfoBy(findAttr, resAttr, username)) {
            if (rs.next()) {
                return new String[]{
                    rs.getString("password_hash"),
                    rs.getString("salt")
                };
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getLastUserId() {
        String sql = "SELECT MAX(account_id) FROM " + tableName;
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

}

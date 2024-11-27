package com.nichga.proj97.Database;

import com.nichga.proj97.Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for interacting with the Users table in the database.
 * It provides methods for querying, updating, and managing user-related data.
 */
public final class UserRepository extends GenericRepository {
    public UserRepository() {
        super("UserAccounts");
    }

    private final String TODAY = "DATE_SUB(CURDATE(), INTERVAL 1 DAY)";

    private final String THIS_WEEK = "DATE_SUB(CURDATE(), INTERVAL WEEKDAY(CURDATE()) DAY)";

    private final String THIS_MONTH = "DATE_FORMAT(CURDATE(), '%Y-%m-01');";

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

    /**
     * Retrieves user information based on a specific attribute and corresponding result attributes.
     *
     * @param findAttribute    The attribute to search by (e.g., username).
     * @param resultAttribute  The result attributes to return (e.g., password, salt).
     * @param args             The values to be used in the query.
     * @return A ResultSet containing the user information.
     */
    public ResultSet getUserInfoBy(long findAttribute, long resultAttribute, String... args) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(String.join(", ", Column.getAttributes(Column.columns, resultAttribute)));
        sql.append(" FROM ").append(tableName);
        if (findAttribute > 0) {
            sql.append(" WHERE ");
            String[] findColumns = Column.getAttributes(Column.columns, findAttribute).toArray(new String[0]);
            sql.append(String.join(" = ? AND ", findColumns)).append(" = ?");
        }
        sql.append(" ORDER BY ").append(Column.ACCOUNT_ID.getName());
        return executeQuery(createStatement(sql.toString()), args);
    }

    public int getTotalUsers() {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public ObservableList<User> getLatestUserByTime(String time) {
        String sql = "SELECT account_id, username FROM UserAccounts "
            + "WHERE created_time >= ";
        switch (time) {
            case "Today":
                sql += TODAY;
                break;
            case "This Week":
                sql += THIS_WEEK;
                break;
            case "This Month":
                sql += THIS_MONTH;
                break;
            default:
                sql += "0";
        }

        ObservableList<User> users = FXCollections.observableArrayList();
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("account_id"),
                    rs.getString("username")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return users;
    }


    /**
     * Adds a new user to the user table.
     *
     * @param args The user details (e.g., username, password, etc.) to insert into the table.
     * @return The number of rows affected (1 if the user was added successfully, 0 otherwise).
     */
    public int addNewUser(String... args) {
        if (args.length != 4) {
            return 0;
        }
        String sql = "INSERT INTO " + tableName + " (" +
                Column.columns.stream()
                        .filter(column -> column != Column.ACCOUNT_ID)
                        .map(GenericColumn::getName)
                        .collect(Collectors.joining(", ")) +
                ") VALUES (?, ?, ?, ?, NOW(), NOW(), 'active')";
        return executeUpdate(createStatement(sql), args);
    }

    /**
     * Checks if a user with the given username already exists in the database.
     *
     * @param username The username to search for.
     * @return {@code true} if the user exists, {@code false} otherwise.
     */
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

    /**
     * Changes the password of a user identified by their username.
     *
     * @param username        The username of the user whose password is to be changed.
     * @param newPasswordHash The new hashed password to set for the user.
     * @return {@code true} if the password was successfully changed, {@code false} otherwise.
     */
    public boolean changePassword(String newPasswordHash, String username) {
        String sql = "UPDATE " + tableName + " SET password_hash = ? WHERE username = ?";
        return executeUpdate(createStatement(sql), newPasswordHash, username) > 0;
    }

    /**
     * Retrieves the authentication information (password hash and salt) for a given username.
     *
     * @param username The username to retrieve the authentication information for.
     * @return An array containing the password hash and salt, or {@code null} if the user is not found.
     */
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

    public int getMemberId(String username) {
        long findAttr = Column.getNumberRepresentation(Column.columns, "username");
        long resAttr = Column.getNumberRepresentation(Column.columns, "member_id");
        try (ResultSet rs = getUserInfoBy(findAttr, resAttr, username)) {
            if (rs.next()) {
                return rs.getInt("member_id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    /**
     * Retrieves the ID of the last user added to the database.
     *
     * @return The ID of the last user, or 0 if no user exists.
     */
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

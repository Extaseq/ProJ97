package com.nichga.proj97.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for interacting with the Members table in the database.
 * It provides methods for querying, updating, and managing user-related data.
 */
public class MemberRepository extends GenericRepository {
    public MemberRepository() {
        super("Members");
    }

    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();

        public static final Column MEMBER_ID = new Column(1,  "member_id");
        public static final Column FULLNAME  = new Column(2,  "fullname");
        public static final Column ADDRESS   = new Column(4,  "address");
        public static final Column EMAIL     = new Column(8,  "email");
        public static final Column PHONE     = new Column(16, "phone");
        public static final Column JOIN_DATE = new Column(32, "join_date");

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
     * Retrieves member information based on a specific attribute and corresponding result attributes.
     *
     * @param findAttribute    The attribute to search by (e.g., username).
     * @param resultAttribute  The result attributes to return (e.g., password, salt).
     * @param args             The values to be used in the query.
     * @return A ResultSet containing the member information.
     */
    public ResultSet getMemberInfoBy(long findAttribute, long resultAttribute, String... args) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(String.join(", ", Column.getAttributes(Column.columns, resultAttribute)));
        sql.append(" FROM ").append(tableName).append(" WHERE ");
        String[] findColumns = Column.getAttributes(Column.columns, findAttribute).toArray(new String[0]);
        sql.append(String.join(" = ? AND ", findColumns)).append(" = ?");
        return executeQuery(createStatement(sql.toString()), args);
    }

    /**
     * Adds a new member to the user table.
     *
     * @param fullname The member name to insert into the table.
     * @return The number of rows affected (1 if the member was added successfully, 0 otherwise).
     */
    public int addNewMember(String fullname) {
        if (fullname == null || fullname.trim().isEmpty()) {
            return 0;
        }
        String sql = "INSERT INTO " + tableName + " (" +
                Column.columns.stream()
                        .filter(column -> column != Column.MEMBER_ID)
                        .map(GenericColumn::getName)
                        .collect(Collectors.joining(", ")) +
                ") VALUES (?, null, null, null, NOW())";
        return executeUpdate(createStatement(sql), fullname);
    }


    public int updateInfo(int member_id, int updateAttribute, String... args) {
        StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");
        List<String> colToUpdate = Column.getAttributes(Column.columns, updateAttribute);
        if (colToUpdate.size() != args.length) {
            return 0;
        }
        for (int i = 0; i < colToUpdate.size(); i++) {
            sql.append(colToUpdate.get(i)).append(" = ?");
            if (i < colToUpdate.size() - 1) {
                sql.append(", ");
            }
        }
        sql.append(" WHERE member_id = ").append(member_id);
        return executeUpdate(createStatement(sql.toString()), args);
    }
}

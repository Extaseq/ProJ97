package com.nichga.proj97.Database;

import com.nichga.proj97.Services.TokenProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class BorrowRepository extends GenericRepository {
    public BorrowRepository() {
        super("borrow");
    }

    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();

        public static final Column BORROW_ID   = new Column(1,   "borrow_id");
        public static final Column MEMBER_ID   = new Column(2,   "member_id");
        public static final Column BOOK_ID     = new Column(4,   "book_id");
        public static final Column BORROW_DATE = new Column(8,   "borrow_date");
        public static final Column DUE_DATE    = new Column(16,  "due_date");
        public static final Column RETURN_DATE = new Column(32,  "return_date");

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

    public boolean existBorrow(String memberID, String bookID) {
        String sql = "SELECT COUNT(*) FROM " + tableName
            + " WHERE member_id = ? AND book_id = ? AND return_date IS NULL";
        try (ResultSet rs = executeQuery(createStatement(sql), memberID, bookID)) {
            if (rs.next()) {
                if(rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean memberExists(int member_id) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE member_id = ?";
        try (ResultSet rs = executeQuery(createStatement(sql), String.valueOf(member_id))) {
            if (rs.next()) {
                return rs.getInt("COUNT(*)") >= 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean bookExists(String book_id) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE book_id = ?";
        try (ResultSet rs = executeQuery(createStatement(sql), String.valueOf(book_id))) {
            if (rs.next()) {
                return rs.getInt("COUNT(*)") >= 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int getTotalBorrows() {
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

    public int getTotalOverdue() {
        String sql = "SELECT COUNT(*) FROM " + tableName
            + " WHERE (return_date IS NULL AND NOW() > due_date) OR (return_date > due_date)";
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public String createBorrowRequest(String memberID, String bookID) {
        if (existBorrow(memberID, bookID)) {
            return null;
        }
        return TokenProvider.generateToken(memberID, bookID);
    }

    public boolean applyBorrowRequest(String token) {
        if (token == null) {
            return false;
        }
        String[] tokenInfo = TokenProvider.getTokenInfo(token);
        if (tokenInfo == null) return false;
        TokenProvider.deleteToken(token);
        String sql = "INSERT INTO " + tableName + "( "
            + Column.columns.stream()
                .filter(column -> column != Column.BORROW_ID)
                .map(GenericColumn::getName)
                .collect(Collectors.joining(", "))
            + ") VALUES (?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 7 DAY), null)";
        return executeUpdate(createStatement(sql), tokenInfo) > 0;
    }

    public boolean addNewComment(String comment, String member_id, String book_id) {
        String sql = "UPDATE " + tableName
                + " SET review_text = ? "
                + "WHERE member_id = ? AND book_id = ?";
        return executeUpdate(createStatement(sql), comment, member_id, book_id) > 0;
    }

    public boolean addNewRating(String rating, String member_id, String book_id) {
        String sql = "UPDATE " + tableName
                + " SET rating = ? "
                + "WHERE member_id = ? AND book_id = ?";
        return executeUpdate(createStatement(sql), rating, member_id, book_id ) > 0;
    }

    public String getAllComments(String book_id) {
        String sql = "SELECT members.fullname, temp.review_text, temp.rating "
                + " FROM members"
                + " NATURAL JOIN ( "
                + " SELECT review_text, member_id, rating "
                + " FROM " + tableName
                + " WHERE review_text IS NOT NULL AND book_id = ? )"
                + " AS temp";
        String result = "";
        try (ResultSet rs = executeQuery(createStatement(sql), book_id)) {
            while (rs.next()) {
                String name = rs.getString(1);
                String comment = rs.getString(2);
                int rating = rs.getInt(3);
                result += name + ": " + comment + ". Rating: " + rating + "/5\n";
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public void returnBook(String memberID, String bookID) {
        String sql = "UPDATE " + tableName
                + " SET return_date = NOW() WHERE member_id = ? AND book_id = ?";
        executeUpdate(createStatement(sql), memberID, bookID);
    }
}

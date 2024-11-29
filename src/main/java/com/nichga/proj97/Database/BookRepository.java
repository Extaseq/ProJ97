package com.nichga.proj97.Database;

import com.nichga.proj97.Model.Book;
import com.nichga.proj97.Model.DisplayBook;
import com.nichga.proj97.Model.User;
import com.nichga.proj97.Users;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class BookRepository extends GenericRepository {
    public BookRepository() {
        super("books");
    }

    public static class Column extends GenericColumn {
        public static final List<Column> columns = new ArrayList<>();

        public static final Column BOOK_ID          = new Column(1,   "book_id");
        public static final Column TITLE            = new Column(2,   "title");
        public static final Column AUTHOR           = new Column(4,   "author");
        public static final Column PUBLISHER        = new Column(8,   "publisher");
        public static final Column GENRE            = new Column(16,  "genre");
        public static final Column PUBLISHED_YEAR   = new Column(32,  "published_year");
        public static final Column ISBN             = new Column(64,  "isbn");
        public static final Column COPIES_AVAILABLE = new Column(128, "copies_available");

        private Column(int idx, String name) {
            super(idx, name);
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

    public boolean insertBook(Book book) {
        String book_id = book.getId();

        String title = book.getVolumeInfo().getTitle();
        String author = book.getVolumeInfo().getAuthors() != null ? book.getVolumeInfo().getAuthors().toString() : null;
        String publisher = book.getVolumeInfo().getPublisher();
        String genre = book.getVolumeInfo().getCategories() != null ? book.getVolumeInfo().getCategories().toString() : null;
        String published_year = book.getVolumeInfo().getPublishedDate() != null ? book.getVolumeInfo().getPublishedDate().toString() : null;

        String isbn = null;
        if (book.getVolumeInfo().getIndustryIdentifiers() != null && !book.getVolumeInfo().getIndustryIdentifiers().isEmpty()) {
            isbn = book.getVolumeInfo().getIndustryIdentifiers().getFirst().getIdentifier();
        }

        String cover_url = book.getVolumeInfo().getImageLinks() != null ? book.getVolumeInfo().getImageLinks().getThumbnail() : null;

        String sql = "INSERT INTO books (book_id, title, author, publisher, genre, published_year, isbn, copies_available, cover_url) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, 100, ?)";

        return executeUpdate(createStatement(sql), book_id, title, author, publisher, genre, published_year, isbn, cover_url) > 0;
    }

    public boolean insertBook(String book_id, String title, String author, String publisher, String genre, String published_year, String isbn, String available, String cover_url) {
        String sql = "INSERT INTO books VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        return executeUpdate(createStatement(sql), book_id, title, author, publisher, genre, published_year, isbn, available, cover_url) > 0;
    }

    public boolean bookAvailable(String book_id) {
        String sql = "SELECT COUNT(*), copies_available FROM " + tableName + " WHERE book_id = ?";
        try (ResultSet rs = executeQuery(createStatement(sql), book_id)) {
            if (rs.next()) {
                return (rs.getInt(1) == 1)
                    && rs.getInt(2) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ObservableList<DisplayBook> getAllBook() {
        String sql = "SELECT * FROM " + tableName;
        ObservableList<DisplayBook> result = FXCollections.observableArrayList();
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            while (rs.next()) {
                result.add(new DisplayBook(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public ObservableList<DisplayBook> getUserBooks(String memberId) {
        String sql = "SELECT * FROM " + tableName + " JOIN ( SELECT book_id FROM borrow WHERE"
                + " member_id = ? " +"AND return_date IS NULL" +
                " UNION SELECT book_id FROM tokens WHERE member_id = ? " +
                ") AS temp on temp.book_id =" + tableName+ ".book_id";
        ObservableList<DisplayBook> result = FXCollections.observableArrayList();
        try (ResultSet rs = executeQuery(createStatement(sql), memberId, memberId)) {
            while (rs.next()) {
                result.add(new DisplayBook(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public ObservableList<DisplayBook> getUserFinishedBooks(String memberId) {
        String sql = "SELECT * FROM " + tableName + " JOIN ( SELECT book_id FROM readinghistory WHERE"
                + " member_id = ? ) AS temp on temp.book_id =" + tableName+ ".book_id";
        ObservableList<DisplayBook> result = FXCollections.observableArrayList();
        try (ResultSet rs = executeQuery(createStatement(sql), memberId)) {
            while (rs.next()) {
                result.add(new DisplayBook(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int getTotalBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public boolean adjustAfterBorrow(String bookId) {
        String subquery = "SELECT copies_available FROM books WHERE book_id = ?";
        int available = 0;
        try (ResultSet rs = executeQuery(createStatement(subquery),bookId)) {
            if (rs.next()) {
                available = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Borrow book error at get number of copies");
        }
        String new_available = String.valueOf(available-1);
        String sql = "UPDATE books SET copies_available = ? WHERE book_id = ?";
        return executeUpdate(createStatement(sql), new_available, bookId) > 0;
    }
    public ResultSet recommendDocument(String userId) {
        // Câu lệnh SQL để lấy các book_id của sách đã mượn
        String reading = "SELECT book_id FROM borrow WHERE member_id = ?";
        ResultSet readingResult = executeQuery(createStatement(reading), userId);
        List<String> bookReading = new ArrayList<>();
        if(readingResult == null)
        try {
            while (readingResult.next()) {
                bookReading.add(readingResult.getString("book_id"));
                System.out.println("bookReading: "+bookReading);
            }
        } catch (SQLException e) {
            System.out.println("Can not list reading books");
        }
        if(bookReading.isEmpty() || bookReading == null) {
            bookReading.add("abc,.,.,.,.,.,.a,.z,.,");
        }
        for(String x:bookReading) {
            System.out.println(x);
        }
        // Xây dựng phần IN clause từ bookReading
        StringBuilder inClause = new StringBuilder();
        for (int i = 0; i < bookReading.size(); i++) {
            inClause.append("?,");
        }
        if (bookReading.size() > 0) {
            inClause.deleteCharAt(inClause.length() - 1);  // Xóa dấu ',' cuối cùng
        }

        // Subquery để lấy các tag từ sách gần đây
        String subquery = "WITH RecentBooks AS ("
                + "    SELECT b.book_id AS book_id, b.genre AS genre "
                + "    FROM books b "
                + "    INNER JOIN borrow bo ON bo.book_id = b.book_id "
                + "    WHERE bo.member_id = ? "
                + "    ORDER BY bo.borrow_date DESC "
                + "    LIMIT 5 "
                + "), "
                + "SplitTags AS ("
                + "    SELECT b.book_id, "
                + "           SUBSTRING_INDEX(SUBSTRING_INDEX(b.genre, '&', n.n), '&', -1) AS tag "
                + "    FROM RecentBooks b "
                + "    CROSS JOIN (SELECT 1 AS n UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5) n "
                + "    WHERE n.n <= 1 + (LENGTH(b.genre) - LENGTH(REPLACE(b.genre, '&', ''))) "
                + ") "
                + "SELECT tag, COUNT(*) AS tag_count "
                + "FROM SplitTags "
                + "GROUP BY tag "
                + "ORDER BY tag_count DESC, tag "
                + "LIMIT 5;";

        ArrayList<String> tags = new ArrayList<>();
        try (ResultSet rs = executeQuery(createStatement(subquery), userId)) {
            while (rs.next()) {
                tags.add(rs.getString("tag"));
            }
            // Đảm bảo có đủ 5 tag (thêm các giá trị mặc định nếu thiếu)
            while (tags.size() < 5) {
                tags.add("abcxyz");
            }
        } catch (SQLException e) {
            System.out.println("Can not generate recommend tags");
        }
        for(int i = 0;i < 5;i ++) {
            System.out.println(tags.get(i));
        }
        // Câu lệnh chính để lấy các sách đề xuất dựa trên các tag và số lần mượn
        String sql = "WITH count_tag AS ("
                + "    SELECT book_id, "
                + "           SUM(CASE WHEN genre LIKE ? THEN 1 ELSE 0 END + "
                + "                   CASE WHEN genre LIKE ? THEN 1 ELSE 0 END + "
                + "                   CASE WHEN genre LIKE ? THEN 1 ELSE 0 END + "
                + "                   CASE WHEN genre LIKE ? THEN 1 ELSE 0 END + "
                + "                   CASE WHEN genre LIKE ? THEN 1 ELSE 0 END) AS CountTag "
                + "    FROM books "
                + "    GROUP BY book_id "
                + "), "
                + "count_borrow AS ("
                + "    SELECT book_id, COUNT(*) AS counted "
                + "    FROM borrow "
                + "    GROUP BY book_id "
                + ") "
                + "SELECT b.book_id "
                + "FROM books b "
                + "LEFT JOIN count_borrow cb ON b.book_id = cb.book_id "
                + "INNER JOIN count_tag ct ON ct.book_id = b.book_id "
                + "WHERE b.book_id NOT IN (" + inClause.toString() + ") "
                + "ORDER BY ct.CountTag * COALESCE(cb.counted,0) DESC, COALESCE(cb.counted,0) DESC "
                + "LIMIT 5;";

        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            for (int i = 0; i < 5; i++) {
                pstmt.setString(i + 1, "%" + tags.get(i) + "%");
            }
            for (int i = 0; i < bookReading.size(); i++) {
                pstmt.setString(i + 6, (bookReading.get(i) == null)?Integer.toString(i):bookReading.get(i));
            }
            System.out.println(pstmt.toString());
            ResultSet rs = pstmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            System.out.println("Error while recommending documents: " + e.getMessage());
        }

        return null;
    }


    public ObservableList<DisplayBook> getUserRecommendBooks(Users user) {
        String[] bookId = user.recommendDoc(user.getId());
        for (String bookId1 : bookId) {
            System.out.println(bookId1);
        }
        ObservableList<DisplayBook> result = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + tableName + " WHERE book_id = '" + bookId[0] + "'"
                + " OR book_id = '" + bookId[1] + "'"
                + " OR book_id = '" + bookId[2] + "'"
                + " OR book_id = '" + bookId[3] + "'"
                + " OR book_id = '" + bookId[4] + "'";
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            while (rs.next()) {
                result.add(new DisplayBook(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public ObservableList<DisplayBook> getMostPopularBooks() {
        ObservableList<DisplayBook> result = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + tableName + " JOIN ( SELECT book_id, COUNT(*) AS totalview FROM borrow GROUP BY book_id " +
                "ORDER BY totalview DESC LIMIT 5 ) AS temp on temp.book_id = " + tableName+ ".book_id";
        try (ResultSet rs = executeQuery(createStatement(sql))) {
            while (rs.next()) {
                result.add(new DisplayBook(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getInt(8),
                        rs.getString(9)
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    public boolean adjustAfterReturn(String bookId) {
        String subquery = "SELECT copies_available FROM books WHERE book_id = ?";
        int available = 0;
        try (ResultSet rs = executeQuery(createStatement(subquery),bookId)) {
            if (rs.next()) {
                available = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Borrow book error at get number of copies");
        }
        String new_available = String.valueOf(available+1);
        String sql = "UPDATE books SET copies_available = ? WHERE book_id = ?";
        return executeUpdate(createStatement(sql), new_available, bookId) > 0;
    }
}

package com.nichga.proj97.Controller;

import com.nichga.proj97.Database.DatabaseConnector;
import com.nichga.proj97.Model.BorrowHistory;
import com.nichga.proj97.Model.TopViewBook;
import com.nichga.proj97.Util.ImageHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BookSectionController {
    @FXML
    private ImageView book_cover_1;

    @FXML
    private ImageView book_cover_2;

    @FXML
    private ImageView book_cover_3;

    @FXML
    private ImageView book_cover_4;

    @FXML
    private ImageView book_cover_5;

    @FXML
    private ImageView book_cover_6;

    @FXML
    private Text book_title_1;

    @FXML
    private Text book_title_2;

    @FXML
    private Text book_title_3;

    @FXML
    private Text book_title_4;

    @FXML
    private Text book_title_5;

    @FXML
    private Text book_title_6;

    @FXML
    private Text book_author_1;

    @FXML
    private Text book_author_2;

    @FXML
    private Text book_author_3;

    @FXML
    private Text book_author_4;

    @FXML
    private Text book_author_5;

    @FXML
    private Text book_author_6;

    @FXML
    private TableView<BorrowHistory> book_status;

    List<ImageView> cover = new ArrayList<>();

    List<Text> title = new ArrayList<>();

    List<Text> author = new ArrayList<>();

    private void initTextContainer() {
        cover.add(book_cover_1);
        cover.add(book_cover_2);
        cover.add(book_cover_3);
        cover.add(book_cover_4);
        cover.add(book_cover_5);
        cover.add(book_cover_6);
        title.add(book_title_1);
        title.add(book_title_2);
        title.add(book_title_3);
        title.add(book_title_4);
        title.add(book_title_5);
        title.add(book_title_6);
        author.add(book_author_1);
        author.add(book_author_2);
        author.add(book_author_3);
        author.add(book_author_4);
        author.add(book_author_5);
        author.add(book_author_6);
    }

    private final String getTableView =
       """
       SELECT member_id, fullname, book_id, title, author,
           IF(return_date IS NOT NULL, IF(return_date <= due_date, '-', CONCAT(DATEDIFF(return_date, due_date), ' days')),
               IF(NOW() <= due_date, '-', DATEDIFF(NOW(), due_date))) as overdue
           ,
           IF(return_date IS NULL, IF(NOW() <= due_date, 'Reading', 'Delay'),
               IF(return_date <= due_date, 'Returned', 'Returned (late)')) AS status
            ,
           IF(fine_id IS NULL, '-', amount) AS fine
       FROM borrow
       JOIN members USING (member_id)
       JOIN books USING (book_id)
       LEFT JOIN fines USING (borrow_id)""";

    private final String getTopView =
        """
        SELECT cover_url, title, author
        FROM books
        JOIN (
            SELECT COUNT(*) as total, book_id
            FROM borrow
            GROUP BY book_id
            ORDER BY total DESC
            LIMIT 6
        ) AS TotalBorrow USING (book_id);""";

    private final String[] label = {
            "member_id", "fullname", "book_id", "title",
            "author", "overdue", "status", "fine"
    };

    private void initTableView() {
        ObservableList<TableColumn<BorrowHistory, ?>> columns = book_status.getColumns();
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellValueFactory(new PropertyValueFactory<>(label[i]));
        }
        ObservableList<BorrowHistory> data = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnector.getInstance().getConnection();
             PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(getTableView);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                data.add(new BorrowHistory(
                    resultSet.getInt("member_id"),
                    resultSet.getString("fullname"),
                    resultSet.getString("book_id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("overdue"),
                    resultSet.getString("status"),
                    resultSet.getString("fine")
                ));
            }
            book_status.setItems(data);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void initTopView() {
        ObservableList<TopViewBook> data = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnector.getInstance().getConnection();
             PreparedStatement statement = Objects.requireNonNull(connection).prepareStatement(getTopView);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                data.add(new TopViewBook(
                    resultSet.getString("cover_url"),
                    resultSet.getString("title"),
                    resultSet.getString("author")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        try {
            for (int i = 0; i < data.size(); i++) {
                String cover_url = data.get(i).getUrl() == null ?
                        "https://i.imgur.com/VwiPLSU.png" : data.get(i).getUrl();
                String book_title = data.get(i).getTitle() == null ?
                        "Unknown" : data.get(i).getTitle().split(",")[0].replaceAll("[\\[\\]\"]", "");
                String book_author = data.get(i).getAuthor() == null ?
                        "Unknown" : data.get(i).getAuthor().split(",")[0].replaceAll("[\\[\\]\"]", "");

                cover.get(i).setImage(new Image(cover_url));
                ImageHelper.roundImage(cover.get(i), 20);
                title.get(i).setText(book_title);
                author.get(i).setText(book_author);

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    @FXML
    private void initialize() {
        initTextContainer();
        initTableView();
        initTopView();
    }
}

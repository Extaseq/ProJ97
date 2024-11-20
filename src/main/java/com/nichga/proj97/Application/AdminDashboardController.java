package com.nichga.proj97.Application;

import com.nichga.proj97.Database.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.nichga.proj97.Util.ImageHelper;

public class AdminDashboardController {
    @FXML
    private TextFlow information;

    @FXML
    private Text username_text;

    @FXML
    private TextFlow current_time;

    @FXML
    private ComboBox<String> timeBox;

    @FXML
    private ImageView total_book_icon;

    @FXML
    private ImageView borrowed_book_icon;

    @FXML
    private ImageView overdue_book_icon;

    @FXML
    private ImageView total_user_icon;

    @FXML
    private ImageView home_button;

    @FXML
    private Text total_book;

    @FXML
    private Text total_borrowed;

    @FXML
    private Text total_overdue;

    @FXML
    private Text total_users;

//    @FXML
//    TableView<User> small_user_table;
//
//    @FXML
//    TableView<Documents> small_document_table;

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, yyyy | EEEE, hh:mm a");

    private final DatabaseConnector dbConnector = DatabaseConnector.getInstance();

    private void initializeTextFields() {
        Text text1 = new Text("Hello, ");
        text1.setFill(Color.web("#e5e5e5"));
        text1.setFont(Font.font("Eina03-Bold", 35));

        username_text = new Text("Admin!");
        username_text.setFill(Color.web("#f65867"));
        username_text.setFont(Font.font("Eina03-Bold", 35));

        LocalDateTime now = LocalDateTime.now();
        Text dateTime = new Text(now.format(dtf));
        dateTime.setFill(Color.web("#e5e5e5"));
        dateTime.setFont(Font.font("Eina03-SemiBold", 20));

        information.getChildren().addAll(text1, username_text);
        current_time.getChildren().addAll(dateTime);
    }

    private void initializeComboBoxes() {
        timeBox.getItems().addAll("Today", "This Week", "This Month", "Overall");
        timeBox.setPromptText("Today");
        timeBox.setValue("Today");
    }

    private void initializeImage() {
        ImageHelper.roundImage(total_book_icon, -1);
        ImageHelper.roundImage(total_user_icon, -1);
        ImageHelper.roundImage(borrowed_book_icon, -1);
        ImageHelper.roundImage(overdue_book_icon, -1);
        ImageHelper.roundImage(home_button, -1);
    }

    private void initializeTable() {

    }

    public void updateUsername(String username) {
        this.username_text.setText(username);
    }

    // Test
    public void testComboBoxEvent() {
        timeBox.setOnAction(event -> {
            String value = timeBox.getValue();
            if ("Overall".equals(value)) {
                updateUsername("Faker!");
            } else {
                updateUsername("KinasTomes!");
            }
        });
    }


    // Test
    public void prepareUserTableView() {
//        small_user_table.getColumns().clear();
//
//        TableColumn<User, Integer> UserID = new TableColumn<>("User ID");
//        UserID.setCellValueFactory(new PropertyValueFactory<>("UserID"));
//        UserID.setPrefWidth(150);
//        TableColumn<User, String> UserName = new TableColumn<>("User Name");
//        UserName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
//        UserName.setPrefWidth(200);
//        TableColumn<User, Integer> BookBorrowed = new TableColumn<>("Book Borrowed");
//        BookBorrowed.setCellValueFactory(new PropertyValueFactory<>("BorrowedBook"));
//        BookBorrowed.setPrefWidth(150);
//        TableColumn<User, Integer> BookOverdue = new TableColumn<>("Book Overdue");
//        BookOverdue.setCellValueFactory(new PropertyValueFactory<>("OverdueBook"));
//        BookOverdue.setPrefWidth(150);
//
//        small_user_table.getColumns().add(UserID);
//        small_user_table.getColumns().add(UserName);
//        small_user_table.getColumns().add(BookBorrowed);
//        small_user_table.getColumns().add(BookOverdue);
    }

    @FXML
    private void initialize() {
        initializeTextFields();
        initializeComboBoxes();
        initializeImage();

        prepareUserTableView();

        testComboBoxEvent();
    }
}

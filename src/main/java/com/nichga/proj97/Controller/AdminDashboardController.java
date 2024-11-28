package com.nichga.proj97.Controller;

import com.nichga.proj97.Model.DisplayBook;
import com.nichga.proj97.Model.User;
import com.nichga.proj97.Services.DatabaseService;
import com.nichga.proj97.Util.Animation;
import com.nichga.proj97.Util.TableViewHelper;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
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
    private VBox inner_pane;

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
    private ImageView refresh_button;

    @FXML
    private ImageView book_section;

    @FXML
    private ImageView book_cover_1;

    @FXML
    private Text book_title_1;

    @FXML
    private Text book_author_1;

    @FXML
    private Text total_book;

    @FXML
    private Text total_borrowed;

    @FXML
    private Text total_overdue;

    @FXML
    private Text total_users;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TableView<User> small_user_table;

    @FXML
    private TableView<DisplayBook> small_book_table;

    private final DatabaseService dbs = new DatabaseService();

    private void initializeTextFields() {
        Text text1 = new Text("Hello, ");
        text1.setFill(Color.web("#e5e5e5"));
        text1.setFont(Font.font("Eina03-Bold", 35));

        username_text = new Text("Admin!");
        username_text.setFill(Color.web("#f65867"));
        username_text.setFont(Font.font("Eina03-Bold", 35));

        LocalDateTime now = LocalDateTime.now();
        Text dateTime = new Text(now.format(
            DateTimeFormatter.ofPattern("MMM dd, yyyy | EEEE, hh:mm a")
        ));
        dateTime.setFill(Color.web("#e5e5e5"));
        dateTime.setFont(Font.font("Eina03-SemiBold", 20));

        information.getChildren().addAll(text1, username_text);
        current_time.getChildren().addAll(dateTime);

        total_book.setText(String.valueOf(dbs.getBookRepo().getTotalBooks()));
        total_users.setText(String.valueOf(dbs.getUserRepo().getTotalUsers()));
        total_borrowed.setText(String.valueOf(dbs.getBorrowRepo().getTotalBorrows()));
        total_overdue.setText(String.valueOf(dbs.getBorrowRepo().getTotalOverdue()));
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
        onRefreshButtonClicked();
    }

    private void onRefreshButtonClicked() {
        refresh_button.setOnMousePressed(_ -> {
            Animation.glowAndRotate(refresh_button).play();
            new Thread(this::refreshData).start();
        });
    }

    private void refreshData() {
        Platform.runLater(() -> {
            updateText(total_book, String.valueOf(dbs.getBookRepo().getTotalBooks()));
            updateText(total_users, String.valueOf(dbs.getUserRepo().getTotalUsers()));
            updateText(total_borrowed, String.valueOf(dbs.getBorrowRepo().getTotalBorrows()));
            updateText(total_overdue, String.valueOf(dbs.getBorrowRepo().getTotalOverdue()));
            System.out.println("Updated!");
        });
    }

    public void updateText(Text text, String content) {
        text.setText(content);
    }

    public void onComboBoxEvent() {
        timeBox.setOnAction(_ -> {
            String value = timeBox.getValue();
            small_user_table.setItems(
                dbs.getUserRepo().getLatestUserByTime(value)
            );
        });
    }

    private void initBookTable() {
        ObservableList<TableColumn<DisplayBook, ?>> columns = small_book_table.getColumns();
        String[] label = {"bookId", "title", "author", "genre", "available"};
        for (int i = 0; i < columns.size(); i++) {
            columns.get(i).setCellValueFactory(new PropertyValueFactory<>(label[i]));
        }
        ObservableList<DisplayBook> db = dbs.getBookRepo().getAllBook();
        small_book_table.setItems(db);
    }

    public void prepareUserTableView() {
        small_user_table.getColumns().clear();

        TableColumn<User, Integer> UserID = new TableColumn<>("User ID");
        UserID.setCellValueFactory(new PropertyValueFactory<>("AccountID"));
        UserID.setPrefWidth(150);
        TableColumn<User, String> UserName = new TableColumn<>("User Name");
        UserName.setCellValueFactory(new PropertyValueFactory<>("Username"));
        UserName.setPrefWidth(200);
        TableColumn<User, Integer> BookBorrowed = new TableColumn<>("Book Borrowed");
        BookBorrowed.setCellValueFactory(new PropertyValueFactory<>("BorrowedBook"));
        BookBorrowed.setPrefWidth(150);
        TableColumn<User, Integer> BookOverdue = new TableColumn<>("Book Overdue");
        BookOverdue.setCellValueFactory(new PropertyValueFactory<>("OverdueBook"));
        BookOverdue.setPrefWidth(150);
        small_user_table.getColumns().add(UserID);
        small_user_table.getColumns().add(UserName);
        small_user_table.getColumns().add(BookBorrowed);
        small_user_table.getColumns().add(BookOverdue);
        ObservableList<User> users = dbs.getUserRepo().getLatestUserByTime("Today");
        small_user_table.setItems(users);
        TableViewHelper.disableScrollBars(small_user_table);
    }

    public void initSwitchMenu() {
        ObservableList<Node> originalContent = FXCollections.observableArrayList(inner_pane.getChildren());

        home_button.setOnMouseClicked(_ -> {
            if (!inner_pane.getChildren().equals(originalContent)) {
                inner_pane.getChildren().clear();
                inner_pane.getChildren().addAll(originalContent);
            }
        });

        Node bookSection = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nichga/proj97/BookSection.fxml"));
            bookSection = loader.load();
        } catch (IOException e) {
            System.out.println("Error loading BookSection: " + e.getMessage());
        }

        if (bookSection != null) {
            Node finalBookSection = bookSection;
            book_section.setOnMouseClicked(_ -> {
                if (!inner_pane.getChildren().contains(finalBookSection)) {
                    inner_pane.getChildren().clear();
                    inner_pane.getChildren().add(finalBookSection);
                }
            });
        } else {
            System.out.println("Failed to load BookSection");
        }
    }




    @FXML
    private void initialize() {
        initBookTable();
        initializeTextFields();
        initializeComboBoxes();
        initializeImage();
        prepareUserTableView();
        initSwitchMenu();
        onComboBoxEvent();
    }
}

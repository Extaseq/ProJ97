package com.nichga.proj97;

import com.google.zxing.WriterException;
import com.nichga.proj97.Model.Book;
import com.nichga.proj97.Model.DisplayBook;

import com.nichga.proj97.Services.DatabaseService;
import com.nichga.proj97.Services.TokenProvider;
import com.nichga.proj97.Util.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class UserDashboardController extends StageController {
    private Users user;
    protected void setUser(Users user) {
        this.user = user;
    }
    ObservableList<DisplayBook> mainData;
    SortedList<DisplayBook> searchData;
    Set<String> tagList; {tagList = new HashSet<>();}
    Set<String> tagList2; {tagList2 = new HashSet<>();}

    private boolean isTagButtonPressed = false;

    private String userId;

    @FXML
    private TabPane tabpaneLibrary;
    @FXML
    private ToggleButton buttonLibrary, buttonAccount, rattingButton;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane libraryPane, accountPane, ratingPane;
    @FXML
    private Line separator1, separator3, separator5;
    //Library
    @FXML
    private TableColumn<DisplayBook, Image> imagecolumn1, detailcolumn1, imagecolumn12, detailcolumn12;
    @FXML
    private TableView<DisplayBook> tableView1, tableView12;
    @FXML
    private ImageView documentimage1, documentimage11, documentimage3;
    @FXML
    private TextArea namedocument1, descripe1, namedocument11, descripe11, namedocument3, descripe3, detail1, detail2, detail3;
    @FXML
    private TextField search1, search12;
    @FXML
    private TextFlow bookDetailFlow;
    @FXML
    private ToggleButton detailbutton1, detailbutton2, detailbutton3;
    @FXML
    private MenuItem sorttitle1, sortauthor1, sortview1;
    @FXML
    private MenuItem sorttitle12, sortauthor12, sortview12;
    @FXML
    private FlowPane tagsfield1, tagsfield11;
    @FXML
    private HBox currentList, recommendList, finishedList, favouriteAuthorList, mostPopularList;
    @FXML
    private VBox continueReadDoc;
    @FXML
    private Pane pane1, pane2, pane3;

    //Account
    @FXML
    private Button signOut, returnbutton1, ChangePasswordButton;
    @FXML
    private TextField accountName, accountID, accountEmail, accountAddress, accountPhone;
    @FXML
    private ToggleButton ChangeInfoButton, SaveButton;
    @FXML
    private Button borrowbutton1, borrowbutton2;

    //Rating
    @FXML
    private TextField  comment;
    @FXML
    private TextArea otherComment, bookDetail;
    @FXML
    private Button commentButton;
    @FXML
    private ImageView star1, star2, star3, star4, star5, imageRattingBook;

    private ObservableList<DisplayBook> userDocuments;

    private ToggleGroup toggleGroup;
    private final DatabaseService dbs = new DatabaseService();
    private final TokenProvider tp = new TokenProvider();

    private ObservableList<DisplayBook> getCurrentDoc() {
        ObservableList<DisplayBook> doc = FXCollections.observableArrayList();
        int min = Math.min(userDocuments.size(), 5);
        for (int i = 0; i < min; i++) {
            doc.add(userDocuments.get(i));
        }
        return doc;
    }
    private ObservableList<DisplayBook> getRecommendedDoc() {
        return dbs.getBookRepo().getUserRecommendBooks(user);
    }
    private ObservableList<DisplayBook> getFinishedDoc() {
        return dbs.getBookRepo().getUserFinishedBooks(String.valueOf(user.getId()));
    }
    private ObservableList<DisplayBook> getFavouriteAuthorDoc() {
        return FXCollections.observableArrayList();
    }
    private ObservableList<DisplayBook> getMostPopularDoc() {
        return dbs.getBookRepo().getMostPopularBooks();
    }

    public void init() {
        userDocuments = getUserDocuments();

        toggleGroup = new ToggleGroup();
        buttonLibrary.setToggleGroup(toggleGroup);
        buttonAccount.setToggleGroup(toggleGroup);
        rattingButton.setToggleGroup(toggleGroup);
        StrokeLine();
        SetButton();
        LockColumn();
        mainData = getAllDocuments();
        buttonLibrary.setSelected(true);
        tableView1.setItems(mainData);
        tableView12.setItems(userDocuments);
        initLibrary(tagsfield1, imagecolumn1, detailcolumn1, mainData, documentimage1, namedocument1, descripe1, tableView1);
        initLibrary(tagsfield11, imagecolumn12, detailcolumn12, userDocuments, documentimage11, namedocument11, descripe11, tableView12);

        Sort();
        initShelve();
        initContinueReadDoc();
        initAccount();
        pane1.setVisible(false);
        pane2.setVisible(false);
        pane3.setVisible(false);
        showDetail1();
        showDetail2();
        showDetail3();

    }

    public void initAccount() {

        ChangeInfoButton.setVisible(true);
        SaveButton.setVisible(false);
        accountName.setText(user.getName());
        accountName.setEditable(false);
        accountEmail.setText(user.getEmail());
        accountEmail.setEditable(false);
        accountID.setText(String.valueOf(user.getId()));
        accountID.setEditable(false);
        accountAddress.setText(user.getAddress());
        accountAddress.setEditable(false);
        accountPhone.setText(user.getPhone());
        accountPhone.setEditable(false);
        EditInfo();

    }

    public void initContinueReadDoc() {
        ObservableList<DisplayBook> currentDoc = getCurrentDoc();
        if(currentDoc == null || currentDoc.isEmpty()) return;
        DisplayBook doc = getCurrentDoc().getFirst();
        TextField title = (TextField) continueReadDoc.getChildren().get(2);
        title.setText(doc.getTitle());
        ImageView image = (ImageView) continueReadDoc.getChildren().get(1);
        image.setImage(doc.getImage());
        continueReadDoc.setOnMouseClicked(event -> {
            if (buttonAccount.isSelected()) {
                toggleGroup.selectToggle(buttonLibrary);
                stackPane.getChildren().clear();
                stackPane.getChildren().add(libraryPane);
            }

            tabpaneLibrary.getSelectionModel().select(2);
            tableView12.getSelectionModel().select(0);
        });
    }

    public void setContinueReadDoc(DisplayBook doc) {
        TextField title = (TextField) continueReadDoc.getChildren().get(2);
        title.setText(doc.getTitle());
    }

    public void initShelve() {
        borrowbutton1.setVisible(false);
        addDocToShelve(getCurrentDoc(), currentList);
        addDocToShelve(getRecommendedDoc(), recommendList);
        addDocToShelve(getFinishedDoc(), finishedList);
        addDocToShelve(getFavouriteAuthorDoc(), favouriteAuthorList);
        addDocToShelve(getMostPopularDoc(), mostPopularList);

    }

    private String bookIDtoDetail1;
    public void addDocToShelve(ObservableList<DisplayBook> docList, HBox hBox) {

        for (int i = 0; i < docList.size(); i++) {
            DisplayBook doc = docList.get(i);
            VBox vBox = (VBox) hBox.getChildren().get(i);
            ImageView image = (ImageView) vBox.getChildren().get(0);
            image.setImage(doc.getImage());
            TextField title = (TextField) vBox.getChildren().get(1);
            title.setText(docList.get(i).getTitle());
            vBox.setOnMouseClicked(event -> {
                bookIDtoDetail1 = doc.getBookId();
                detail1.setVisible(false);
                descripe3.setVisible(true);
                pane1.setVisible(true);
                borrowbutton1.setVisible(true);
                documentimage3.setImage(image.getImage());
                namedocument3.setText(title.getText());
                descripe3.setText("Author: " + doc.getAuthor() + "\nPublisher: " + doc.getPublisher() + "\nPublished year: " + doc.getPublishedYear()
                        + "\n" + doc.getGenre()+ "\nAvailable: " + doc.getAvailable());
                borrowbutton1.setOnMouseClicked(e -> {
                    borrowDocument(doc);
                });
            });
        }

    }

    //Sua mac dinh anh
    public void initLibrary(FlowPane tagsfield, TableColumn<DisplayBook, Image> imagecolumn,
                            TableColumn<DisplayBook, Image> detailcolumn, ObservableList<DisplayBook> data,
                            ImageView documentImage, TextArea namedocument, TextArea descripe, TableView<DisplayBook> tableView) {
        tagsfield.setHgap(10);
        tagsfield.setVgap(10);

        imagecolumn.setCellValueFactory(new PropertyValueFactory<>("image")); // "image" là thuộc tính kiểu Image trong model

        imagecolumn.setCellFactory(_ -> new TableCell<>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitWidth(80);
                    imageView.setFitHeight(119);
                    imageView.setPreserveRatio(false);
                    setGraphic(imageView);
                    setStyle("-fx-alignment: CENTER;");
                }
            }

        });


        detailcolumn.setCellFactory(_ -> new TableCell<>() {
            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    DisplayBook doc = getTableRow().getItem();
                    if (doc != null) {
                        Text title = new Text(doc.getTitle() + "\n");
                        title.setFont(Font.font("System", FontWeight.BOLD, 16));
                        Text detail = new Text("Author: " + doc.getAuthor() + "\nPublisher: " + doc.getPublisher() + "\nPublished year: " + doc.getPublishedYear()
                                + "\n" + doc.getGenre()+ "\nAvailable: " + doc.getAvailable());
                        TextFlow flow = new TextFlow(title, detail);
                        flow.setPrefHeight(100);
                        setPrefHeight(100);
                        setGraphic(flow);
                        setText(null);

                    } else {
                        setText(null);
                        setGraphic(null);
                    }
                }
            }
        });

        tableView.setItems(data);
        if (tableView == tableView1)
        {
            search(data, tableView, search1);
        } else {
            search(data, tableView, search12);
        }
        tableView.getSelectionModel().selectedItemProperty().addListener((_, oldValue, newValue) -> {
            detail2.setVisible(false);
            detail3.setVisible(false);
            descripe1.setVisible(true);
            descripe11.setVisible(true);
            if (tableView == tableView1) {
                pane2.setVisible(true);
            } else if (tableView == tableView12) {
                pane3.setVisible(true);
            }
            if (newValue != null && !newValue.equals(oldValue)) {
                documentImage.setImage(newValue.getImage());
                namedocument.setText(newValue.getTitle());
                descripe.setText("Author: " + newValue.getAuthor() + "\nPublisher: " + newValue.getPublisher() + "\nPublished year: " + newValue.getPublishedYear()
                        + "\n" + newValue.getGenre()+ "\nAvailable: " + newValue.getAvailable());

                if (!isTagButtonPressed) {
                    displayTags(newValue, tagsfield, tableView);
                }

                isTagButtonPressed = false;
            }
        });

    }

    private void LockColumn() {
        imagecolumn1.setReorderable(false);
        detailcolumn1.setReorderable(false);
        imagecolumn12.setReorderable(false);
        detailcolumn12.setReorderable(false);
    }

    private void StrokeLine() {
        separator1.getStrokeDashArray().addAll(7d, 7d);
        separator3.getStrokeDashArray().addAll(7d, 7d);
        separator5.getStrokeDashArray().addAll(7d, 7d);
    }

    private void SetButton() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(libraryPane);
        buttonLibrary.setOnAction(event -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(libraryPane);
        });
        buttonAccount.setOnAction(event -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(accountPane);
        });

        rattingButton.setOnAction(event -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(ratingPane);

            DisplayBook book = tableView12.getSelectionModel().getSelectedItem();

            imageRattingBook.setImage(book.getImage());
            imageRattingBook.setPreserveRatio(false);
            Text title = new Text(book.getTitle() + "\n");
            title.setFont(Font.font("System", FontWeight.BOLD, 16));
            Text detail = new Text("Author: " + book.getAuthor() + "\nPublisher: " + book.getPublisher() + "\nPublished year: " + book.getPublishedYear()
                    + "\n" + book.getGenre()+ "\nAvailable: " + book.getAvailable());
            bookDetailFlow.getChildren().clear();
            bookDetailFlow.getChildren().addAll(title, detail);


            String usersComment = dbs.getBorrowRepo().getAllComments(book.getBookId());
            star1.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
            star2.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
            star3.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
            star4.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
            star5.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));

            AtomicInteger rating = new AtomicInteger(5);
            star1.setOnMouseClicked(e -> {
                star1.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star2.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                star3.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                star4.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                star5.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                rating.set(1);
            });

            star2.setOnMouseClicked(e -> {
                star1.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star2.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star3.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                star4.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                star5.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                rating.set(2);
            });

            star3.setOnMouseClicked(e -> {
                star1.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star2.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star3.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star4.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                star5.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                rating.set(3);
            });

            star4.setOnMouseClicked(e -> {
                star1.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star2.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star3.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star4.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star5.setImage(new Image(getClass().getResource("Star.png").toExternalForm()));
                rating.set(4);
            });

            star5.setOnMouseClicked(e -> {
                star1.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star2.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star3.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star4.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                star5.setImage(new Image(getClass().getResource("Star2.png").toExternalForm()));
                rating.set(5);
            });

            if (usersComment.length() > 1) {
                otherComment.setText(usersComment);
            } else {
                otherComment.setText("This book have no comments");
            }
            commentButton.setOnMouseClicked(e -> {
                String str = comment.getText();
                if (dbs.getBorrowRepo().addNewComment(str, String.valueOf(user.getId()), book.getBookId())){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("You comment have been saved");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("You did not borrow this book in library!");
                    alert.showAndWait();
                }
                if (dbs.getBorrowRepo().addNewRating(String.valueOf(rating), String.valueOf(user.getId()), book.getBookId()));
            });
        });
    }

    private ObservableList<DisplayBook> getAllDocuments() {
        ObservableList<DisplayBook> books = dbs.getBookRepo().getAllBook();
        return books;
    }

    private ObservableList<DisplayBook> getUserDocuments() {
        return dbs.getBookRepo().getUserBooks(String.valueOf(user.getId()));
    }

    private void sortTable(Comparator<DisplayBook> comparator, TableView<DisplayBook> table) {
        // Tạo danh sách sắp xếp

        SortedList<DisplayBook> sortedData = new SortedList<>(table.getItems(), comparator);
        table.setItems(sortedData);
    }
    private void Sort() {
        sorttitle1.setOnAction(_ -> sortTable((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()), tableView1));
        sortauthor1.setOnAction(_ -> sortTable((o1, o2) -> o1.getAuthor().compareToIgnoreCase(o2.getAuthor()), tableView1));
        sortauthor12.setOnAction(_ -> sortTable((o1, o2) -> o1.getAuthor().compareToIgnoreCase(o2.getAuthor()), tableView12));
        sorttitle12.setOnAction(_ -> sortTable((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()), tableView12));
        //sortViewDesc.setOnAction(event -> sortTable((o1, o2) -> Integer.compare(o2.getViewCount(), o1.getViewCount())), tableView1);
    }

    private void displayTags(DisplayBook doc, FlowPane tagsfield, TableView<DisplayBook> table) {

        tagsfield.getChildren().clear();
        if (table == tableView1) {
            tagList.clear();
        } else {
            tagList2.clear();
        }

        isTagButtonPressed = false;
        Set<String> tagSet = doc.getTags();
        for(String tags : tagSet) {
            Button tag = new Button(tags);
            tag.getStyleClass().setAll("default-button");
            tag.setMaxWidth(1000);
            tag.setOnAction(_ -> {
                isTagButtonPressed = true;
                if (tag.getStyleClass().contains("highlighted-button")) {

                    tag.getStyleClass().setAll("default-button");

                    if (table == tableView1) {
                        tagList.remove(tags);
                        showDocumentsWithTag(table, mainData);
                    } else {
                        tagList2.remove(tags);
                        showDocumentsWithTag(table, userDocuments);
                    }
                } else {

                    tag.getStyleClass().setAll("highlighted-button");
                    if (table == tableView1) {
                        showDocumentsWithTag(tags, table, mainData);
                    } else {
                        showDocumentsWithTag(tags, table, userDocuments);
                    }

                }
            });
            tagsfield.getChildren().add(tag);
        }

    }

    private void showDocumentsWithTag(TableView<DisplayBook> tableView, ObservableList<DisplayBook> data) {
        ObservableList<DisplayBook> filteredDocuments = FXCollections.observableArrayList();
        Set<String> tags;
        if (tableView == tableView1) {
            tags = tagList;
        } else {
            tags = tagList2;
        }

        for (DisplayBook document : data) {
            if (tags.isEmpty()) {
                filteredDocuments.addAll(data);
                break;
            }
            boolean hasAllTags = true;
            for (String tag : tags) {
                if (!document.hasTag(tag)) {
                    hasAllTags = false;
                    break;
                }
            }
            if (hasAllTags) {
                filteredDocuments.add(document);
            }
        }
        if (tableView == tableView1)
        {
            search(filteredDocuments, tableView, search1);
        } else {
            search(filteredDocuments, tableView, search12);
        }
    }
    private void showDocumentsWithTag(String tag, TableView<DisplayBook> tableView, ObservableList<DisplayBook> data) {
        if (tableView == tableView1)
            tagList.add(tag);
        else {
            tagList2.add(tag);
        }
        showDocumentsWithTag(tableView, data);
    }

    public void search(ObservableList<DisplayBook> data, TableView<DisplayBook> table, TextField search) {

        FilteredList<DisplayBook> filteredData = new FilteredList<>(data, p -> true);
        search.textProperty().addListener((_, _, _) -> {
            applyFilter(filteredData, search.getText());
        });

        applyFilter(filteredData, search.getText());

        SortedList<DisplayBook> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.refresh();
        table.setItems(sortedData);

    }
    private void applyFilter(FilteredList<DisplayBook> filteredData, String filterText) {
        filteredData.setPredicate(item -> {
            if (filterText == null || filterText.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filterText.toLowerCase();
            return item.getTitle().toLowerCase().contains(lowerCaseFilter) ||
                    item.getAuthor().toLowerCase().contains(lowerCaseFilter);
        });
    }

    //Account
    public void signOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to logout?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            goToNextStage("/com/nichga/proj97/LoginRegister.fxml", signOut, null);
        }

    }

    private void EditInfo() {
        ToggleGroup group = new ToggleGroup();
        ChangeInfoButton.setToggleGroup(group);
        SaveButton.setToggleGroup(group);

        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(ChangeInfoButton)) {

                SaveButton.setVisible(true);
                ChangeInfoButton.setVisible(false);

                accountName.setEditable(true);
                accountName.setStyle("-fx-background-color: white;" + "-fx-border-color: lightgray;");

                accountEmail.setEditable(true);
                accountEmail.setStyle("-fx-background-color: white;" + "-fx-border-color: lightgray;");

                accountAddress.setEditable(true);
                accountAddress.setStyle("-fx-background-color: white;" + "-fx-border-color: lightgray;");

                accountPhone.setEditable(true);
                accountPhone.setStyle("-fx-background-color: white;" + "-fx-border-color: lightgray;");

            } else if (newValue.equals(SaveButton)) {
                SaveButton.setVisible(false);
                ChangeInfoButton.setVisible(true);
                accountName.setEditable(false);
                accountName.setStyle("-fx-background-color: transparent;" + "-fx-border-color: transparent;" );

                accountEmail.setEditable(false);
                accountEmail.setStyle("-fx-background-color: transparent;" + "-fx-border-color: transparent;");

                accountAddress.setEditable(false);
                accountAddress.setStyle("-fx-background-color: transparent;" + "-fx-border-color: transparent;");

                accountPhone.setEditable(false);
                accountPhone.setStyle("-fx-background-color: transparent;" + "-fx-border-color: transparent;");

                dbs.getMemberRepo().updateInfo(user.getId(), accountName.getText(), accountAddress.getText(), accountEmail.getText(), accountPhone.getText());
            }
        });
    }

    @FXML
    public void changePassword() throws IOException {
        openNewStage("/com/nichga/proj97/ChangePassword.fxml", ChangePasswordButton, user);
    }

    public void borrowDocument(DisplayBook doc) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to borrow?");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            BufferedImage qr = user.borrow(doc.getBookId());
            if (qr != null) {
                userDocuments.add(doc);
                tabpaneLibrary.getSelectionModel().select(2);
                tableView12.refresh();
                tableView12.getSelectionModel().select(doc);
                tableView12.scrollTo(doc);
                showQr(qr);
            }
        }
    }

    @FXML
    public void borrowDocFromAllBooks() {
        DisplayBook doc = tableView1.getSelectionModel().getSelectedItem();
        if (doc != null) {
            borrowDocument(doc);
        } else {
            System.out.println("No doc selected");
        }
    }

    public void showQr(BufferedImage qr) {
        Image fxImage = SwingFXUtils.toFXImage(qr, null);
        ImageView imageView = new ImageView(fxImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 500, 500);
        Stage stage = new Stage();
        stage.setTitle("QR Code Display");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void showQr() throws WriterException {
        DisplayBook doc = tableView12.getSelectionModel().getSelectedItem();
        if (doc == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No doc selected");
            alert.showAndWait();
            return;
        }
        String token = dbs.getTokenRepo().getToken(String.valueOf(user.getId()), doc.getBookId());
        if (token == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You borrowed this book!");
            alert.showAndWait();
            return;
        }
        BufferedImage qr = TokenProvider.generateQRCode(token);
        Image fxImage = SwingFXUtils.toFXImage(qr, null);
        ImageView imageView = new ImageView(fxImage);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root, 500, 500);
        Stage stage = new Stage();
        stage.setTitle("QR Code Display");
        stage.setScene(scene);
        stage.show();
    }
    private String details(String bookId) {
        try {
            String apiKey = "AIzaSyA5B1G2E0gdk-1vag_sJTrsPKOlh7O2y_Y";
            String urlString = "https://www.googleapis.com/books/v1/volumes/" + bookId + "?key=" + apiKey;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine).append("\n");
                }
                in.close();
                String json = response.toString();
                Book book = JsonParser.ParseSingleBook(json);
                if (book != null) {
                    if (book.getVolumeInfo().getDescription() != null && !book.getVolumeInfo().getDescription().isEmpty()) {
                        return book.getVolumeInfo().getDescription();
                    } else {
                        System.out.println("No summary available for this book.");
                    }
                } else {
                    System.out.println("Book not found.");
                }
            } else {
                System.out.println("Error: HTTP " + responseCode);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    public void showDetail1() {
        detailbutton1.setOnAction(event -> {
            if (detailbutton1.isSelected()) {
                if (bookIDtoDetail1 != null) {
                    detail1.setText(details(bookIDtoDetail1));
                    descripe3.setVisible(false);
                    detail1.setVisible(true);
                } else {
                    System.out.println("No book selected");
                }
            } else {
                detail1.setVisible(false);
                descripe3.setVisible(true);
            }
        });
    }
    public void showDetail2() {
        detailbutton2.setOnAction(event -> {
            if (detailbutton2.isSelected()) {
                DisplayBook book = tableView1.getSelectionModel().getSelectedItem();
                if (book != null) {
                    detail2.setText(details(book.getBookId()));
                    descripe1.setVisible(false);
                    detail2.setVisible(true);
                } else {
                    System.out.println("No book selected");
                }
            } else {
                detail2.setVisible(false);
                descripe1.setVisible(true);
            }
        });
    }
    public void showDetail3() {
        detailbutton3.setOnAction(event -> {
            if (detailbutton3.isSelected()) {
                DisplayBook book = tableView12.getSelectionModel().getSelectedItem();
                if (book != null) {
                    detail3.setText(details(book.getBookId()));
                    descripe11.setVisible(false);
                    detail3.setVisible(true);
                } else {
                    System.out.println("No book selected");
                }
            } else {
                detail3.setVisible(false);
                descripe11.setVisible(true);
            }
        });
    }


}

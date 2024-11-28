package com.nichga.proj97;

import com.google.zxing.WriterException;
import com.nichga.proj97.Model.DisplayBook;
import com.nichga.proj97.Model.Book;

import com.nichga.proj97.Services.DatabaseService;
import com.nichga.proj97.Services.TokenProvider;
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
import java.io.IOException;
import java.util.*;

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
    private ToggleButton buttonLibrary, buttonAccount;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane libraryPane, accountPane;
    @FXML
    private Line separator1, separator3;
    //Library
    @FXML
    private TableColumn<DisplayBook, Image> imagecolumn1, detailcolumn1, imagecolumn12, detailcolumn12;
    @FXML
    private TableView<DisplayBook> tableView1, tableView12;
    @FXML
    private ImageView documentimage1, documentimage11, documentimage3;
    @FXML
    private TextArea namedocument1, descripe1, namedocument11, descripe11, namedocument3, descripe3;
    @FXML
    private TextField search1, search12;
    @FXML
    private MenuButton menuButton1, menuButton12;
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
    //Account
    @FXML
    private Button signOut, returnbutton1, ChangePasswordButton;
    @FXML
    private TextField accountName, accountID, accountEmail, accountAddress, accountPhone;
    @FXML
    private ToggleButton ChangeInfoButton, SaveButton;
    @FXML
    private Button borrowbutton1, borrowbutton2;

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
        return FXCollections.observableArrayList();
    }
    private ObservableList<DisplayBook> getFinishedDoc() {
        return dbs.getBookRepo().getUserFinishedBooks(String.valueOf(user.getId()));
    }
    private ObservableList<DisplayBook> getFavouriteAuthorDoc() {
        return FXCollections.observableArrayList();
    }
    private ObservableList<DisplayBook> getMostPopularDoc() {
        return FXCollections.observableArrayList();
    }

    public void init() {
        userDocuments = getUserDocuments();

        toggleGroup = new ToggleGroup();
        buttonLibrary.setToggleGroup(toggleGroup);
        buttonAccount.setToggleGroup(toggleGroup);
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

    }

    @FXML
    public void returnDoc() {
        DisplayBook doc = tableView12.getSelectionModel().getSelectedItem();
        if (doc != null) {
            System.out.println("remove " + doc.getTitle());
            userDocuments.remove(doc);
            tableView12.refresh();
            tableView12.setItems(userDocuments);
            setContinueReadDoc(userDocuments.get(0));
        }

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

    public void addDocToShelve(ObservableList<DisplayBook> docList, HBox hBox) {

        for (int i = 0; i < docList.size(); i++) {
            DisplayBook doc = docList.get(i);
            VBox vBox = (VBox) hBox.getChildren().get(i);
            ImageView image = (ImageView) vBox.getChildren().get(0);
            TextField title = (TextField) vBox.getChildren().get(1);
            title.setText(docList.get(i).getTitle());
            vBox.setOnMouseClicked(event -> {
                borrowbutton1.setVisible(true);
                documentimage3.setImage(image.getImage());
                namedocument3.setText(title.getText());
                descripe3.setText("Author: " + doc.getAuthor() + "\nPublisher: " + doc.getPublisher() + "\nPublished year: " + doc.getPublishedYear()
                        + "\n" + doc.getGenre()+ "\nAvailable: " + doc.getCopiesAvailable());
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
                    imageView.setFitHeight(120);
                    imageView.setPreserveRatio(true);
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
                                + "\n" + doc.getGenre()+ "\nAvailable: " + doc.getCopiesAvailable());
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

            if (newValue != null && !newValue.equals(oldValue)) {
                documentImage.setImage(newValue.getImage());
                namedocument.setText(newValue.getTitle());
                descripe.setText("Author: " + newValue.getAuthor() + "\nPublisher: " + newValue.getPublisher() + "\nPublished year: " + newValue.getPublishedYear()
                        + "\n" + newValue.getGenre()+ "\nAvailable: " + newValue.getCopiesAvailable());

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
        List<String> tagList = doc.getTags();
        for(String tags : tagList) {
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
                accountEmail.setEditable(true);
                accountAddress.setEditable(true);
                accountPhone.setEditable(true);

            } else if (newValue.equals(SaveButton)) {
                SaveButton.setVisible(false);
                ChangeInfoButton.setVisible(true);
                accountName.setEditable(false);
                accountEmail.setEditable(false);
                accountAddress.setEditable(false);
                accountPhone.setEditable(false);

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
        BufferedImage qr = tp.generateQRCode(token);
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

}

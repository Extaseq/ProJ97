package com.nichga.proj97;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.*;


public class UserDashboard2Controller extends StageController {
    Users user;
    protected void setUser(Users user) {
        this.user = user;
    }
    ObservableList<Documents> mainData;
    SortedList<Documents> searchData;
    Set<String> tagList; {tagList = new HashSet<>();}
    //SortedList<Documents> sortedData;
    private boolean isTagButtonPressed = false;

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
    private TableColumn<Documents, String> imagecolumn1, detailcolumn1;
    @FXML
    private TableView<Documents> tableView1, tableView11;
    @FXML
    private ImageView documentimage1;
    @FXML
    private TextArea namedocument1, descripe1;
    @FXML
    private TextField search1;
    @FXML
    private MenuButton menuButton1;

    @FXML
    private MenuItem sorttitle1, sortauthor1, sortview1;

    @FXML
    private FlowPane tagsfield1;


    public void initialize() {
        initLibrary();
    }

    public void initLibrary() {
        tagsfield1.setHgap(10);
        tagsfield1.setVgap(10);

        buttonLibrary.setSelected(true);

        StrokeLine();
        SetButton();
        imagecolumn1.setCellValueFactory(new PropertyValueFactory<>("imageLink"));
        imagecolumn1.setCellFactory(column -> new TableCell<Documents, String>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String imageLink, boolean empty) {
                super.updateItem(imageLink, empty);
                if (empty || imageLink == null) {
                    setGraphic(null);
                } else {
                    try {
                        imageView.setImage(new Image(getClass().getResourceAsStream(imageLink)));
                        imageView.setFitWidth(80);
                        imageView.setFitHeight(120);
                        imageView.setPreserveRatio(false);
                        setGraphic(imageView);
                        setStyle("-fx-alignment: CENTER;");
                    } catch (Exception e) {
                        setGraphic(null);
                        System.err.println("Error loading image: " + imageLink);
                    }
                }
            }
        });

        detailcolumn1.setCellFactory(column -> new TableCell<Documents, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Documents doc = getTableRow().getItem();
                    if (doc != null) {
//                        setText(doc.getTitle() + "\nAuthor: " + doc.getAuthor() + "\nType: " + doc.getType()
//                                + "\n" + doc.getTagsString() + "\nAvailable: " + "\nView: ");
                        Text title = new Text(doc.getTitle() + "\n");
                        title.setFont(Font.font("System", FontWeight.BOLD, 16));
                        Text detail = new Text("Author: " + doc.getAuthor() + "\nType: " + doc.getType() + "\n" + doc.getTagsString() + "\nAvailable: " + "\nView: ");
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
        mainData = getAllDocuments();
        tableView1.setItems(mainData);
        search(mainData, tableView1);
        tableView1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && !newValue.equals(oldValue)) {
                documentimage1.setImage(new Image(getClass().getResourceAsStream(newValue.getImageLink())));
                namedocument1.setText(newValue.getTitle());
                descripe1.setText("Author: " + newValue.getAuthor() + "\nDescripe: " + "\nType: " + newValue.getType()
                        + "\n" + newValue.getTagsString()+ "\nAvailable: " + "\nView: ");

                if (isTagButtonPressed == false) {
                    displayTags(newValue);
                }
                isTagButtonPressed = false;
            }
        });

        Sort();
    }

    private void LockColumn() {
        imagecolumn1.setReorderable(false);
        detailcolumn1.setReorderable(false);
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

    private ObservableList<Documents> getAllDocuments() {
        // Khởi tạo danh sách tài liệu
        return FXCollections.observableArrayList(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50)
        );
    }

    private ObservableList<Documents> getUserDocuments() {
        user.addUserDocuments(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100)
        );
        user.addUserDocuments(
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200)
        );
        user.addUserDocuments(
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150)
        );
        user.addUserDocuments(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100)
        );
        user.addUserDocuments(
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU", "Classic"}, 4, 150)
        );
        user.addUserDocuments(
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50)
        );
        return FXCollections.observableArrayList(user.getUserDocuments());

    }


    private void sortTable(Comparator<Documents> comparator, TableView<Documents> table) {
        // Tạo danh sách sắp xếp

        SortedList<Documents> sortedData = new SortedList<>(table.getItems(), comparator);
        table.setItems(sortedData);
    }
    private void Sort() {
        sorttitle1.setOnAction(event -> sortTable((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()), tableView1));
        sortauthor1.setOnAction(event -> sortTable((o1, o2) -> o1.getAuthor().compareToIgnoreCase(o2.getAuthor()), tableView1));
        //sortViewDesc.setOnAction(event -> sortTable((o1, o2) -> Integer.compare(o2.getViewCount(), o1.getViewCount())), tableView1);
    }

    private void displayTags(Documents doc) {
        System.out.println("re display");
        tagsfield1.getChildren().clear();
        tagList.clear();

        isTagButtonPressed = false;

        for(String tags : doc.tag) {
            Button tag = new Button(tags);
            tag.getStyleClass().setAll("default-button");
            tag.setMaxWidth(1000);
            tag.setOnAction(actionEvent -> {
                isTagButtonPressed = true;
                if (tag.getStyleClass().contains("highlighted-button")) {

                    tag.getStyleClass().setAll("default-button");
                    tagList.remove(tags);
                    showDocumentsWithTag();
                } else {

                    tag.getStyleClass().setAll("highlighted-button");
                    showDocumentsWithTag(tags);
                }
            });
            tagsfield1.getChildren().add(tag);

        }



    }

    private void showDocumentsWithTag() {
        ObservableList<Documents> filteredDocuments = FXCollections.observableArrayList();

        for (Documents document : mainData) {
            boolean hasAllTags = true;
            for (String tag : tagList) {
                if (!document.hasTag(tag)) {
                    hasAllTags = false;
                    break;
                }
            }
            if (hasAllTags) {
                filteredDocuments.add(document);
            }
        }
        search(filteredDocuments, tableView1);
    }
    private void showDocumentsWithTag(String tag) {

        tagList.add(tag);
        showDocumentsWithTag();
    }

    public void search(ObservableList<Documents> data, TableView<Documents> table) {

        FilteredList<Documents> filteredData = new FilteredList<>(data, p -> true);
        search1.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter(filteredData, search1.getText());
        });

        applyFilter(filteredData, search1.getText());

        SortedList<Documents> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView1.comparatorProperty());
        table.refresh();
        table.setItems(sortedData);

    }
    private void applyFilter(FilteredList<Documents> filteredData, String filterText) {
        filteredData.setPredicate(item -> {
            if (filterText == null || filterText.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filterText.toLowerCase();
            return item.getTitle().toLowerCase().contains(lowerCaseFilter) ||
                    item.getAuthor().toLowerCase().contains(lowerCaseFilter);
        });
    }


}

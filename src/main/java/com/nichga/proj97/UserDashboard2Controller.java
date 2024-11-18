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

import javax.print.Doc;
import java.util.*;

public class UserDashboard2Controller extends StageController {
    Users user;
    protected void setUser(Users user) {
        this.user = user;
    }
    ObservableList<Documents> mainData;
    SortedList<Documents> searchData;
    Set<String> tagList; {tagList = new HashSet<>();}
    Set<String> tagList2; {tagList2 = new HashSet<>();}
    //SortedList<Documents> sortedData;
    private boolean isTagButtonPressed = false;

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
    private TableColumn<Documents, String> imagecolumn1, detailcolumn1, imagecolumn12, detailcolumn12;
    @FXML
    private TableView<Documents> tableView1, tableView12;
    @FXML
    private ImageView documentimage1, documentimage11;
    @FXML
    private TextArea namedocument1, descripe1, namedocument11, descripe11;
    @FXML
    private TextField search1, search12;
    @FXML
    private MenuButton menuButton1;
    private MenuButton menuButton12;

    @FXML
    private MenuItem sorttitle1, sortauthor1, sortview1;
    @FXML
    private MenuItem sorttitle12, sortauthor12, sortview12;

    @FXML
    private FlowPane tagsfield1, tagsfield11;

    private ObservableList<Documents> userDocuments; {userDocuments = getUserDocuments();}


    public void initialize() {
        StrokeLine();
        SetButton();
        mainData = getAllDocuments();
        buttonLibrary.setSelected(true);
        tableView1.setItems(mainData);
        tableView12.setItems(getUserDocuments());
        initLibrary(tagsfield1, imagecolumn1, detailcolumn1, mainData, documentimage1, namedocument1, descripe1, tableView1);
        initLibrary(tagsfield11, imagecolumn12, detailcolumn12, getUserDocuments(), documentimage11, namedocument11, descripe11, tableView12);
        Sort();

    }

    public void initLibrary(FlowPane tagsfield, TableColumn<Documents, String> imagecolumn,
                            TableColumn<Documents, String> detailcolumn, ObservableList<Documents> data,
                            ImageView documentImage, TextArea namedocument, TextArea descripe, TableView<Documents> tableView) {
        tagsfield.setHgap(10);
        tagsfield.setVgap(10);

        imagecolumn.setCellValueFactory(new PropertyValueFactory<>("imageLink"));
        imagecolumn.setCellFactory(column -> new TableCell<Documents, String>() {
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

        detailcolumn.setCellFactory(column -> new TableCell<Documents, String>() {
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

        tableView.setItems(data);
        if (tableView == tableView1)
        {
            search(data, tableView, search1);
        } else {
            search(data, tableView, search12);
        }
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue != null && !newValue.equals(oldValue)) {
                documentImage.setImage(new Image(getClass().getResourceAsStream(newValue.getImageLink())));
                namedocument.setText(newValue.getTitle());
                descripe.setText("Author: " + newValue.getAuthor() + "\nDescripe: " + "\nType: " + newValue.getType()
                        + "\n" + newValue.getTagsString()+ "\nAvailable: " + "\nView: ");

                if (isTagButtonPressed == false) {
                    displayTags(newValue, tagsfield, tableView);
                }
                isTagButtonPressed = false;
            }
        });

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


    private void sortTable(Comparator<Documents> comparator, TableView<Documents> table) {
        // Tạo danh sách sắp xếp

        SortedList<Documents> sortedData = new SortedList<>(table.getItems(), comparator);
        table.setItems(sortedData);
    }
    private void Sort() {
        sorttitle1.setOnAction(event -> sortTable((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()), tableView1));
        sortauthor1.setOnAction(event -> sortTable((o1, o2) -> o1.getAuthor().compareToIgnoreCase(o2.getAuthor()), tableView1));
        sortauthor12.setOnAction(event -> sortTable((o1, o2) -> o1.getAuthor().compareToIgnoreCase(o2.getAuthor()), tableView12));
        sorttitle12.setOnAction(event -> sortTable((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()), tableView12));
        //sortViewDesc.setOnAction(event -> sortTable((o1, o2) -> Integer.compare(o2.getViewCount(), o1.getViewCount())), tableView1);
    }

    private void displayTags(Documents doc, FlowPane tagsfield, TableView<Documents> table) {

        tagsfield.getChildren().clear();
        if (table == tableView1) {
            tagList.clear();
            System.out.println("re display table 1");
        } else {
            tagList2.clear();
            System.out.println("re display table 2");
        }

        isTagButtonPressed = false;

        for(String tags : doc.tag) {
            Button tag = new Button(tags);
            tag.getStyleClass().setAll("default-button");
            tag.setMaxWidth(1000);
            tag.setOnAction(actionEvent -> {
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

    private void showDocumentsWithTag(TableView<Documents> tableView, ObservableList<Documents> data) {
        ObservableList<Documents> filteredDocuments = FXCollections.observableArrayList();
        Set<String> tags = new HashSet<>();
        if (tableView == tableView1) {
            tags = tagList;
        } else {
            tags = tagList2;
        }
        for (Documents document : data) {
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
    private void showDocumentsWithTag(String tag, TableView<Documents> tableView, ObservableList<Documents> data) {
        if (tableView == tableView1)
            tagList.add(tag);
        else {
            tagList2.add(tag);
        }
        showDocumentsWithTag(tableView, data);
    }

    public void search(ObservableList<Documents> data, TableView<Documents> table, TextField search) {

        FilteredList<Documents> filteredData = new FilteredList<>(data, p -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter(filteredData, search.getText());
        });

        applyFilter(filteredData, search.getText());

        SortedList<Documents> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
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
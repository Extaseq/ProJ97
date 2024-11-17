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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.print.Doc;
import java.util.Comparator;


public class UserDashboard2Controller extends StageController {
    Users user;
    protected void setUser(Users user) {
        this.user = user;
    }
    ObservableList<Documents> mainData;
    SortedList<Documents> searchData;
    //SortedList<Documents> sortedData;

    @FXML
    private ToggleButton buttonLibrary, buttonRecommend, buttonMyDocument, buttonAccount;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane libraryPane, recommendPane, myDocumentPane,accountPane;
    @FXML
    private Line separator1, separator3, separator5, separator7;
    //Library
    @FXML
    private TableColumn<Documents, String> imagecolumn1, detailcolumn1;
    @FXML
    private TableView<Documents> tableView1;
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

    public void initialize() {
        buttonLibrary.setSelected(true);
        LockColumn();
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
        mainData = getDocuments();
        searchData = Search();
        tableView1.setItems(searchData);
        tableView1.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                documentimage1.setImage(new Image(getClass().getResourceAsStream(newValue.getImageLink())));
                namedocument1.setText(newValue.getTitle());
                descripe1.setText("Author: " + newValue.getAuthor() + "\nDescripe: " + "\nType: " + newValue.getType()
                        + "\n" + newValue.getTagsString()+ "\nAvailable: " + "\nView: ");
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
        separator5.getStrokeDashArray().addAll(7d, 7d);
        separator7.getStrokeDashArray().addAll(7d, 7d);
    }

    private void SetButton() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(libraryPane);
        buttonLibrary.setOnAction(event -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(libraryPane);
        });
        buttonRecommend.setOnAction(event -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(recommendPane);
        });
        buttonMyDocument.setOnAction(event -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(myDocumentPane);
        });
        buttonAccount.setOnAction(event -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(accountPane);
        });
    }

    private ObservableList<Documents> getDocuments() {
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

    private SortedList<Documents> Search() {
        FilteredList<Documents> filteredData = new FilteredList<>(mainData, p -> true);
        search1.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return item.getTitle().toLowerCase().contains(lowerCaseFilter) ||
                        item.getAuthor().toLowerCase().contains(lowerCaseFilter);
            });
        });
        SortedList<Documents> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView1.comparatorProperty());
        return sortedData;
    }

    private void sortTable(Comparator<Documents> comparator, TableView tab) {
        // Tạo danh sách sắp xếp
        SortedList<Documents> sortedData = new SortedList<>(searchData, comparator);
        tab.setItems(sortedData);
    }
    private void Sort() {
        sorttitle1.setOnAction(event -> sortTable((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()), tableView1));
        sortauthor1.setOnAction(event -> sortTable((o1, o2) -> o1.getAuthor().compareToIgnoreCase(o2.getAuthor()), tableView1));
        //sortViewDesc.setOnAction(event -> sortTable((o1, o2) -> Integer.compare(o2.getViewCount(), o1.getViewCount())), tableView1);
    }
}

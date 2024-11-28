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
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.*;

public class UserDashboardController extends StageController {
    Users user;
    protected void setUser(Users user) {
        this.user = user;
    }
    ObservableList<Documents> mainData;
    Set<String> tagList; {tagList = new HashSet<>();}
    Set<String> tagList2; {tagList2 = new HashSet<>();}
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
    private ImageView documentimage1, documentimage11, documentimage3;
    @FXML
    private TextArea namedocument1, descripe1, namedocument11, descripe11, namedocument3, descripe3;
    @FXML
    private TextField search1, search12;
    @FXML
    private Button movePrev, moveNext;
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
    private Button signOut, returnbutton1;
    @FXML
    private TextField accountName, accountID, accountEmail, accountAddress;
    @FXML
    private ToggleButton ChangeInfoButton, SaveButton;

    private ObservableList<Documents> userDocuments; {userDocuments = getUserDocuments();}

    private ToggleGroup toggleGroup;

    private ObservableList<Documents> getCurrentDoc() {
        return FXCollections.observableArrayList(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100)
        );
    }
    private ObservableList<Documents> getRecommendedDoc() {
        return FXCollections.observableArrayList(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100)
        );
    }
    private ObservableList<Documents> getFinishedDoc() {
        return FXCollections.observableArrayList(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100)
        );
    }
    private ObservableList<Documents> getFavouriteAuthorDoc() {
        return FXCollections.observableArrayList(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100)
        );
    }
    private ObservableList<Documents> getMostPopularDoc() {
        return FXCollections.observableArrayList(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100)
        );
    }

    public void initialize() {
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

        //Account
        initAccount();
        ChangeInfoButton.setOnAction(event -> EditInfo());
        SaveButton.setOnAction(event -> EditInfo());

    }

    @FXML
    public void returnDoc() {
        Documents doc = tableView12.getSelectionModel().getSelectedItem();
        if (doc != null) {
            System.out.println("remove " + doc.getTitle());
            userDocuments.remove(doc);
            tableView12.refresh();
            tableView12.setItems(userDocuments);
            setContinueReadDoc(userDocuments.get(0));
        }

    }

    public void initContinueReadDoc() {

        Documents doc = getCurrentDoc().get(0);
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

    public void setContinueReadDoc(Documents doc) {
        TextField title = (TextField) continueReadDoc.getChildren().get(2);
        title.setText(doc.getTitle());
    }

    public void initShelve() {
        addDocToShelve(getCurrentDoc(), currentList);
        addDocToShelve(getRecommendedDoc(), recommendList);
        addDocToShelve(getFinishedDoc(), finishedList);
        addDocToShelve(getFavouriteAuthorDoc(), favouriteAuthorList);
        addDocToShelve(getMostPopularDoc(), mostPopularList);

    }

    public void addDocToShelve(ObservableList<Documents> docList, HBox hBox) {

        for (int i = 0; i < hBox.getChildren().size(); i++) {
            Documents doc = docList.get(i);
            VBox vBox = (VBox) hBox.getChildren().get(i);
            ImageView image = (ImageView) vBox.getChildren().get(0);
            TextField title = (TextField) vBox.getChildren().get(1);
            title.setText(docList.get(i).getTitle());
            vBox.setOnMouseClicked(event -> {
                documentimage3.setImage(image.getImage());
                namedocument3.setText(title.getText());
                descripe3.setText("Author: " + doc.getAuthor() + "\nDescripe: " + "\nType: " + doc.getType()
                        + "\n" + doc.getTagsString()+ "\nAvailable: " + "\nView: ");
            });
        }

    }

    //Sua mac dinh anh
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
                        imageView.setFitHeight(119);
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
                    System.out.println("empty row");
                } else {
                    Documents doc = getTableRow().getItem();
                    if (doc != null) {
                        System.out.println(doc.getTitle());
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
                        System.out.println("Doc is null");
                    }
                }
            }
        });

        tableView.setItems(data);
        if (tableView == tableView1)
        {
            search2(data, tableView, search1);
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

    private ObservableList<Documents> getAllDocuments() {
        // Khởi tạo danh sách tài liệu
        return FXCollections.observableArrayList(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("The Great Gatsby 2", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird 2", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984 2", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick 2", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("The Great Gatsby 3", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird 3", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984 3", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick 3", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50)
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
            search2(filteredDocuments, tableView, search1);
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
    private static final int ROWS_PER_PAGE = 5;
    private int currentPageIndex = 0;

    public void search2(ObservableList<Documents> data, TableView<Documents> table, TextField search) {
        FilteredList<Documents> filteredData = new FilteredList<>(data, p -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilter(filteredData, search.getText());
            updatePage(table, new SortedList<>(filteredData));
        });
        SortedList<Documents> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        updatePage(table, sortedData);
    }
    private void updatePage(TableView<Documents> table, SortedList<Documents> sortedData) {
        ObservableList<Documents> currentPageData = FXCollections.observableArrayList();
        int fromIndex = currentPageIndex * ROWS_PER_PAGE;
        int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, sortedData.size());
        currentPageData.setAll(sortedData.subList(fromIndex, toIndex));
        movePrev.setDisable(currentPageIndex == 0);
        moveNext.setDisable(toIndex >= sortedData.size());

        movePrev.setOnAction(event -> {
            if (currentPageIndex > 0) {
                currentPageIndex--;
                updatePage(table, sortedData);
            }
        });

        moveNext.setOnAction(event -> {
            if ((currentPageIndex + 1) * ROWS_PER_PAGE < sortedData.size()) {
                currentPageIndex++;
                updatePage(table, sortedData);
            }
        });

        table.setItems(currentPageData);
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

    //Account
    public void signOut() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to logout?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            goToNextStage("/com/nichga/proj97/LoginRegister.fxml", signOut, null);
        }
    }
    
    public void initAccount() {
        ChangeInfoButton.setVisible(true);
        SaveButton.setVisible(false);
        accountName.setEditable(false);
        accountEmail.setEditable(false);
        accountID.setEditable(false);
        accountAddress.setEditable(false);
        //Error User null
    }

    private void EditInfo() {
        if (ChangeInfoButton.isSelected()) {
            ChangeInfoButton.setVisible(false);
            SaveButton.setVisible(true);
            accountName.setEditable(true);
            accountEmail.setEditable(true);
            accountID.setEditable(true);
            accountAddress.setEditable(true);
        }
        if (SaveButton.isSelected()) {
            SaveButton.setVisible(false);
            ChangeInfoButton.setVisible(true);
            accountName.setEditable(false);
            accountEmail.setEditable(false);
            accountID.setEditable(false);
            accountAddress.setEditable(false);

            //Thao tac cap nhat Info duoi nay

        }
    }

}

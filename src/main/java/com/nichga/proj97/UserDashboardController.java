package com.nichga.proj97;

import com.nichga.proj97.Database.MemberRepository;
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
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserDashboardController extends StageController {
    private Users user;
    protected void setUser(Users user) {
        this.user = user;
    }
    ObservableList<Documents> mainData;
    SortedList<Documents> searchData;
    Set<String> tagList; {tagList = new HashSet<>();}
    Set<String> tagList2; {tagList2 = new HashSet<>();}
    //SortedList<Documents> sortedData;
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

    @FXML
    private Tab tabShelve, tabAllBooks;

    private ObservableList<Documents> userDocuments; {userDocuments = getUserDocuments();}

    private ToggleGroup toggleGroup;
    private final MemberRepository memberRepository = new MemberRepository();

    private ObservableList<Documents> getCurrentDoc() {
        return FXCollections.observableArrayList(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                new Documents("Duy deptrai", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100)
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
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150)
                //new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50),
                //new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100)
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
        Documents doc = getCurrentDoc().getFirst();
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
        borrowbutton1.setVisible(false);
        addDocToShelve(getCurrentDoc(), currentList);
        addDocToShelve(getRecommendedDoc(), recommendList);
        addDocToShelve(getFinishedDoc(), finishedList);
        addDocToShelve(getFavouriteAuthorDoc(), favouriteAuthorList);
        addDocToShelve(getMostPopularDoc(), mostPopularList);

    }

    public void addDocToShelve(ObservableList<Documents> docList, HBox hBox) {

        for (int i = 0; i < docList.size(); i++) {
            Documents doc = docList.get(i);
            VBox vBox = (VBox) hBox.getChildren().get(i);
            ImageView image = (ImageView) vBox.getChildren().get(0);
            TextField title = (TextField) vBox.getChildren().get(1);
            title.setText(docList.get(i).getTitle());
            vBox.setOnMouseClicked(event -> {
                borrowbutton1.setVisible(true);
                documentimage3.setImage(image.getImage());
                namedocument3.setText(title.getText());
                descripe3.setText("Author: " + doc.getAuthor() + "\nDescripe: " + "\nType: " + doc.getType()
                        + "\n" + doc.getTagsString()+ "\nAvailable: " + "\nView: ");
                borrowbutton1.setOnMouseClicked(e -> {
                    borrowDocument(doc);
                });
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
        imagecolumn.setCellFactory(_ -> new TableCell<>() {
            private final ImageView imageView = new ImageView();
            @Override
            protected void updateItem(String imageLink, boolean empty) {
                super.updateItem(imageLink, empty);
                if (empty || imageLink == null) {
                    setGraphic(null);
                } else {
                    try {
                        imageView.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imageLink))));
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

        detailcolumn.setCellFactory(_ -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Documents doc = getTableRow().getItem();
                    if (doc != null) {
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
        tableView.getSelectionModel().selectedItemProperty().addListener((_, oldValue, newValue) -> {

            if (newValue != null && !newValue.equals(oldValue)) {
                documentImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(newValue.getImageLink()))));
                namedocument.setText(newValue.getTitle());
                descripe.setText("Author: " + newValue.getAuthor() + "\nDescripe: " + "\nType: " + newValue.getType()
                        + "\n" + newValue.getTagsString()+ "\nAvailable: " + "\nView: ");

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

    private ObservableList<Documents> getAllDocuments() {
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
                new Documents("Tran Binh", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Dat fit", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50)
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
        sorttitle1.setOnAction(_ -> sortTable((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()), tableView1));
        sortauthor1.setOnAction(_ -> sortTable((o1, o2) -> o1.getAuthor().compareToIgnoreCase(o2.getAuthor()), tableView1));
        sortauthor12.setOnAction(_ -> sortTable((o1, o2) -> o1.getAuthor().compareToIgnoreCase(o2.getAuthor()), tableView12));
        sorttitle12.setOnAction(_ -> sortTable((o1, o2) -> o1.getTitle().compareToIgnoreCase(o2.getTitle()), tableView12));
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

    private void showDocumentsWithTag(TableView<Documents> tableView, ObservableList<Documents> data) {
        ObservableList<Documents> filteredDocuments = FXCollections.observableArrayList();
        Set<String> tags;
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
        search.textProperty().addListener((_, _, _) -> {
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

                memberRepository.updateInfo(user.getId(), accountName.getText(), accountAddress.getText(), accountEmail.getText(), accountPhone.getText());
            }
        });
    }

    @FXML
    public void changePassword() throws IOException {
        openNewStage("/com/nichga/proj97/ChangePassword.fxml", ChangePasswordButton, user);
    }

    public void borrowDocument(Documents doc) {
        if (false) { // Kiểm tra xem đã có document này trong myDoc chưa.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You already have this document");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to borrow?");
        alert.showAndWait();
        if (alert.getResult() == ButtonType.OK) {
            userDocuments.add(doc);
            tabpaneLibrary.getSelectionModel().select(2);
            tableView12.refresh();
            tableView12.getSelectionModel().select(doc);
            tableView12.scrollTo(doc);
        }
    }

    @FXML
    public void borrowDocFromAllBooks() {
        Documents doc = tableView1.getSelectionModel().getSelectedItem();
        if (doc != null) {
            borrowDocument(doc);
        } else {
            System.out.println("No doc selected");
        }
    }

//    @FXML
//    public void showQr() {
//        String bookId = "-k6Nfqud-kIC";
//
//        BufferedImage qr = user.borrow(bookId);
//        WritableImage qrImage = SwingFXUtils.toFXImage(qr, null);
//        ImageView imageView = new ImageView(qrImage);
//        imageView.setPreserveRatio(true);
//        imageView.setFitWidth(600);
//
//        // Đưa ImageView vào StackPane
//        StackPane newRoot = new StackPane(imageView);
//        Scene scene = new Scene(newRoot);
//
//        // Configure a new Stage
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
//    }

}

package com.nichga.proj97;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TestUserController extends StageController{
    Users user;
    protected void setUser(Users user) {
        this.user = user;
    }

    @FXML
    private TextField searchfield;
    @FXML
    private Button searchbutton;
    @FXML
    private TableView<Documents> tableView;
    @FXML
    private TableColumn<Documents, String> imagecolumn;
    @FXML
    private TableColumn<Documents, String> detailcolumn;
    @FXML
    private ImageView documentimage;
    @FXML
    private TextArea namedocument, descripe;
    @FXML
    private FlowPane tagsfield;
    @FXML
    private Button borrowbutton;

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

    public void initialize() {
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
                        setGraphic(null); // Không hiển thị nếu ảnh không tìm thấy
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
                } else {
                    Documents doc = getTableRow().getItem();
                    if (doc != null) {
                        setText(doc.getTitle() + "\nAuthor: " + doc.getAuthor() + "\nType: " + doc.getType()
                                + "\n" + doc.getTagsString() + "\nAvailable: " + "\nView: ");
                    } else {
                        setText(null);
                    }
                }
            }
        });
        tableView.setItems(getDocuments());
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                documentimage.setImage(new Image(getClass().getResourceAsStream(newValue.getImageLink())));
                namedocument.setText(newValue.getTitle());
                descripe.setText("Author: " + newValue.getAuthor() + "\nDescripe: " + "\nType: " + newValue.getType()
                        + "\n" + newValue.getTagsString()+ "\nAvailable: " + "\nView: ");
            }
        });
    }

}

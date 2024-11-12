package com.nichga.proj97;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import java.io.IOException;

public class UserMainDashboardController extends StageController {
    Users user;
    protected void setUser(Users user) {
        this.user = user;
        userID.setText(user.getName());
    }
    @FXML
    private TableColumn<Documents,String > author_col;

    @FXML
    private TableColumn<Documents,String > available_col;

    @FXML
    private ImageView avatar;

    @FXML
    private TableColumn<Documents, String> book_tittle_col;

    @FXML
    private Button borrowButton;

    @FXML
    private TableView<Documents> docList;

    @FXML
    private Label docName;

    @FXML
    private ImageView image;

    @FXML
    private Button minimize;

    @FXML
    private Button myDoc;

    @FXML
    private Button reccomendDoc;

    @FXML
    private Button signOut;

    @FXML
    private TableColumn<Documents, String> tag_col;

    @FXML
    private FlowPane tagsField;

    @FXML
    private TableColumn<Documents, String> type_col;

    @FXML
    private Label userID;

    @FXML
    private TableColumn<Documents, Integer> view_col;
    @FXML
    private void initialize() {
        book_tittle_col.setCellValueFactory(new PropertyValueFactory<Documents,String>("title"));
        author_col.setCellValueFactory(new PropertyValueFactory<Documents,String>("author"));
        type_col.setCellValueFactory(new PropertyValueFactory<Documents,String>("type"));
        view_col.setCellValueFactory(new PropertyValueFactory<Documents,Integer>("timesBorrowed"));
        tag_col.setCellValueFactory(cellData -> {
            String[] tags = cellData.getValue().tag;
            return new SimpleStringProperty(String.join(", ", tags));
        });
        available_col.setCellValueFactory(cellData -> {
            Integer currentCopies = cellData.getValue().getCurrentCopies();
            String status = (currentCopies != null && currentCopies > 0) ? "Yes" : "No";
            return new SimpleStringProperty(status);
        });
        docList.setItems(getDocuments());
    }
    private ObservableList<Documents> getDocuments() {
        // Khởi tạo danh sách tài liệu
        return FXCollections.observableArrayList(
                new Documents("The Great Gatsby", "F. Scott Fitzgerald", "Fiction", new String[]{"Classic", "Novel"}, 5, 100),
                new Documents("To Kill a Mockingbird", "Harper Lee", "Fiction", new String[]{"Classic", "Novel"}, 0, 200),
                new Documents("1984", "George Orwell", "Dystopian", new String[]{"Science Fiction", "Dystopian", "ABCDJFIOEU"}, 4, 150),
                new Documents("Moby Dick", "Herman Melville", "Adventure", new String[]{"Classic", "Adventure"}, 0, 50)
        );
    }
    private void displayDoc(Documents doc) {
        docName.setText(doc.getTitle());
        displayTags(doc);
        //set image cho image view ở dây.
    }
    public void signOut(ActionEvent e) throws IOException {
        super.goToNextStage("LogIn.fxml", signOut, null);
    }
    private void displayTags(Documents doc) {
        tagsField.getChildren().clear();
        for(String tags : doc.tag) {
            Button tagLabel = new Button(tags);
            tagLabel.getStyleClass().add("function-button");
            tagLabel.setPrefSize(68,31);
            // cài đặt chức năng sau khi bấm vào nút hiện ra danh sách các document có cùng tag.
            tagsField.getChildren().add(tagLabel);
        }
    }


}

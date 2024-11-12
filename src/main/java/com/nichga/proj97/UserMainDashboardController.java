package com.nichga.proj97;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class UserMainDashboardController extends StageController {
    Users user;
    protected void setUser(Users user) {
        this.user = user;
        userID.setText(user.getName());
    }
    @FXML
    private TableColumn<?, ?> author_col;

    @FXML
    private TableColumn<?, ?> available_col;

    @FXML
    private ImageView avatar;

    @FXML
    private TableColumn<?, ?> book_tittle_col;

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
    private TableColumn<?, ?> tag_col;

    @FXML
    private FlowPane tagsField;

    @FXML
    private TableColumn<?, ?> type_col;

    @FXML
    private Label userID;

    @FXML
    private TableColumn<?, ?> view_col;

    @FXML
    void signOut(ActionEvent event) {

    }

}

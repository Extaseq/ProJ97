package com.nichga.proj97;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller implements Initializable {
    @FXML
    private ComboBox<String> roleBox;

    private ObservableList <String> rolesList = FXCollections.observableArrayList("USER", "ADMIN");

    @FXML
    private TextField userName;

    @FXML
    private PasswordField passWord;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private CopyOnWriteArrayList <Users> users;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleBox.setItems(rolesList);
        UserMangement userMangement = new UserMangement();
        users = userMangement.getUsers();

    }

    @FXML
    public void Login (ActionEvent event) {
        String username = userName.getText();
        String password = passWord.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (roleBox.getValue() == null) {
            alert.setContentText("Please select your role!");
            alert.showAndWait();

        } else if (roleBox.getValue().equals("USER")) {
            for (Users user : users) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)) {
                        alert.setContentText("Login Successful!");
                        alert.showAndWait();
                        loginButton.getScene().getWindow().hide();
                        return;
                    }
                }
            }

            alert.setContentText("Wrong Username or Password!");
            alert.showAndWait();

        } else  {

        }

    }

    @FXML
    public void Register(ActionEvent event) throws IOException {
        loginButton.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("RegisterForm.fxml"));
        // Lấy stage hiện tại
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Tạo Scene mới với FXML đã tải
        Scene scene = new Scene(root);

        //stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

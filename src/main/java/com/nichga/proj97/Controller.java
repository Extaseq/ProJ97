package com.nichga.proj97;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        Boolean isLoggedIn = false;
        if (roleBox.getValue().equals("USER")) {
            for (Users user : users) {
                if (user.getUsername().equals(username)) {
                    if (user.getPassword().equals(password)) {
                        System.out.println("Login Successful!");
                        isLoggedIn = true;
                        break;
                    }
                }
            }
            if (!isLoggedIn) {
                System.out.println("Login Failed!");
            }

        }

    }
}

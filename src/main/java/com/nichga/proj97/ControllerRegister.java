package com.nichga.proj97;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class ControllerRegister {
    @FXML
    private TextField name;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField passWord;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private Button registerButton;

    @FXML
    private Button backLoginButton;

    @FXML
    public void Register(ActionEvent event) throws IOException {

        UserMangement userMangement = new UserMangement();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (userName.getText().isEmpty() || passWord.getText().isEmpty() || confirmPassword.getText().isEmpty() || name.getText().isEmpty()) {

            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else if (! passWord.getText().equals(confirmPassword.getText())) {

            alert.setContentText("Passwords do not match");
            alert.showAndWait();
        } else {
            CopyOnWriteArrayList <Users> ListUers = userMangement.getUsers();
            for (Users user : ListUers) {
                if (user.getUsername().equals(userName.getText())) {
                    alert.setContentText("Username already exists");
                    alert.showAndWait();
                    return;
                }
            }

            Users user = new Users(userName.getText(), passWord.getText());
            user.setName(name.getText());

            userMangement.addUser(user);
            alert.setContentText("Successfully registered! Please Login!");
            alert.showAndWait();

            // Avoid duplicate code
            BackToLogin(event);
        }
    }

    @FXML
    public void BackToLogin(ActionEvent event) throws IOException {
        backLoginButton.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
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

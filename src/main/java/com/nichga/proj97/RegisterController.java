package com.nichga.proj97;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class RegisterController extends StageController{
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
    private Button backButton;
    @FXML
    public void doRegister(ActionEvent event) throws IOException {

        UserManagement userMangement = new UserManagement();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (userName.getText().isEmpty() || passWord.getText().isEmpty() || confirmPassword.getText().isEmpty() || name.getText().isEmpty()) {

            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else if (! passWord.getText().equals(confirmPassword.getText())) {

            alert.setContentText("Passwords do not match");
            alert.showAndWait();
        } else {
            Map<String, Users> userLoginInfo = userMangement.getUserLoginInfo();
            if (userLoginInfo.containsKey(userName.getText())) {
                alert.setContentText("Username already exists");
                alert.showAndWait();
                return;
            }

            Users user = new Users(userName.getText(), passWord.getText());
            if(name.getText() != null) {
                user.setName(name.getText());
            }
            userMangement.addUser(user);
            alert.setContentText("Successfully registered! Please Login!");
            alert.showAndWait();
            goToNextStage("LogIn.fxml",registerButton, null);
        }
    }
    @FXML
    public void backToLogin(ActionEvent event) throws IOException {
        goToNextStage("Login.fxml", backButton, null);
    }

}

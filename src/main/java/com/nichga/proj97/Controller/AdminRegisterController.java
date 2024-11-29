package com.nichga.proj97.Controller;

import com.nichga.proj97.Services.Auth;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminRegisterController {
    @FXML
    private TextField fullname;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button applyButton;

    @FXML
    private ImageView back_button;

    @FXML
    private Label success_label;

    @FXML
    private Label error_label;

    Auth auth = new Auth();

    @FXML
    private void initialize() {
        applyButton.setOnAction(_ -> {
            String name = fullname.getText();
            String uname = username.getText();
            String pass = password.getText();
            String phone = phoneNumber.getText();
            if (auth.register(uname, pass, name)) {
                TranslateTransition moveLeft = new TranslateTransition();
                moveLeft.setNode(applyButton);
                moveLeft.setDuration(Duration.seconds(0.5));
                moveLeft.setToX(-70);
                moveLeft.setOnFinished(_ -> {
                    success_label.setVisible(true);
                    PauseTransition wait = new PauseTransition(Duration.seconds(3));
                    wait.setOnFinished(_ -> {
                        clearFields();
                        Stage stage = (Stage) applyButton.getScene().getWindow();
                        stage.close();
                    });
                    wait.play();
                });
                moveLeft.play();
            } else {
                error_label.setVisible(true);
            }
        });

        back_button.setOnMouseClicked(_ -> {
            Stage stage = (Stage) applyButton.getScene().getWindow();
            stage.close();
        });
    }

    private void clearFields() {
        fullname.clear();
        username.clear();
        password.clear();
        phoneNumber.clear();
    }
}

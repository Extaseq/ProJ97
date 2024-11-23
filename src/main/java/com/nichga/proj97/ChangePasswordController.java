package com.nichga.proj97;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcons;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TextField;

public class ChangePasswordController {
    @FXML
    private TextField PasswordText, newPasswordText, confirmPasswordText;
    @FXML
    private PasswordField PasswordField, newPasswordField, confirmPasswordField;
    @FXML
    private ToggleButton eye1, eye2, eye3;
    @FXML
    private FontAwesomeIcon icon1, icon2, icon3;
    @FXML
    private Button resetPassword;

    public void initialize() {
        PasswordField.setVisible(true);
        newPasswordField.setVisible(true);
        confirmPasswordField.setVisible(true);
        PasswordText.setVisible(false);
        newPasswordText.setVisible(false);
        confirmPasswordText.setVisible(false);
        eye1.setSelected(false);
        eye2.setSelected(false);
        eye3.setSelected(false);

        eye1.setOnAction(event -> Visibility(eye1, PasswordField, PasswordText, icon1));
        eye2.setOnAction(event -> Visibility(eye2, newPasswordField, newPasswordText, icon2));
        eye3.setOnAction(event -> Visibility(eye3, confirmPasswordField, confirmPasswordText, icon3));
        resetPassword.setOnAction(event -> ResetPassword());
    }

    private void Visibility(ToggleButton toggleButton, PasswordField field, TextField text, FontAwesomeIcon icon) {
        if (toggleButton.isSelected()) {
            text.setText(field.getText());
            text.setVisible(true);
            field.setVisible(false);
            icon.setIcon(FontAwesomeIcons.EYE);
        } else {
            field.setText(text.getText());
            field.setVisible(true);
            text.setVisible(false);
            icon.setIcon(FontAwesomeIcons.EYE_SLASH);
        }
    }

    private void ResetPassword() {
        String password, newPass, confirmPass;
        //Lấy dữ liệu chuẩn vào các string
        if (eye1.isSelected()) {
            password = PasswordText.getText();
        } else {
            password = PasswordField.getText();
        }
        if (eye2.isSelected()) {
            newPass = newPasswordText.getText();
        } else {
            newPass = newPasswordField.getText();
        }
        if (eye3.isSelected()) {
            confirmPass = confirmPasswordText.getText();
        } else {
            confirmPass = confirmPasswordField.getText();
        }

        //Thao tác với các String trên, không thao tác với textField, passwordField!!
        //Viết code dưới này


    }
}

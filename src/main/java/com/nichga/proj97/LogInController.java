package com.nichga.proj97;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class LogInController extends StageController {
    @FXML
    private TextField userName;
    @FXML
    private PasswordField passWord;
    @FXML
    private ComboBox<String> roleButton;
    private ObservableList<String> rolesList = FXCollections.observableArrayList("USER", "ADMIN");
    @FXML
    private Button logInButton;
    @FXML
    private Button registerButton;
    private UserManagement userManagement;
    private CopyOnWriteArrayList<Users> users;

    public void initialize() {
        // Thêm danh sách vai trò vào ComboBox
        roleButton.getItems().addAll(rolesList);
        // Khởi tạo UserManagement và danh sách users
        userManagement = new UserManagement();
        users = userManagement.getUsers();
    }

    public void LogIn(ActionEvent event) {
        // Lấy giá trị từ các trường nhập
        String u = userName.getText();
        String p = passWord.getText();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Kiểm tra nếu người dùng không chọn vai trò
        if (roleButton.getValue() == null) {
            alert.setContentText("Please select a role");
            alert.showAndWait();
        }
        // Xử lý khi vai trò là USER
        else if (roleButton.getValue().equals("USER")) {
            for (Users user : users) {
                if (user.getUsername().equals(u)) {
                    if (user.getPassword().equals(p)) {
                        alert.setContentText("Login Successful!");
                        alert.showAndWait();
                        try {
                            goToNextStage("/com/nichga/proj97/userMainDashboard.fxml", logInButton, user);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            }
            alert.setContentText("Wrong Username or Password!");
            alert.showAndWait();
        }
        // Xử lý khi vai trò là ADMIN (chưa hoàn thành)
        else if (roleButton.getValue().equals("ADMIN")) {
            // Thêm logic xử lý đăng nhập vai trò ADMIN
            alert.setContentText("Admin login functionality not implemented yet.");
            alert.showAndWait();
        }
    }
    public void Register(ActionEvent event) throws IOException {
        goToNextStage("Register.fxml",logInButton, null);
    }
}

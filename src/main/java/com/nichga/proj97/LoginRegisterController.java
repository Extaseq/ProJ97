package com.nichga.proj97;

import com.google.protobuf.StringValue;
import com.nichga.proj97.Database.MemberRepository;
import com.nichga.proj97.Database.UserRepository;
import com.nichga.proj97.Services.PasswordUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class LoginRegisterController extends StageController {
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane loginPane, registerPane;
    @FXML
    private ToggleButton loginSelect, registerSelect;
    @FXML
    private TextField name, userName1, userName2;
    @FXML
    private PasswordField password1, password2, confirmPassword;
    @FXML
    private Button loginButton, registerButton;
    @FXML
    private ComboBox<String> roleButton;
    private final ObservableList<String> rolesList = FXCollections.observableArrayList("USER", "ADMIN");

    private UserManagement userManagement;
    private CopyOnWriteArrayList<Users> users;
    private Map<String, Users> userLoginInfo ;
    private UserRepository userRepository;
    private MemberRepository memberRepository;


    public void initialize() {
        // Thêm danh sách vai trò vào ComboBox
        roleButton.getItems().addAll(rolesList);
        // Khởi tạo UserManagement và danh sách users
        userManagement = new UserManagement();
        users = userManagement.getUsers();

        userRepository = new UserRepository();
        memberRepository = new MemberRepository();
        SetButton();
    }

    public void Login(ActionEvent event) {
        // Lấy giá trị từ các trường nhập
        String userName = userName1.getText();
        String passWord = password1.getText();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Kiểm tra nếu người dùng không chọn vai trò
        if (roleButton.getValue() == null) {
            alert.setContentText("Please select a role");
            alert.showAndWait();
        }
        // Xử lý khi vai trò là USER
        else if (roleButton.getValue().equals("USER")) {
            if (userRepository.userExists(userName)) {
                String[] authInfo = userRepository.getAuthInformation(userName);
                if (PasswordUtil.verifyPassword(passWord, authInfo[0], authInfo[1])) {
                    try {
                        Users user = new Users(userName, passWord);
                        goToNextStage("/com/nichga/proj97/UserDashboard.fxml", loginButton, user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String userName = userName2.getText();
        String passWord = password2.getText();

        if (userName2.getText().isEmpty() || password2.getText().isEmpty() || confirmPassword.getText().isEmpty() || name.getText().isEmpty()) {
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
        } else if (! password2.getText().equals(confirmPassword.getText())) {
            alert.setContentText("Passwords do not match");
            alert.showAndWait();
        } else {

            if (userRepository.userExists(userName)) {
                alert.setContentText("Username already exists");
                alert.showAndWait();
                return;
            }
            Users user = new Users(userName, passWord);
            if(name.getText() != null) {
                user.setName(name.getText());
            }
            String salt = PasswordUtil.generateSalt();
            String passwordHash = PasswordUtil.hashPassword(passWord, salt);

            String newMemberId = String.valueOf(memberRepository.getLastMemberId() + 1);
            userRepository.addNewUser(newMemberId, userName, passwordHash, salt);
            memberRepository.addNewMember(name.getText(), "NULL", "NULL");
            alert.setContentText("Successfully registered! Please Login!");
            alert.showAndWait();
            //goto Login
            loginSelect.setSelected(true);
            stackPane.getChildren().clear();
            stackPane.getChildren().add(loginPane);
        }
    }

    private void SetButton() {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(loginPane);
        loginSelect.setSelected(true);
        loginSelect.setOnAction(event -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(loginPane);
        });
        registerSelect.setOnAction(event -> {
            stackPane.getChildren().clear();
            stackPane.getChildren().add(registerPane);
        });
    }
}

package com.nichga.proj97.Controller;

import com.nichga.proj97.Database.DatabaseConnector;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditUserInfoController {
    @FXML
    private TextField address;

    @FXML
    private Button apply;

    @FXML
    private TextField email;

    @FXML
    private TextField fullname;

    @FXML
    private TextField status;

    @FXML
    private TextField phone;

    @FXML
    private TextField username;

    @FXML
    private Label success;

    public void initAccountID(String account_id, String member_id) {
        apply.setOnAction(_ -> {
            String username = this.username.getText();
            String fullname = this.fullname.getText();
            String status = this.status.getText();
            String phone = this.phone.getText();
            String address = this.address.getText();
            String email = this.email.getText();

            if (username.isEmpty() || fullname.isEmpty() || status.isEmpty()
                    || phone.isEmpty() || address.isEmpty() || email.isEmpty()) {
                return;
            }

            String updateUser = "UPDATE useraccounts SET username = ?, status = ? WHERE account_id = ?";
            String updateMember = "UPDATE members SET fullname = ?, phone = ?, address = ?, email = ? WHERE member_id = ?";
            try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
                assert connection != null;
                connection.setAutoCommit(false);
                try (PreparedStatement statement = connection.prepareStatement(updateUser)) {
                    statement.setString(1, username);
                    statement.setString(2, status);
                    statement.setString(3, account_id);
                    statement.executeUpdate();
                }

                try (PreparedStatement statement = connection.prepareStatement(updateMember)) {
                    statement.setString(1, fullname);
                    statement.setString(2, phone);
                    statement.setString(3, address);
                    statement.setString(4, email);
                    statement.setString(5, member_id);
                    statement.executeUpdate();
                }
                connection.commit();

                success.setVisible(true);
                PauseTransition wait = new PauseTransition(Duration.seconds(2));
                wait.setOnFinished(_ -> {
                    Stage stage = (Stage) success.getScene().getWindow();
                    stage.close();
                });
                wait.play();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                try (Connection connection = DatabaseConnector.getInstance().getConnection()) {
                    connection.rollback();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    @FXML
    private void initialize() {

    }
}

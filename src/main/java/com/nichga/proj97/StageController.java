package com.nichga.proj97;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

import static java.lang.System.exit;

public abstract class StageController {

    private Stage previousStage;  // Stage trước đó
    @FXML
    Button backButton;

    @FXML
    Button minimizeButton;
    @FXML
    Button closeButton;

    // Phương thức thiết lập Stage trước đó
    public void setPreviousStage(Stage previousStage) {
        this.previousStage = previousStage;
    }

    // Phương thức trả về Stage trước đó
    public Stage getPreviousStage() {
        return this.previousStage;
    }

    // Phương thức để lấy Stage hiện tại từ sự kiện ActionEvent
    public Stage getCurrentStage(ActionEvent event) {
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    // Phương thức quay lại Stage trước đó
    public void goBackToPreviousStage(ActionEvent e) {
        if (previousStage != null) {
            Stage currentStage = getCurrentStage(e);
            currentStage.close();
            previousStage.show();
        }
    }
    public void goToNextStage(String s, Button b, Users user) throws IOException {

        Stage currentStage = (Stage) b.getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();

        // Kiểm tra controller là instance của StageController
        Object controller = loader.getController();
        if (controller instanceof StageController) {
            ((StageController) controller).setPreviousStage(currentStage);
        }

        if (controller instanceof UserDashboardController) {
            if (user != null) {

                ((UserDashboardController) controller).setUser(user);
                ((UserDashboardController) controller).initAccount();
            } else {
                System.out.println("Warning: Users object is null, skipping setUser.");
            }
        }


        // Hiển thị Stage mới
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        setUpMouseEvents(root, stage);
        stage.show();
    }
    public void minimize(ActionEvent e) {
        Stage stage = (Stage) minimizeButton.getScene().getWindow();
        stage.setIconified(true);
    }
    public void close(ActionEvent e) {
        exit(0);
    }
    private void setUpMouseEvents(Parent root, Stage stage) {
        root.setOnMousePressed((MouseEvent e) -> {
            x = e.getSceneX();
            y = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
        });
    }

    private double x = 0;
    private double y = 0;

    public void openNewStage(String s, Button b, Users user) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(s));
        Parent root = loader.load();

        // Kiểm tra controller là instance của StageController
        Object controller = loader.getController();

        if (controller instanceof ChangePasswordController) {
            if (user != null) {
                ((ChangePasswordController) controller).setUser(user);
                ((ChangePasswordController) controller).initialize();

            } else {
                System.out.println("Warning: Users object is null, skipping setUser.");
            }
        }
        // Hiển thị Stage mới
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        setUpMouseEvents(root, stage);
        stage.show();
    }

    public void closeCurrentStage(Button b) {
        Stage currentStage = (Stage) b.getScene().getWindow();
        currentStage.close();
    }
}

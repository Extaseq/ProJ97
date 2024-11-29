package com.nichga.proj97.Controller;

import com.nichga.proj97.Services.Auth;
import com.nichga.proj97.Services.DatabaseService;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.SQLException;

public class BorrowPanelController {
    @FXML
    private Button apply;

    @FXML
    private ImageView book_check;

    @FXML
    private Button book_check_button;

    @FXML
    private Label book_fail;

    @FXML
    private TextField book_id;

    @FXML
    private Text title;

    @FXML
    private ImageView user_check;

    @FXML
    private Button user_check_button;

    @FXML
    private Label user_fail;

    @FXML
    private TextField user_id;

    @FXML
    private Label success;

    private final DatabaseService dbs = new DatabaseService();

    private void initTitle() {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0), _ -> title.setOpacity(1)),
            new KeyFrame(Duration.seconds(0.5), _ -> title.setOpacity(0))
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setRate(1);
        timeline.play();
    }

    private void initButton() {
        user_check_button.setOnAction(_ -> {
            String id = user_id.getText();
            try {
                int member_id = Integer.parseInt(id);
                if (!dbs.getMemberRepo().memberExists(member_id)) {
                    System.out.println("No user found!");
                    user_fail.setVisible(true);
                } else {
                    user_fail.setVisible(false);
                    playButtonAnimation(user_check_button, user_check);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid user ID format!");
                user_fail.setVisible(true);
            }
        });

        book_check_button.setOnAction(_ -> {
            String id = book_id.getText();
            if (!dbs.getBookRepo().bookAvailable(id)) {
                System.out.println("No book found!");
                book_fail.setVisible(true);
            } else {
                book_fail.setVisible(false);
                playButtonAnimation(book_check_button, book_check);
            }
        });

        apply.setOnAction(_ -> {
            String memberID = user_id.getText();
            String bookID = book_id.getText();
            DatabaseService dbs = new DatabaseService();
            dbs.getBorrowRepo().returnBook(memberID, bookID);
            dbs.getBookRepo().adjustAfterReturn(bookID);
            dbs.getReadingHistoryRepository().returnBook(memberID, bookID);
            PauseTransition wait = new PauseTransition(Duration.seconds(2));
            wait.setOnFinished(_ -> {
                Stage stage = (Stage) book_check.getScene().getWindow();
                stage.close();
            });
            wait.play();
        });
    }

    private void playButtonAnimation(Button button, Node resultIndicator) {
        TranslateTransition moveLeft = new TranslateTransition();
        moveLeft.setNode(button);
        moveLeft.setDuration(Duration.seconds(0.5));
        moveLeft.setToX(-40);
        moveLeft.setOnFinished(_ -> resultIndicator.setVisible(true));
        moveLeft.play();
    }


    @FXML
    private void initialize() {
        initTitle();
        initButton();
    }
}

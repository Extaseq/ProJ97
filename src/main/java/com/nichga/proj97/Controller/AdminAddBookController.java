package com.nichga.proj97.Controller;

import com.nichga.proj97.Services.DatabaseService;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AdminAddBookController {
    @FXML
    private TextField amount;

    @FXML
    private TextField author;

    @FXML
    private TextField book_id;

    @FXML
    private TextField cover_url;

    @FXML
    private ImageView display_image;

    @FXML
    private TextField genre;

    @FXML
    private TextField isbn;

    @FXML
    private TextField published_year;

    @FXML
    private TextField publisher;

    @FXML
    private TextField title;

    @FXML
    private Button apply;

    @FXML
    private Button test_img;

    @FXML
    private ImageView back_button;

    @FXML
    private Label failed;

    @FXML
    private Label success;

    private final DatabaseService dbs = new DatabaseService();

    @FXML
    private void initialize() {
        apply.setOnAction(_ -> {
            String bookID = book_id.getText();
            String bookTitle = title.getText();
            String bookAuthor = author.getText();
            String bookPublisher = publisher.getText();
            String bookGenre = genre.getText();
            String bookPublishedYear = published_year.getText();
            String bookISBN = isbn.getText();
            String bookAvailable = amount.getText();
            String bookCoverURL = cover_url.getText();

            if (bookID.isEmpty() || bookTitle.isEmpty() || bookAuthor.isEmpty() || bookPublisher.isEmpty() ||
                    bookGenre.isEmpty() || bookPublishedYear.isEmpty() || bookISBN.isEmpty() || bookAvailable.isEmpty()) {
                failed.setVisible(true);
                return;
            }

            try {
                boolean isInserted = dbs.getBookRepo().insertBook(
                        bookID, bookTitle, bookAuthor, bookPublisher, bookGenre,
                        bookPublishedYear, bookISBN, bookAvailable, bookCoverURL
                );
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(_ -> {
                    clearFields();
                    Stage stage = (Stage) back_button.getScene().getWindow();
                    stage.close();
                });
                if (isInserted) {
                    failed.setVisible(false);
                    success.setVisible(true);
                } else {
                    failed.setVisible(true);
                }
                pause.play();
            } catch (Exception e) {
                System.out.println("Error while adding book: " + e.getMessage());
            }
        });

        test_img.setOnAction(_ -> {
            try {
                String url = cover_url.getText();
                if (url.isEmpty()) {
                    display_image.setImage(new Image(
                        "https://imgur.com/VwiPLSU.png"
                    ));
                    return;
                }
                display_image.setImage(new Image(url));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                display_image.setImage(new Image(
                        "https://imgur.com/VwiPLSU.png"
                ));
            }
        });

        back_button.setOnMouseClicked(_ -> {
            Stage stage = (Stage) apply.getScene().getWindow();
            stage.close();
        });
    }

    private void clearFields() {
        book_id.clear();
        title.clear();
        author.clear();
        publisher.clear();
        genre.clear();
        published_year.clear();
        isbn.clear();
        amount.clear();
        cover_url.clear();
        display_image.setImage(null);
    }
}

package com.nichga.proj97.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;


public class BookSectionController {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView book_cover_1;

    @FXML
    private ImageView book_cover_2;

    @FXML
    private ImageView book_cover_3;

    @FXML
    private ImageView book_cover_4;

    @FXML
    private ImageView book_cover_5;

    @FXML
    private ImageView book_cover_6;

    @FXML
    private Text book_title_1;

    @FXML
    private Text book_title_2;

    @FXML
    private Text book_title_3;

    @FXML
    private Text book_title_4;

    @FXML
    private Text book_title_5;

    @FXML
    private Text book_title_6;

    List<Text> title = new ArrayList<>(); {
        title.add(book_title_1);
        title.add(book_title_2);
        title.add(book_title_3);
        title.add(book_title_4);
        title.add(book_title_5);
        title.add(book_title_6);
    }

    private void prepareText() {
        for (Text t : title) {
            t.setFill(Color.web("#e5e5e5"));
            t.setFont(Font.font("Eina03-Bold", 15));
        }
    }


    @FXML
    private void initialize() {
        book_title_2.setText("Test name");
        book_title_2.setFill(Color.web("#e5e5e5"));
        book_title_2.setFont(Font.font("Eina03-Bold", 15));
    }
}

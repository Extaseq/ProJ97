package com.nichga.proj97;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

public class TestBorrow extends Application {
    @Override
    public void start(Stage primaryStage) {
        Users test = new Users();
        test.setId(8);
        BufferedImage bf = test.borrow("-k6Nfqud-kIC");
        if (bf != null) {
            Image fxImage = SwingFXUtils.toFXImage(bf, null);
            ImageView imageView = new ImageView(fxImage);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(400);
            imageView.setFitHeight(400);
            StackPane root = new StackPane(imageView);
            Scene scene = new Scene(root, 500, 500);
            Stage stage = new Stage();
            stage.setTitle("QR Code Display");
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("BufferedImage is null!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

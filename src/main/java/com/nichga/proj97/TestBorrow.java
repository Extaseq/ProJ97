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
        // Tạo đối tượng Users và lấy BufferedImage
        Users test = new Users();
        BufferedImage bf = test.borrow("-k6Nfqud-kIC");

        if (bf != null) {
            // Chuyển BufferedImage thành Image của JavaFX
            Image fxImage = SwingFXUtils.toFXImage(bf, null);

            // Tạo ImageView để hiển thị ảnh
            ImageView imageView = new ImageView(fxImage);

            // Đặt kích thước và canh giữa
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(400); // Thay đổi kích thước ảnh nếu cần
            imageView.setFitHeight(400);

            // Tạo layout và thêm ImageView
            StackPane root = new StackPane(imageView);

            // Tạo scene và gắn vào stage
            Scene scene = new Scene(root, 500, 500);
            primaryStage.setTitle("QR Code Display");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            System.out.println("BufferedImage is null!");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

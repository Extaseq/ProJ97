package com.nichga.proj97;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class Test extends Application {

    static class RoundedAvatar {
        @FXML
        private ImageView avatar_img;

        @FXML
        public void initialize() {
            Rectangle clip = new Rectangle(avatar_img.getBoundsInLocal().getWidth(), avatar_img.getBoundsInLocal().getHeight());
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            avatar_img.setClip(clip);

            SnapshotParameters params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            WritableImage image = avatar_img.snapshot(params, null);

            avatar_img.setClip(null);
            avatar_img.setEffect(new DropShadow(10, Color.BLACK));
            avatar_img.setImage(image);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            getClass().getResource(
                "/com/nichga/proj97/test.fxml"
            )
        );
        loader.setController(new RoundedAvatar());
        stage.setTitle("Test");
        stage.setScene(new Scene(loader.load()));
        stage.show();
    }
}

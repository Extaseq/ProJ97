package com.nichga.proj97;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ImageHelper {
    /**
     * Rounds the corners of the provided {@code ImageView} to create a rounded or circular image.
     * If {@code radius} is set to {@code -1}, the radius will be automatically calculated to create
     * a fully circular clip based on the smallest dimension of the image. Otherwise, the specified
     * {@code radius} will be applied to the corners.
     *
     * @param image  the {@code ImageView} object whose corners will be rounded
     * @param radius the radius of the corner rounding; set to {@code -1} for a circular image
     */
    public static void roundImage(ImageView image, int radius) {
        double width = image.getBoundsInLocal().getWidth();
        double height = image.getBoundsInLocal().getHeight();

        Rectangle clip = new Rectangle(width, height);

        double rad = radius == -1 ? Math.min(width, height) : radius;

        clip.setArcHeight(rad);
        clip.setArcWidth(rad);

        image.setClip(clip);

        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        WritableImage writableImage = image.snapshot(params, null);

        image.setClip(null);
        image.setImage(writableImage);
    }
}

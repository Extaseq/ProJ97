package com.nichga.proj97.Util;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Animation {
    public static RotateTransition glowAndRotate(ImageView image) {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(image);
        rotateTransition.setDuration(Duration.seconds(1));
        rotateTransition.setByAngle(-360);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        Glow glow = new Glow(0.8);
        image.setEffect(glow);
        rotateTransition.setOnFinished(_ -> image.setEffect(null));
        return rotateTransition;
    }
}
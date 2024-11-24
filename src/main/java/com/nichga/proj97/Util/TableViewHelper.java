package com.nichga.proj97.Util;

import javafx.scene.control.TableView;
import javafx.scene.input.ScrollEvent;

public class TableViewHelper {
    public static void disableScrollBars(TableView<?> tableView) {
        tableView.addEventFilter(ScrollEvent.SCROLL, event -> {
            if (event.getDeltaX() != 0) {
                event.consume();
            }
        });
    }
}

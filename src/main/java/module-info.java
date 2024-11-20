module com.nichga.proj97 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires com.zaxxer.hikari;
    requires com.google.gson;
    requires java.sql;

    opens com.nichga.proj97 to javafx.fxml;
    opens Application to javafx.fxml;
    exports com.nichga.proj97;
    exports Application;
    exports Database;
    exports Services;
}
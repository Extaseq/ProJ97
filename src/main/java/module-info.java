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
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires com.google.zxing;
    requires com.google.protobuf;
    requires fontawesomefx;
    requires javafx.swing;

    opens com.nichga.proj97 to javafx.fxml, com.google.gson;
    opens com.nichga.proj97.Application to javafx.fxml;
    exports com.nichga.proj97;
    exports com.nichga.proj97.Application;
    exports com.nichga.proj97.Database;
    exports com.nichga.proj97.Services;
    exports com.nichga.proj97.Util;
    exports com.nichga.proj97.Model;
    opens com.nichga.proj97.Model to javafx.fxml, com.google.gson;
    exports com.nichga.proj97.Controller;
    opens com.nichga.proj97.Controller to javafx.fxml;
    opens com.nichga.proj97.Util to com.google.gson, javafx.fxml;
}
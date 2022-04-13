module projects.softwareengineering.restaurantms {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.oracle.database.jdbc;
    requires java.sql;

    opens projects.software.restaurantns to javafx.fxml;
    exports projects.software.restaurantns;
}
module com.wasp.javafx {
    requires com.opencsv;
    requires java.sql;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports com.wasp;
    opens com.wasp to javafx.fxml;
    exports com.wasp.controller;
    opens com.wasp.controller to javafx.fxml;
    exports com.wasp.data;
    opens com.wasp.data to javafx.fxml;
    exports com.wasp.handler;
    opens com.wasp.handler to javafx.fxml;
    exports com.wasp.ui.elements;
    opens com.wasp.ui.elements to javafx.fxml;
}
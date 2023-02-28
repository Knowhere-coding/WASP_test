module com.wasp.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.wasp;
    opens com.wasp to javafx.fxml;
    exports com.wasp.controller;
    opens com.wasp.controller to javafx.fxml;
    exports com.wasp.data;
    opens com.wasp.data to javafx.fxml;
}
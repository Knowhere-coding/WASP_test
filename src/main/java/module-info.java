module com.wasp.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.wasp.javafx to javafx.fxml;
    exports com.wasp.javafx;
}
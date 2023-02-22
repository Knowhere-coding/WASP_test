package com.wasp.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setResizable(false);
        switchToLoginPage();
        stage.show();
    }

    public void switchToLoginPage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login_page.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.initialize();
        controller.setMainApp(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void switchToMainPage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_page.fxml"));
        Parent root = loader.load();

        BaseController controller = loader.getController();
        controller.initialize();
        controller.setMainApp(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
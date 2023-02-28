package com.wasp;

import com.wasp.controller.BaseController;
import com.wasp.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    private Stage stage;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        stage.setResizable(false);
        stage.setTitle("WASP - Password Manager");

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/WASP.png")));
        stage.getIcons().add(icon);

        switchToLoginPage();
        stage.show();
    }

    public void switchToLoginPage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/login_page.fxml"));
        Parent root = loader.load();

        LoginController controller = loader.getController();
        controller.initialize();
        controller.setMainApp(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    public void switchToMainPage() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/main_page.fxml"));
        Parent root = loader.load();

        BaseController controller = loader.getController();
        controller.initialize();
        controller.setMainApp(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
package com.wasp;

import com.wasp.controller.BaseController;
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

        switchToPage("login_page.fxml");
        stage.show();
    }

    public void switchToPage(String pageName) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/" + pageName));
        Parent root = loader.load();

        BaseController controller = loader.getController();
        controller.initialize();
        controller.setMainApp(this);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
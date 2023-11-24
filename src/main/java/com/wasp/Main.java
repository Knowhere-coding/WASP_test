package com.wasp;

import com.wasp.controller.BaseController;
import com.wasp.controller.InactivityDialogController;
import com.wasp.data.AppData;
import com.wasp.handler.InactivityEventHandler;
import com.wasp.handler.InactivityTimer;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    private Stage stage;
    private Timeline timeline;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        setupConfigData();

        this.stage = stage;
        AppData.getInstance().setStage(stage);

        stage.setResizable(false);
        stage.setTitle("WASP - Password Manager");

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("images/WASP.png")));
        stage.getIcons().add(icon);

        InactivityTimer inactivityTimer = InactivityTimer.getInstance();
        inactivityTimer.start();
        AppData.getInstance().setInactivityTimer(inactivityTimer);

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> checkLogout()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        switchToPage("login_page.fxml");
        stage.show();
    }

    @Override
    public void stop() {
        timeline.stop();
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    public void switchToPage(String pageName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/" + pageName));
            Parent root = loader.load();
            AppData.getInstance().setRoot(root);

            BaseController controller = loader.getController();
            controller.setMainApp(this);

            InactivityEventHandler eventHandler = new InactivityEventHandler(root);
            eventHandler.start();
            AppData.getInstance().setInactivityEventHandler(eventHandler);

            if (stage.getScene() == null) {
                stage.setScene(new Scene(root));
            } else {
                stage.getScene().setRoot(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkLogout() {
        if (!"login_page".equals(AppData.getInstance().getRoot().getId()) && AppData.getInstance().isInactive()) {
            AppData.getInstance().setInactive(false);
            AppData.getInstance().getRoot().setEffect(new GaussianBlur());
            Platform.runLater(this::showInactivityDialog);
        }
    }

    private void showInactivityDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pages/" + "logout_dialog.fxml"));
            loader.load();

            InactivityDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.showDialog(stage.getScene());

            AppData.getInstance().getInactivityEventHandler().resetTimer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupConfigData() {
        AppData.getInstance().setInactivityPeriod(2 * 60 * 1000); // 2min;
        AppData.getInstance().setMasterAccountDataFile(new File("data" + File.separator + "master_account_data.csv"));
        AppData.getInstance().setAccountDataFile(new File("data" + File.separator + "account_data.csv"));
        AppData.getInstance().setBackupDirectory(new File("backup"));
    }
}
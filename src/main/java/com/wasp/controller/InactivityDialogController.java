package com.wasp.controller;

import com.wasp.data.AppData;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

public class InactivityDialogController extends BaseController {

    @FXML private Dialog<Object> logoutDialog;
    @FXML private DialogPane dialogPane;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    @Override
    public void initialize() {
        logoutDialog.initStyle(StageStyle.UNDECORATED);
    }

    @FXML
    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onLoginButtonPressed();
        }
    }

    public void showDialog(Scene scene) {
        logoutDialog.initOwner(scene.getWindow());
        logoutDialog.initModality(Modality.APPLICATION_MODAL);
        logoutDialog.setX(scene.getWindow().getX() + scene.getWidth()/2 - logoutDialog.getWidth()/2);
        logoutDialog.setY(scene.getWindow().getY() + scene.getHeight()/2 - logoutDialog.getHeight()/2);
        logoutDialog.showAndWait();
    }

    @FXML
    private void onLoginButtonPressed() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.equals(AppData.getInstance().getUsername()) && password.equals(AppData.getInstance().getPassword())) {
            AppData.getInstance().getRoot().setEffect(null);
            dialogPane.getScene().getWindow().hide();
        } else {
            usernameField.setStyle("-fx-border-width: 2px; -fx-border-radius: 2px; -fx-border-color: rgba(255, 0, 0, 0.8);");
            passwordField.setStyle("-fx-border-width: 2px; -fx-border-radius: 2px; -fx-border-color: rgba(255, 0, 0, 0.8);");
        }
    }

    @FXML
    private void onLogoutButtonPressed() {
        dialogPane.getScene().getWindow().hide();
        mainApp.switchToPage("login_page.fxml");
    }
}

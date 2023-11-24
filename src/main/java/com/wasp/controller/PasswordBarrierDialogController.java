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

public class PasswordBarrierDialogController extends BaseController {

    @FXML
    private Dialog<Object> passwordBarrierDialog;
    @FXML private DialogPane dialogPane;
    @FXML private TextField passwordField;

    private boolean isValidated;

    @Override
    public void initialize() {
        passwordBarrierDialog.initStyle(StageStyle.UNDECORATED);
    }

    @FXML
    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onValidateButtonPressed();
        }
    }

    public void showDialog(Scene scene) {
        passwordBarrierDialog.initOwner(scene.getWindow());
        passwordBarrierDialog.initModality(Modality.APPLICATION_MODAL);
        passwordBarrierDialog.setX(scene.getWindow().getX() + scene.getWidth()/2 - passwordBarrierDialog.getWidth()/2);
        passwordBarrierDialog.setY(scene.getWindow().getY() + scene.getHeight()/2 - passwordBarrierDialog.getHeight()/2);
        passwordBarrierDialog.showAndWait();
    }

    @FXML
    private void onCancelButtonPressed() {
        isValidated = false;
        dialogPane.getScene().getWindow().hide();
    }

    @FXML
    private void onValidateButtonPressed() {
        String password = passwordField.getText();

        if (password.equals(AppData.getInstance().getPassword())) {
            isValidated = true;
            dialogPane.getScene().getWindow().hide();
        } else {
            passwordField.setStyle("-fx-border-width: 2px; -fx-border-radius: 2px; -fx-border-color: rgba(255, 0, 0, 0.8);");
        }
    }

    public boolean isValidated() {
        return isValidated;
    }
}

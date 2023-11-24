package com.wasp.controller;

import com.wasp.data.AppData;
import com.wasp.data.MasterAccountData;
import com.wasp.handler.CsvHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController extends BaseController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label loginStatus;

    @FXML
    private void onEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            onLoginButtonPressed();
        }
    }

    @FXML
    private void onLoginButtonPressed() {
        CsvHandler<MasterAccountData> csvHandler = new CsvHandler<>(AppData.getInstance().getMasterAccountDataFile(), MasterAccountData.class);

        String username = usernameField.getText();
        String password = passwordField.getText();

        MasterAccountData masterAccountDataList = csvHandler.getCsvList().get(0);
        String masterUsername = masterAccountDataList.getMasterUsername();
        String masterPassword = masterAccountDataList.getMasterPassword();

        if (username.equals(masterUsername) && password.equals(masterPassword)) {
            AppData.getInstance().setUsername(username);
            AppData.getInstance().setPassword(password);
            mainApp.switchToPage("main_page.fxml");
        } else {
            if (username.isEmpty() && password.isEmpty()) {
                loginStatus.setText("Please input a username and password!");
            } else if (username.isEmpty()) {
                loginStatus.setText("Please input a username!");
            } else if (password.isEmpty()) {
                loginStatus.setText("Please input a password!");
            } else {
                loginStatus.setText("Wrong username or password!");
            }

            passwordField.setText("");
        }
    }
}

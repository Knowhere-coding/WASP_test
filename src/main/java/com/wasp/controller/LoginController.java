package com.wasp.controller;

import com.wasp.data.AppData;
import com.wasp.data.MasterAccountData;
import com.wasp.handler.CsvHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.util.Objects;

public class LoginController extends BaseController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Label loginStatus;

    public void onLoginButtonPressed() {
        File csvFile = new File(Objects.requireNonNull(getClass().getResource("/com/wasp/data/master_account_data.csv")).getFile());
        CsvHandler<MasterAccountData> csvHandler = new CsvHandler<>(csvFile, MasterAccountData.class);

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
        }
    }
}

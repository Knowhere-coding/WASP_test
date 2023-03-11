package com.wasp.controller;

import com.wasp.data.AppData;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController extends BaseController {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    @FXML private Label loginStatus;

    public void onLoginButtonPressed(ActionEvent event) throws Exception {
        String username = usernameField.getCharacters().toString();
        String password = passwordField.getCharacters().toString();

        if (username.equals("Test") && password.equals("1234")) {
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

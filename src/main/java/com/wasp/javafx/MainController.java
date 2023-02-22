package com.wasp.javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class MainController extends BaseController {

    @FXML private Label usernameLabel;
    @FXML private Label passwordLabel;
    @FXML private Button logoutButton;

    @Override
    public void initialize() {
        String username = AppData.getInstance().getUsername();
        String password = AppData.getInstance().getPassword();

        usernameLabel.setText(username);
        passwordLabel.setText(password);
    }

    public void onLogoutButtonPressed(ActionEvent event) throws Exception {
        mainApp.switchToLoginPage();
    }
}

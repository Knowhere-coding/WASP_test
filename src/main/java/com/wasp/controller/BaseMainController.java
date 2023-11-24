package com.wasp.controller;

import javafx.fxml.FXML;

public class BaseMainController extends BaseController {
    @FXML
    private void onLogoutButtonPressed() {
        mainApp.switchToPage("login_page.fxml");
    }

    @FXML
    private void onHomeButtonPressed() {
        mainApp.switchToPage("main_page.fxml");
    }

    @FXML
    private void onAddAccountButtonPressed() {
        mainApp.switchToPage("add_account_page.fxml");
    }

    @FXML
    private void onBackupButtonPressed() {
        mainApp.switchToPage("backup_page.fxml");
    }
}

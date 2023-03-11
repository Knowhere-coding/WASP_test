package com.wasp.controller;

import com.wasp.data.Entry;
import com.wasp.handler.CsvHandler;
import com.wasp.handler.InputValidationHandler;
import com.wasp.handler.ObservableListHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddAccountController extends BaseController {

    @FXML private ChoiceBox<String> categoryOptions;
    @FXML private TextField siteNameField;
    @FXML private TextField urlField;
    @FXML private Label urlStatusLabel;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private Label emailStatusLabel;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordTextField;
    @FXML private ProgressBar passwordSecurityBar;
    @FXML private CheckBox hidePasswordCheckBox;
    @FXML private ChoiceBox<String> expirationOptions;
    @FXML private Label statusLabel;

    private CsvHandler csvHandler;
    private TextField[] textFields;
    private ChoiceBox[] choiceBoxes;

    @Override
    public void initialize() {
        List<String> categories = new ArrayList<>(Arrays.asList("email", "social media", "gaming", "coding", "shopping", "banking", "education", "private", "other"));
        categoryOptions.setItems(ObservableListHandler.getObservableList(categories));
        categoryOptions.setValue("Category");

        List<String> expiration = new ArrayList<>(Arrays.asList("never", "daily", "weekly", "monthly", "quarterly", "half-yearly", "yearly"));
        expirationOptions.setItems(ObservableListHandler.getObservableList(expiration));
        expirationOptions.setValue("monthly");

        textFields = new TextField[]{siteNameField, urlField, usernameField, emailField, passwordField, passwordTextField};
        choiceBoxes = new ChoiceBox[]{categoryOptions, expirationOptions};
    }

    public void onLogoutButtonPressed() throws Exception {
        mainApp.switchToPage("login_page.fxml");
    }

    public void onHomeButtonPressed() throws Exception {
        mainApp.switchToPage("main_page.fxml");
    }

    public void onAddAccountButtonPressed() throws Exception {
        mainApp.switchToPage("add_account_page.fxml");
    }

    public void updatePasswordSecurityBar() {
        double security = Math.max(passwordField.getText().length(), passwordTextField.getText().length())/20.0;
        passwordSecurityBar.setProgress(security);
    }

    public void onHidePasswordButtonPressed() {
        if (hidePasswordCheckBox.isSelected()) {
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
        } else {
            passwordField.setText(passwordTextField.getText());
            passwordTextField.setVisible(false);
        }
    }

    public void onSaveButtonPressed() {
        if (checkAllInputs()) {
            addNewAccount();

            statusLabel.setTextFill(Color.rgb(248, 182, 7));
            statusLabel.setText("Account added successfully!");

            clearAddAccountForm();
        } else {
            statusLabel.setTextFill(Color.rgb(255, 0, 0));
            statusLabel.setText("Invalid Inputs!");
        }
    }

    private boolean checkAllInputs() {
        boolean validInputs = true;

        // Check for empty inputs
        for (TextField textField : textFields) {
            if (InputValidationHandler.isEmptyTextField(textField)) {
                validInputs = false;
            }
        }

        for (ChoiceBox choiceBox : choiceBoxes) {
            if (InputValidationHandler.isEmptyChoiceBox(choiceBox)) {
                validInputs = false;
            }
        }

        // validate url
        if (!InputValidationHandler.isValidUrl(urlField, urlStatusLabel)) {
            validInputs = false;
        }

        // validate email
        if (!InputValidationHandler.isValidEmail(emailField, emailStatusLabel)) {
            validInputs = false;
        }

        return validInputs;
    }

    private void addNewAccount() {
        File csvFile = new File(Objects.requireNonNull(getClass().getResource("account_data.csv")).getFile());
        csvHandler = new CsvHandler(csvFile);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(new Date());

        Entry entry = new Entry(csvHandler.getNextIndex(), siteNameField.getText(), urlField.getText(), usernameField.getText(), emailField.getText(), passwordField.getText(), dateString, expirationOptions.getSelectionModel().getSelectedItem(), categoryOptions.getSelectionModel().getSelectedItem());
        csvHandler.saveNewEntry(entry);
    }

    private void clearAddAccountForm() {
        Arrays.stream(textFields).forEach(TextInputControl::clear);
        categoryOptions.setValue("Category");
        expirationOptions.setValue("monthly");

        passwordTextField.setText(".");
        passwordTextField.setVisible(false);
        passwordSecurityBar.setProgress(0);
        hidePasswordCheckBox.setSelected(false);
    }
}

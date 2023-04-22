package com.wasp.controller;

import com.wasp.data.AccountData;
import com.wasp.data.ExpirationEnum;
import com.wasp.handler.DataHandler;
import com.wasp.handler.ObservableListHandler;
import com.wasp.handler.CsvHandler;
import com.wasp.handler.ValidationHandler;
import com.wasp.ui.elements.ValidatingTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class AddAccountController extends BaseMainController {

    @FXML private ChoiceBox<String> categoryOptions;
    @FXML private ValidatingTextField siteNameField;
    @FXML private ValidatingTextField urlField;
    @FXML private ValidatingTextField usernameField;
    @FXML private ValidatingTextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField passwordTextField;
    @FXML private ProgressBar passwordSecurityBar;
    @FXML private CheckBox hidePasswordCheckBox;
    @FXML private ChoiceBox<String> expirationOptions;
    @FXML private Label statusLabel;

    private ValidatingTextField[] validatingTextFields;
    private ChoiceBox[] choiceBoxes;

    @Override
    public void initialize() {
        List<String> categories = new ArrayList<>(Arrays.asList("email", "social media", "gaming", "coding", "shopping", "banking", "education", "private", "other"));
        categoryOptions.setItems(ObservableListHandler.getObservableList(categories));
        categoryOptions.setValue("Category");

        List<String> expirationLabels = ExpirationEnum.getAllLabels();
        expirationOptions.setItems(ObservableListHandler.getObservableList(expirationLabels));
        expirationOptions.setValue("monthly");

        validatingTextFields = new ValidatingTextField[]{siteNameField, urlField, usernameField, emailField};
        choiceBoxes = new ChoiceBox[]{categoryOptions, expirationOptions};

        Arrays.stream(validatingTextFields).forEach(ValidatingTextField::initialize);
        setValidations();
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
            statusLabel.setTextFill(Color.rgb(248, 182, 7));
            statusLabel.setText("Account added successfully!");

            addNewAccount();
            clearAddAccountForm();
        } else {
            statusLabel.setTextFill(Color.rgb(255, 0, 0));
            statusLabel.setText("Invalid Inputs!");

            Arrays.stream(validatingTextFields).forEach(ValidatingTextField::checkInput);
        }
    }

    private void setValidations() {
        // siteName validation
        siteNameField.setValidation(ValidationHandler.SITE_NAME_VALIDATION);

        // url validation
        urlField.setValidation(ValidationHandler.URL_VALIDATION);

        // username validation
        usernameField.setValidation(ValidationHandler.USERNAME_VALIDATION);

        // email validation
        emailField.setValidation(ValidationHandler.EMAIL_VALIDATION);
    }

    private boolean checkAllInputs() {
        boolean validInputs = true;

        for (ChoiceBox choiceBox : choiceBoxes) {
            if (choiceBox.getSelectionModel().getSelectedIndex() == -1) {
                choiceBox.getStyleClass().add("common-invalid-style");
                validInputs = false;
            } else {
                choiceBox.getStyleClass().remove("common-invalid-style");
            }
        }

        if (passwordField.getText().isEmpty() && passwordTextField.getText().isEmpty()) {
            validInputs = false;
        }

        if (!Arrays.stream(validatingTextFields).allMatch(validatingTextField -> validatingTextField.getIsValidProperty().get())) {
            validInputs = false;
        }

        return validInputs;
    }

    private void addNewAccount() {
        File csvFile = new File(Objects.requireNonNull(getClass().getResource("/com/wasp/data/account_data.csv")).getFile());
        CsvHandler<AccountData> csvHandler = new CsvHandler<>(csvFile, AccountData.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(new Date());

        AccountData entry = new AccountData(
                DataHandler.getNextAccountIndex(),
                siteNameField.getText(),
                urlField.getText(),
                usernameField.getText(),
                emailField.getText(),
                passwordField.getText(),
                "",
                dateString,
                ExpirationEnum.fromLabel(expirationOptions.getSelectionModel().getSelectedItem()).getValue(),
                categoryOptions.getSelectionModel().getSelectedItem()
        );

        csvHandler.addEntry(entry);
    }

    private void clearAddAccountForm() {
        Arrays.stream(validatingTextFields).forEach(ValidatingTextField::clear);
        categoryOptions.setValue("Category");
        expirationOptions.setValue("monthly");

        passwordField.clear();
        passwordTextField.setText(".");
        passwordTextField.setVisible(false);
        passwordSecurityBar.setProgress(0);
        hidePasswordCheckBox.setSelected(false);
    }
}

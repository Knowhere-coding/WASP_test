package com.wasp.controller;

import com.wasp.data.AccountData;
import com.wasp.data.AppData;
import com.wasp.data.ExpirationEnum;
import com.wasp.handler.DataHandler;
import com.wasp.handler.ObservableListHandler;
import com.wasp.handler.CsvHandler;
import com.wasp.handler.ValidationHandler;
import com.wasp.ui.elements.CustomInputComponent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.text.SimpleDateFormat;
import java.util.*;

public class AddAccountController extends BaseMainController {

    @FXML private ChoiceBox<String> categoryOptions;
    @FXML private CustomInputComponent siteNameField;
    @FXML private CustomInputComponent urlField;
    @FXML private CustomInputComponent usernameField;
    @FXML private CustomInputComponent emailField;
    @FXML private CustomInputComponent passwordField;
    @FXML private ChoiceBox<String> expirationOptions;
    @FXML private Label statusLabel;

    private CustomInputComponent[] validatingTextFields;
    private ChoiceBox[] choiceBoxes;

    @Override
    public void initialize() {
        List<String> categories = new ArrayList<>(Arrays.asList("email", "social media", "gaming", "coding", "shopping", "banking", "education", "private", "other"));
        categoryOptions.setItems(ObservableListHandler.getObservableList(categories));
        categoryOptions.setValue("Category");

        List<String> expirationLabels = ExpirationEnum.getAllLabels();
        expirationOptions.setItems(ObservableListHandler.getObservableList(expirationLabels));
        expirationOptions.setValue("monthly");

        validatingTextFields = new CustomInputComponent[]{siteNameField, urlField, usernameField, emailField, passwordField};
        choiceBoxes = new ChoiceBox[]{categoryOptions, expirationOptions};

        Arrays.stream(validatingTextFields).forEach(CustomInputComponent::initialize);
        setValidations();
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

            Arrays.stream(validatingTextFields).forEach(CustomInputComponent::checkInput);
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

        // password validation
        passwordField.setValidation(ValidationHandler.PASSWORD_VALIDATION);
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

        if (!Arrays.stream(validatingTextFields).allMatch(validatingTextField -> validatingTextField.getIsValidProperty().get())) {
            validInputs = false;
        }

        return validInputs;
    }

    private void addNewAccount() {
        CsvHandler<AccountData> csvHandler = new CsvHandler<>(AppData.getInstance().getAccountDataFile(), AccountData.class);

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
        List<AccountData> accountDataList = csvHandler.getCsvList();
        AppData.getInstance().setAccountDataList(accountDataList);
    }

    private void clearAddAccountForm() {
        Arrays.stream(validatingTextFields).forEach(CustomInputComponent::clear);
        categoryOptions.setValue("Category");
        expirationOptions.setValue("monthly");
    }
}

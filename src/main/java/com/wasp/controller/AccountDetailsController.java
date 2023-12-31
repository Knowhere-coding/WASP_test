package com.wasp.controller;

import com.wasp.data.AccountData;
import com.wasp.data.AppData;
import com.wasp.data.ExpirationEnum;
import com.wasp.handler.CsvHandler;
import com.wasp.handler.ObservableListHandler;
import com.wasp.handler.ValidationHandler;
import com.wasp.ui.elements.AdvancedPasswordField;
import com.wasp.ui.elements.CustomInputComponent;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.Desktop;
import java.net.URI;

public class AccountDetailsController extends BaseMainController {

    @FXML private CustomInputComponent siteNameField;
    @FXML private CustomInputComponent urlField;
    @FXML private Button openUrlButton;
    @FXML private CustomInputComponent usernameField;
    @FXML private CustomInputComponent emailField;
    @FXML private CustomInputComponent passwordField;
    @FXML private Label changeDateLabel;
    @FXML private TextArea additionalInformationTextArea;

    @FXML private ChoiceBox<String> categoryOptions;
    @FXML private ChoiceBox<String> expirationOptions;

    private CustomInputComponent[] validatingTextFields;
    private ChoiceBox[] choiceBoxes;

    @FXML private Button editButton;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    @Override
    public void initialize() {
        List<String> categories = new ArrayList<>(Arrays.asList("email", "social media", "gaming", "coding", "shopping", "banking", "education", "private", "other"));
        categoryOptions.setItems(ObservableListHandler.getObservableList(categories));

        List<String> expirationLabels = ExpirationEnum.getAllLabels();
        expirationOptions.setItems(ObservableListHandler.getObservableList(expirationLabels));

        validatingTextFields = new CustomInputComponent[]{siteNameField, urlField, usernameField, emailField, passwordField};
        choiceBoxes = new ChoiceBox[]{categoryOptions, expirationOptions};

        Arrays.stream(validatingTextFields).forEach(CustomInputComponent::initialize);

        setValidations();
        setAccountDetailValues();
        ((AdvancedPasswordField)passwordField).setTextFieldVisible();
        toggleFieldDisable();
    }

    @FXML
    private void onEditButtonPressed() {
        toggleFieldDisable();
        toggleButtonVisibility();
    }

    @FXML
    private void onCancelButtonPressed() {
        setAccountDetailValues();
        toggleFieldDisable();
        toggleButtonVisibility();
        Arrays.stream(validatingTextFields).forEach(CustomInputComponent::removeStyles);
    }

    @FXML
    private void onSaveButtonPressed() {
        if (checkAllInputs()) {
            saveAccountDetailValues();
            setAccountDetailValues();
            toggleFieldDisable();
            toggleButtonVisibility();
        } else {
            Arrays.stream(validatingTextFields).forEach(CustomInputComponent::checkInput);
        }
    }

    @FXML
    private void onOpenUrlButtonPressed() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(urlField.getText()));
            } catch (IOException | URISyntaxException e) {
                openUrlButton.setDisable(true);
                openUrlButton.getStyleClass().add("open-url-button-error");
                PauseTransition errorTransition = new PauseTransition(Duration.seconds(3));
                errorTransition.setOnFinished(event -> {
                    openUrlButton.getStyleClass().remove("open-url-button-error");
                    openUrlButton.setDisable(false);
                });
                errorTransition.play();
            }
        }
    }

    private void setAccountDetailValues() {
        AccountData selectedAccount = AppData.getInstance().getSelectedAccount();

        categoryOptions.setValue(selectedAccount.getCategory());
        siteNameField.setText(selectedAccount.getSiteName());
        urlField.setText(selectedAccount.getUrl());
        usernameField.setText(selectedAccount.getUsername());
        emailField.setText(selectedAccount.getEmail());
        passwordField.setText(selectedAccount.getPassword());
        expirationOptions.setValue(ExpirationEnum.fromValue(selectedAccount.getExpiration()).getLabel());
        changeDateLabel.setText(selectedAccount.getChangeDate());
        additionalInformationTextArea.setText(selectedAccount.getAdditionalInformation());
    }

    private void saveAccountDetailValues() {
        AccountData selectedAccount = AppData.getInstance().getSelectedAccount();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = dateFormat.format(new Date());

        selectedAccount.setCategory(categoryOptions.getValue());
        selectedAccount.setSiteName(siteNameField.getText());
        selectedAccount.setUrl(urlField.getText());
        selectedAccount.setUsername(usernameField.getText());
        selectedAccount.setEmail(emailField.getText());
        selectedAccount.setPassword(passwordField.getText());
        selectedAccount.setExpiration(ExpirationEnum.fromLabel(expirationOptions.getValue()).getValue());
        selectedAccount.setChangeDate(dateString);
        selectedAccount.setAdditionalInformation(additionalInformationTextArea.getText());

        CsvHandler<AccountData> csvHandler = new CsvHandler<>(AppData.getInstance().getAccountDataFile(), AccountData.class);
        csvHandler.updateEntries(AppData.getInstance().getAccountDataList());
    }

    private void toggleFieldDisable() {

        Arrays.stream(validatingTextFields).forEach(validatingTextField -> ((Node)validatingTextField).setDisable(!((Node)validatingTextField).isDisabled()));

        Arrays.stream(choiceBoxes).forEach(choiceBox -> choiceBox.setDisable(!choiceBox.isDisabled()));
        additionalInformationTextArea.setDisable(!additionalInformationTextArea.isDisabled());
    }

    private void toggleButtonVisibility() {
        editButton.setVisible(!editButton.isVisible());
        cancelButton.setVisible(!cancelButton.isVisible());
        saveButton.setVisible(!saveButton.isVisible());
    }

    public boolean checkAllInputs() {
        return Arrays.stream(validatingTextFields).allMatch(validatingTextField -> validatingTextField.getIsValidProperty().get());
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
}

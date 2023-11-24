package com.wasp.ui.elements;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.function.Predicate;

public class AdvancedPasswordField extends VBox implements CustomInputComponent {
    private VBox container;
    private StackPane fieldContainer;
    private Label fieldLabel;
    private TextField textField;
    private PasswordField passwordField;
    private ProgressBar passwordSecurityBar;
    private CheckBox hidePasswordCheckBox;
    private Label fieldStatus;
    private Predicate<String> validation;
    private final BooleanProperty isValidProperty = new SimpleBooleanProperty();
    private boolean isChecked;
    private final StringProperty fieldLabelText = new SimpleStringProperty("");
    private final StringProperty fieldText = new SimpleStringProperty("");
    private final StringProperty fieldStatusText = new SimpleStringProperty("");
    private final StringProperty fieldLabelStyle = new SimpleStringProperty("");
    private final StringProperty fieldStyle = new SimpleStringProperty("");
    private final StringProperty fieldStatusStyle = new SimpleStringProperty("");
    private final StringProperty fieldProgressBarStyle = new SimpleStringProperty("");
    private final StringProperty fieldCheckBoxStyle = new SimpleStringProperty("");

    private final String invalidFieldStyle = "-fx-border-width: 2px; -fx-border-radius: 2px; -fx-border-color: rgba(255, 0, 0, 0.8);";
    private final String defaultFieldStyle = "-fx-border-width: 0px; -fx-border-radius: 0px; -fx-border-color: transparent;";

    public AdvancedPasswordField() {}

    public void initialize() {
        this.validation = s -> true; // default validation always returns true
        isChecked = false;
        build();
        addTextListeners();
        addFocusListener();
        updatePasswordSecurityBar();
        isValidProperty.set(validation.test(textField.getText()));
    }

    public void setTextFieldVisible() {
        updatePasswordSecurityBar();
        textField.setVisible(true);
        hidePasswordCheckBox.setSelected(true);
    }

    private void build() {
        container = new VBox();

        if (!fieldLabelText.get().isEmpty()) {
            fieldLabel = new Label();
            fieldLabel.textProperty().bind(fieldLabelText);
            fieldLabel.getStyleClass().add(fieldLabelStyle.get());
            fieldLabel.setPadding(new Insets(0, 0, 10.0, 0));
            container.getChildren().add(fieldLabel);
        }

        fieldContainer = new StackPane();

        passwordField = new PasswordField();
        passwordField.setPrefWidth(container.getPrefWidth());
        passwordField.setPrefHeight(container.getPrefHeight());
        passwordField.setPadding(new Insets(10.0, 100.0, 10.0, 10.0));
        passwordField.promptTextProperty().bind(fieldText);
        passwordField.getStyleClass().add(fieldStyle.get());
        passwordField.setStyle(defaultFieldStyle);
        passwordField.setFont(new Font(15));
        passwordField.setOnKeyTyped(event -> updatePasswordSecurityBar());
        fieldContainer.getChildren().add(passwordField);

        textField = new TextField();
        textField.setPrefWidth(container.getPrefWidth());
        textField.setPrefHeight(container.getPrefHeight());
        textField.setPadding(new Insets(10.0, 100.0, 10.0, 10.0));
        textField.promptTextProperty().bind(fieldText);
        textField.getStyleClass().add(fieldStyle.get());
        textField.setStyle(defaultFieldStyle);
        textField.setFont(new Font(15));
        textField.setVisible(false);
        textField.setOnKeyTyped(event -> updatePasswordSecurityBar());
        fieldContainer.getChildren().add(textField);

        passwordSecurityBar = new ProgressBar();
        passwordSecurityBar.setPrefWidth(95.0);
        passwordSecurityBar.setPrefHeight(20.0);
        passwordSecurityBar.setPadding(new Insets(0.0, 35.0, 0.0, 0.0));
        passwordSecurityBar.getStyleClass().add(fieldProgressBarStyle.get());
        fieldContainer.getChildren().add(passwordSecurityBar);
        StackPane.setAlignment(passwordSecurityBar, Pos.CENTER_RIGHT);

        hidePasswordCheckBox = new CheckBox();
        hidePasswordCheckBox.setPrefWidth(20.0);
        hidePasswordCheckBox.setPrefHeight(20.0);
        hidePasswordCheckBox.setPadding(new Insets(0.0, 10.0, 0.0, 0.0));
        hidePasswordCheckBox.getStyleClass().add(fieldCheckBoxStyle.get());
        hidePasswordCheckBox.setOnAction(event -> onHidePasswordButtonPressed());
        fieldContainer.getChildren().add(hidePasswordCheckBox);
        StackPane.setAlignment(hidePasswordCheckBox, Pos.CENTER_RIGHT);

        container.getChildren().addAll(fieldContainer);

        if (!fieldStatusText.get().isEmpty()) {
            fieldStatus = new Label();
            fieldStatus.textProperty().bind(fieldStatusText);
            fieldStatus.getStyleClass().add(fieldStatusStyle.get());
            fieldStatus.setPadding(new Insets(2.0, 0, 0, 0));
            fieldStatus.setVisible(false);
            container.getChildren().add(fieldStatus);
        }

        getChildren().add(container);
    }

    private void addTextListeners() {
        passwordField.textProperty().addListener((observer, oldValue, newValue) -> {
            isValidProperty.set(validation.test(newValue));
            textField.setText(newValue);
            if (isChecked) {
                setTextFieldStatusStyle();
            }
        });

        textField.textProperty().addListener((observer, oldValue, newValue) -> {
            isValidProperty.set(validation.test(newValue));
            passwordField.setText(newValue);
            if (isChecked) {
                setTextFieldStatusStyle();
            }
        });
    }

    private void addFocusListener() {
        passwordField.focusedProperty().addListener((observer, oldValue, newValue) -> {
            if (oldValue && !newValue) { // Focus lost
                setTextFieldStatusStyle();
                isChecked = true;
            }
        });

        textField.focusedProperty().addListener((observer, oldValue, newValue) -> {
            if (oldValue && !newValue) { // Focus lost
                setTextFieldStatusStyle();
                isChecked = true;
            }
        });
    }

    private void setTextFieldStatusStyle() {
        if (isValidProperty.get()) {
            if (!fieldStatusText.get().isEmpty()) {
                fieldStatus.setVisible(false);
            }
            textField.setStyle(defaultFieldStyle);
            passwordField.setStyle(defaultFieldStyle);
        } else {
            if (!fieldStatusText.get().isEmpty()) {
                fieldStatus.setVisible(true);
            }
            textField.setStyle(invalidFieldStyle);
            passwordField.setStyle(invalidFieldStyle);
        }
    }

    public void checkInput() {
        isValidProperty.set(validation.test(textField.getText()));
        setTextFieldStatusStyle();
    }

    public void removeStyles() {
        textField.setStyle(defaultFieldStyle);
        passwordField.setStyle(defaultFieldStyle);
    }

    public void setValidation(Predicate<String> validation) {
        this.validation = validation;
        isValidProperty.set(validation.test(textField.getText()));
    }

    public void updatePasswordSecurityBar() {
        double length = Math.min((getText().length()/20.0)*0.5, 0.5);
        double lowerCaseLetter = getText().matches(".*[a-z].*") ? 0.1 : 0.0;
        double upperCaseLetter = getText().matches(".*[A-Z].*") ? 0.1 : 0.0;
        double number = getText().matches(".*\\d.*") ? 0.1 : 0.0;
        double specialCharacter = getText().matches(".*[-!#$%&'()*+./:;<=>?@^_`{|}~\\]\\[].*") ? 0.2 : 0.0;
        double security = length + lowerCaseLetter + upperCaseLetter + number + specialCharacter;
        passwordSecurityBar.setProgress(security);
    }

    public void onHidePasswordButtonPressed() {
        if (hidePasswordCheckBox.isSelected()) {
            textField.setText(passwordField.getText());
            textField.setVisible(true);
        } else {
            passwordField.setText(textField.getText());
            textField.setVisible(false);
        }
    }

    public BooleanProperty getIsValidProperty() {
        return isValidProperty;
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        passwordField.setText(text);
        textField.setText(text);
    }

    public void clear() {
        isChecked = false;
        passwordField.setText("");
        textField.setText("");
        textField.setVisible(false);
        updatePasswordSecurityBar();
        hidePasswordCheckBox.setSelected(false);
    }

    public String getFieldLabelText() {
        return fieldLabelText.get();
    }

    public void setFieldLabelText(String fieldLabelText) {
        this.fieldLabelText.set(fieldLabelText);
    }

    public StringProperty fieldLabelTextProperty() {
        return fieldLabelText;
    }

    public String getFieldText() {
        return fieldText.get();
    }

    public void setFieldText(String fieldText) {
        this.fieldText.set(fieldText);
    }

    public StringProperty fieldTextProperty() {
        return fieldText;
    }

    public String getFieldStatusText() {
        return fieldStatusText.get();
    }

    public void setFieldStatusText(String fieldStatusText) {
        this.fieldStatusText.set(fieldStatusText);
    }

    public StringProperty fieldStatusTextProperty() {
        return fieldStatusText;
    }

    public String getFieldLabelStyle() {
        return fieldLabelStyle.get();
    }

    public void setFieldLabelStyle(String fieldLabelStyle) {
        this.fieldLabelStyle.set(fieldLabelStyle);
    }

    public StringProperty fieldLabelStyleProperty() {
        return fieldLabelStyle;
    }

    public String getFieldStyle() {
        return fieldStyle.get();
    }

    public void setFieldStyle(String fieldStyle) {
        this.fieldStyle.set(fieldStyle);
    }

    public StringProperty fieldStyleProperty() {
        return fieldStyle;
    }

    public String getFieldStatusStyle() {
        return fieldStatusStyle.get();
    }

    public void setFieldStatusStyle(String fieldStatusStyle) {
        this.fieldStatusStyle.set(fieldStatusStyle);
    }

    public StringProperty fieldStatusStyleProperty() {
        return fieldStatusStyle;
    }

    public String getFieldCheckBoxStyle() {
        return fieldCheckBoxStyle.get();
    }

    public void setFieldCheckBoxStyle(String fieldCheckBoxStyle) {
        this.fieldCheckBoxStyle.set(fieldCheckBoxStyle);
    }

    public StringProperty fieldCheckBoxStyleProperty() {
        return fieldCheckBoxStyle;
    }

    public String getFieldProgressBarStyle() {
        return fieldProgressBarStyle.get();
    }

    public void setFieldProgressBarStyle(String fieldProgressBarStyle) {
        this.fieldProgressBarStyle.set(fieldProgressBarStyle);
    }

    public StringProperty fieldProgressBarStyleProperty() {
        return fieldProgressBarStyle;
    }
}

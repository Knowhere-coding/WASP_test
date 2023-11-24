package com.wasp.ui.elements;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.function.Predicate;

public class ValidatingTextField extends VBox implements CustomInputComponent {
    private VBox container;
    private Label textFieldLabel;
    private TextField textField;
    private Label textFieldStatus;
    private Predicate<String> validation;
    private final BooleanProperty isValidProperty = new SimpleBooleanProperty();
    private boolean isChecked;
    private final StringProperty textFieldLabelText = new SimpleStringProperty("");
    private final StringProperty textFieldText = new SimpleStringProperty("");
    private final StringProperty textFieldStatusText = new SimpleStringProperty("");
    private final StringProperty textFieldLabelStyle = new SimpleStringProperty("");
    private final StringProperty textFieldStyle = new SimpleStringProperty("");
    private final StringProperty textFieldStatusStyle = new SimpleStringProperty("");

    private final String invalidFieldStyle = "-fx-border-width: 2px; -fx-border-radius: 2px; -fx-border-color: rgba(255, 0, 0, 0.8);";
    private final String defaultFieldStyle = "-fx-border-width: 0px; -fx-border-radius: 0px; -fx-border-color: transparent;";

    public ValidatingTextField() {}

    public void initialize() {
        this.validation = s -> true; // default validation always returns true
        isChecked = false;
        buildTextField();
        addTextListeners();
        addFocusListener();
        isValidProperty.set(validation.test(""));
    }

    private void buildTextField() {
        container = new VBox();

        if (!textFieldLabelText.get().isEmpty()) {
            textFieldLabel = new Label();
            textFieldLabel.textProperty().bind(textFieldLabelText);
            textFieldLabel.getStyleClass().add(textFieldLabelStyle.get());
            textFieldLabel.setPadding(new Insets(0, 0, 10.0, 0));
            container.getChildren().add(textFieldLabel);
        }

        textField = new TextField();
        textField.setPrefWidth(container.getPrefWidth());
        textField.setPrefHeight(container.getPrefHeight());
        textField.setPadding(new Insets(10.0));
        textField.promptTextProperty().bind(textFieldText);
        textField.getStyleClass().add(textFieldStyle.get());
        textField.setStyle(defaultFieldStyle);
        textField.setFont(new Font(15));
        container.getChildren().addAll(textField);

        if (!textFieldStatusText.get().isEmpty()) {
            textFieldStatus = new Label();
            textFieldStatus.textProperty().bind(textFieldStatusText);
            textFieldStatus.getStyleClass().add(textFieldStatusStyle.get());
            textFieldStatus.setPadding(new Insets(2.0, 0, 0, 0));
            textFieldStatus.setVisible(false);
            container.getChildren().add(textFieldStatus);
        }

        getChildren().add(container);
    }

    private void addTextListeners() {
        textField.textProperty().addListener((observer, oldValue, newValue) -> {
            isValidProperty.set(validation.test(newValue));
            if (isChecked) {
                setTextFieldStatusStyle();
            }
        });
    }

    private void addFocusListener() {
        textField.focusedProperty().addListener((observer, oldValue, newValue) -> {
            if (oldValue && !newValue) { // Focus lost
                setTextFieldStatusStyle();
                isChecked = true;
            }
        });
    }

    private void setTextFieldStatusStyle() {
        if (isValidProperty.get()) {
            if (!textFieldStatusText.get().isEmpty()) {
                textFieldStatus.setVisible(false);
            }
            textField.setStyle(defaultFieldStyle);
        } else {
            if (!textFieldStatusText.get().isEmpty()) {
                textFieldStatus.setVisible(true);
            }
            textField.setStyle(invalidFieldStyle);
        }
    }

    public void checkInput() {
        isValidProperty.set(validation.test(textField.getText()));
        setTextFieldStatusStyle();
    }

    public void removeStyles() {
        textField.setStyle(defaultFieldStyle);
    }

    public void setValidation(Predicate<String> validation) {
        this.validation = validation;
        isValidProperty.set(validation.test(textField.getText()));
    }

    public BooleanProperty getIsValidProperty() {
        return isValidProperty;
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public void clear() {
        isChecked = false;
        textField.setText("");
        textField.setStyle(defaultFieldStyle);
    }

    public String getTextFieldLabelText() {
        return textFieldLabelText.get();
    }

    public void setTextFieldLabelText(String textFieldLabelText) {
        this.textFieldLabelText.set(textFieldLabelText);
    }

    public StringProperty textFieldLabelTextProperty() {
        return textFieldLabelText;
    }

    public String getTextFieldText() {
        return textFieldText.get();
    }

    public void setTextFieldText(String textFieldText) {
        this.textFieldText.set(textFieldText);
    }

    public StringProperty textFieldTextProperty() {
        return textFieldText;
    }

    public String getTextFieldStatusText() {
        return textFieldStatusText.get();
    }

    public void setTextFieldStatusText(String textFieldStatusText) {
        this.textFieldStatusText.set(textFieldStatusText);
    }

    public StringProperty textFieldStatusTextProperty() {
        return textFieldStatusText;
    }

    public String getTextFieldLabelStyle() {
        return textFieldLabelStyle.get();
    }

    public void setTextFieldLabelStyle(String textFieldLabelStyle) {
        this.textFieldLabelStyle.set(textFieldLabelStyle);
    }

    public StringProperty textFieldLabelStyleProperty() {
        return textFieldLabelStyle;
    }

    public String getTextFieldStyle() {
        return textFieldStyle.get();
    }

    public void setTextFieldStyle(String textFieldStyle) {
        this.textFieldStyle.set(textFieldStyle);
    }

    public StringProperty textFieldStyleProperty() {
        return textFieldStyle;
    }

    public String getTextFieldStatusStyle() {
        return textFieldStatusStyle.get();
    }

    public void setTextFieldStatusStyle(String textFieldStatusStyle) {
        this.textFieldStatusStyle.set(textFieldStatusStyle);
    }

    public StringProperty textFieldStatusStyleProperty() {
        return textFieldStatusStyle;
    }
}
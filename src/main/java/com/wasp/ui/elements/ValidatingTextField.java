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

public class ValidatingTextField extends VBox {
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

    private final String invalidFieldStyle = "-fx-border-width: 2px; -fx-border-radius: 2px; -fx-border-color: rgba(255, 0, 0, 0.8);";
    private final String defaultFieldStyle = "-fx-border-width: 0px; -fx-border-radius: 0px; -fx-border-color: transparent;";

    public ValidatingTextField() {
        this.validation = s -> true; // default validation always returns true
        isChecked = false;
        buildTextField();
        addTextListeners();
        addFocusListener();
        isValidProperty.set(validation.test(""));
    }

    private void buildTextField() {
        container = new VBox();

        textFieldLabel = new Label();
        textFieldLabel.textProperty().bind(textFieldLabelText);
        textFieldLabel.getStyleClass().add("common-label");
        textFieldLabel.setPadding(new Insets(0, 0, 10.0, 0));

        textField = new TextField();
        textField.setPrefWidth(container.getPrefWidth());
        textField.setPrefHeight(container.getPrefHeight());
        textField.setPadding(new Insets(10.0));

        textField.promptTextProperty().bind(textFieldText);
        textField.getStyleClass().add("common-text-field");
        textField.setStyle(defaultFieldStyle);
        textField.setFont(new Font(15));

        textFieldStatus = new Label();
        textFieldStatus.textProperty().bind(textFieldStatusText);
        textFieldStatus.getStyleClass().add("common-status-label");
        textFieldStatus.setPadding(new Insets(2.0, 0, 0, 0));
        textFieldStatus.setVisible(false);

        container.getChildren().addAll(textFieldLabel, textField, textFieldStatus);

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
            textField.setStyle(defaultFieldStyle);
            textFieldStatus.setVisible(false);
        } else {
            textField.setStyle(invalidFieldStyle);
            textFieldStatus.setVisible(true);
        }
    }

    public void checkInput() {
        isValidProperty.set(validation.test(textField.getText()));
        setTextFieldStatusStyle();
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
}
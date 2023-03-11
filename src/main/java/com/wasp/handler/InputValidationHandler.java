package com.wasp.handler;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InputValidationHandler {
    public static final String invalidFieldStyle = "-fx-border-width: 2px; -fx-border-radius: 2px; -fx-border-color: rgba(255, 0, 0, 0.8);";
    public static final String defaultFieldStyle = "-fx-border-width: 0px; -fx-border-radius: 0px; -fx-border-color: transparent;";

    public static boolean isEmptyTextField(TextField textField) {
        if (textField.getText().isEmpty()) {
            textField.setStyle(invalidFieldStyle);
            return true;
        } else {
            textField.setStyle(defaultFieldStyle);
            return false;
        }
    }

    public static boolean isEmptyChoiceBox(ChoiceBox<Object> choiceBox) {
        if (choiceBox.getSelectionModel().getSelectedIndex() == -1) {
            choiceBox.setStyle(invalidFieldStyle);
            return true;
        } else {
            choiceBox.setStyle(defaultFieldStyle);
            return false;
        }
    }

    public static boolean isValidUrl(TextField urlField, Label urlStatusLabel) {
        String url = urlField.getText();

        if (!url.isEmpty()) {
            if (!url.startsWith("http://") & !url.startsWith("https://")) {
                url = "https://" + url;
                urlField.setText(url);
            }
        }

        Pattern pattern = Pattern.compile("^(https?://)?([a-zA-Z0-9]+([\\-\\.][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,63}(:[0-9]{1,5})?)(/[a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&amp;%\\$#\\=~]*)?$");
        Matcher matcher = pattern.matcher(url);

        if (matcher.matches()) {
            urlField.setStyle(defaultFieldStyle);
            urlStatusLabel.setText("");
            return true;
        } else {
            urlField.setStyle(invalidFieldStyle);
            urlStatusLabel.setText("Invalid email!");
            return false;
        }
    }

    public static boolean isValidEmail(TextField emailField, Label emailStatusLabel) {
        String email = emailField.getText();

        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            emailField.setStyle(defaultFieldStyle);
            emailStatusLabel.setText("");
            return true;
        } else {
            emailField.setStyle(invalidFieldStyle);
            emailStatusLabel.setText("Invalid email!");
            return false;
        }
    }
}

package com.wasp.ui.elements;

import javafx.beans.property.BooleanProperty;

import java.util.function.Predicate;

public interface CustomInputComponent {

    void initialize();
    void clear();
    void checkInput();
    void setValidation(Predicate<String> validation);
    String getText();
    void setText(String text);
    void removeStyles();
    BooleanProperty getIsValidProperty();
}

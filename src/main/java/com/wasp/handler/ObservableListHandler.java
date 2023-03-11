package com.wasp.handler;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;

public class ObservableListHandler {
    public static <T> ObservableList<T> getObservableList(List<T> entries) {
        ObservableList<T> observableList = FXCollections.observableArrayList();
        observableList.addAll(entries);
        return observableList;
    }
}

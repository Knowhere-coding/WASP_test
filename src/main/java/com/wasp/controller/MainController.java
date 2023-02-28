package com.wasp.controller;

import com.wasp.data.Entry;
import com.wasp.handler.CsvHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class MainController extends BaseController {

    @FXML private Button logoutButton;
    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> searchOptions;
    @FXML private TableView<com.wasp.data.Entry> accountTable;

    private CsvHandler csvHandler;

    @Override
    public void initialize() {
        File csvFile = new File(Objects.requireNonNull(getClass().getResource("account_data.csv")).getFile());
        csvHandler = new CsvHandler(csvFile);

        searchOptions.setItems(csvHandler.getObservableSearchOptionsList(csvHandler.getSearchOptions()));
        searchOptions.setValue("all");

        accountTable.setItems(csvHandler.getObservableTableList(csvHandler.getCsvList()));
    }

    public void onLogoutButtonPressed(ActionEvent event) throws Exception {
        mainApp.switchToLoginPage();
    }

    public void getSearchOutput() {
        int option = searchOptions.getSelectionModel().getSelectedIndex();
        List<Entry> entries = csvHandler.getCsvList(searchField.getCharacters().toString(), option);
        accountTable.setItems(csvHandler.getObservableTableList(entries));
    }
}

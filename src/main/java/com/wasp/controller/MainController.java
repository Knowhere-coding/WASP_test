package com.wasp.controller;

import com.wasp.handler.CsvHandler;
import com.wasp.handler.ObservableListHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class MainController extends BaseController {

    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> searchOptions;
    @FXML private TableView<com.wasp.data.Entry> accountTable;

    private CsvHandler csvHandler;

    @Override
    public void initialize() {
        File csvFile = new File(Objects.requireNonNull(getClass().getResource("account_data.csv")).getFile());
        csvHandler = new CsvHandler(csvFile);

        List<String> searchOptionsList = csvHandler.getHeader();
        searchOptionsList.add(0, "all");

        searchOptions.setItems(ObservableListHandler.getObservableList(searchOptionsList));
        searchOptions.setValue("all");

        accountTable.setItems(ObservableListHandler.getObservableList(csvHandler.getCsvList()));
    }

    public void onLogoutButtonPressed() throws Exception {
        mainApp.switchToPage("login_page.fxml");
    }

    public void onHomeButtonPressed() throws Exception {
        mainApp.switchToPage("main_page.fxml");
    }

    public void onAddAccountButtonPressed() throws Exception {
        mainApp.switchToPage("add_account_page.fxml");
    }

    public void getSearchOutput() {
        int option = searchOptions.getSelectionModel().getSelectedIndex();
        accountTable.setItems(ObservableListHandler.getObservableList(csvHandler.getCsvList(searchField.getText(), option, true)));
    }
}

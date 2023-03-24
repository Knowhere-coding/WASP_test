package com.wasp.controller;

import com.wasp.data.AccountData;
import com.wasp.data.AppData;
import com.wasp.handler.ObservableListHandler;
import com.wasp.handler.CsvHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class MainController extends BaseController {

    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> searchOptions;
    @FXML private TableView<AccountData> accountTable;

    private CsvHandler<AccountData> csvHandler;

    @Override
    public void initialize() {
        File csvFile = new File(Objects.requireNonNull(getClass().getResource("/com/wasp/data/account_data.csv")).getFile());
        csvHandler = new CsvHandler<>(csvFile, AccountData.class);

        List<String> searchOptionsList = csvHandler.getCsvHeader();
        searchOptionsList.add(0, "all");

        searchOptions.setItems(ObservableListHandler.getObservableList(searchOptionsList));
        searchOptions.setValue("all");

        accountTable.setItems(ObservableListHandler.getObservableList(csvHandler.getTableEntries()));
    }

    public void onLogoutButtonPressed() {
        mainApp.switchToPage("login_page.fxml");
    }

    public void onHomeButtonPressed() {
        mainApp.switchToPage("main_page.fxml");
    }

    public void onAddAccountButtonPressed() {
        mainApp.switchToPage("add_account_page.fxml");
    }

    public void getSearchOutput() {
        int option = searchOptions.getSelectionModel().getSelectedIndex();
        accountTable.setItems(ObservableListHandler.getObservableList(csvHandler.getTableEntries(searchField.getText(), option)));
    }

    public void onAccountSelected(MouseEvent event) {
        if (event.getClickCount() == 2) {
            AccountData account = accountTable.getSelectionModel().getSelectedItem();
            if (account != null) {
                AppData.getInstance().setSelectedAccount(account);
                mainApp.switchToPage("account_details_page.fxml");
            }
        }
    }
}
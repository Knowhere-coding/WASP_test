package com.wasp.controller;

import com.wasp.data.AccountData;
import com.wasp.data.AppData;
import com.wasp.handler.ObservableListHandler;
import com.wasp.handler.CsvHandler;
import com.wasp.handler.DataHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumnBase;
import javafx.scene.control.TableView;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainController extends BaseMainController {

    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> searchOptions;
    @FXML private TableView<AccountData> accountTable;

    @Override
    public void initialize() {
        if (AppData.getInstance().getAccountDataList() == null) {
            File csvFile = new File(Objects.requireNonNull(getClass().getResource("/com/wasp/data/account_data.csv")).getFile());
            CsvHandler<AccountData> csvHandler = new CsvHandler<>(csvFile, AccountData.class);
            List<AccountData> accountDataList = csvHandler.getCsvList();
            AppData.getInstance().setAccountDataList(accountDataList);
        }

        List<String> searchOptionsList = new ArrayList<>(accountTable.getColumns().stream().map(TableColumnBase::getText).toList());
        searchOptionsList.add(0, "all");
        searchOptions.setItems(ObservableListHandler.getObservableList(searchOptionsList));
        searchOptions.setValue("all");

        accountTable.setItems(ObservableListHandler.getObservableList(DataHandler.getTableEntries()));
    }

    @FXML
    private void getSearchOutput() {
        String option = "all".equals(searchOptions.getSelectionModel().getSelectedItem()) ? "" : searchOptions.getSelectionModel().getSelectedItem();
        accountTable.setItems(ObservableListHandler.getObservableList(DataHandler.getTableEntries(searchField.getText(), option)));
    }

    @FXML
    private void onAccountSelected(MouseEvent event) {
        if (event.getClickCount() == 2) {
            AccountData account = accountTable.getSelectionModel().getSelectedItem();
            if (account != null) {
                account.unhidePassword();
                AppData.getInstance().setSelectedAccount(account);
                mainApp.switchToPage("account_details_page.fxml");
            }
        }
    }
}
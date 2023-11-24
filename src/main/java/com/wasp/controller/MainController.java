package com.wasp.controller;

import com.wasp.data.AccountData;
import com.wasp.data.AppData;
import com.wasp.handler.ObservableListHandler;
import com.wasp.handler.CsvHandler;
import com.wasp.handler.DataHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class MainController extends BaseMainController {

    @FXML private TextField searchField;
    @FXML private ChoiceBox<String> searchOptions;
    @FXML private TableView<AccountData> accountTable;
    @FXML private TableColumn<AccountData, String> passwordTableColumn;

    @Override
    public void initialize() {
        if (AppData.getInstance().getAccountDataList() == null) {
            CsvHandler<AccountData> csvHandler = new CsvHandler<>(AppData.getInstance().getAccountDataFile(), AccountData.class);
            List<AccountData> accountDataList = csvHandler.getCsvList();
            AppData.getInstance().setAccountDataList(accountDataList);
        }

        List<String> searchOptionsList = new ArrayList<>(accountTable.getColumns().stream().map(TableColumnBase::getText).toList());
        searchOptionsList.add(0, "all");
        searchOptions.setItems(ObservableListHandler.getObservableList(searchOptionsList));
        searchOptions.setValue("all");

        passwordTableColumn.setCellValueFactory(new PropertyValueFactory<>("hiddenPassword"));
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
                AppData.getInstance().setSelectedAccount(account);
                mainApp.switchToPage("account_details_page.fxml");
            }
        }
    }
}
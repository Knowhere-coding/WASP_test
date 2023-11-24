package com.wasp.data;

import com.wasp.handler.InactivityEventHandler;
import com.wasp.handler.InactivityTimer;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class AppData {
    private static final AppData INSTANCE = new AppData();
    private File masterAccountDataFile;
    private File accountDataFile;
    private File backupDirectory;
    private String username;
    private String password;
    private List<AccountData> accountDataList;
    private AccountData selectedAccount;
    private int inactivityPeriod;
    private InactivityTimer inactivityTimer;
    private InactivityEventHandler inactivityEventHandler;
    private boolean isInactive;
    private Stage stage;
    private Parent root;

    private AppData() {}

    public static AppData getInstance() {
        return INSTANCE;
    }

    public File getMasterAccountDataFile() {
        return masterAccountDataFile;
    }

    public void setMasterAccountDataFile(File masterAccountDataFile) {
        this.masterAccountDataFile = masterAccountDataFile;
    }

    public File getAccountDataFile() {
        return accountDataFile;
    }

    public void setAccountDataFile(File accountDataFile) {
        this.accountDataFile = accountDataFile;
    }

    public File getBackupDirectory() {
        return backupDirectory;
    }

    public void setBackupDirectory(File backupDirectory) {
        this.backupDirectory = backupDirectory;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<AccountData> getAccountDataList() {
        return accountDataList;
    }

    public void setAccountDataList(List<AccountData> accountDataList) {
        this.accountDataList = accountDataList;
    }

    public AccountData getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(AccountData selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public int getInactivityPeriod() {
        return inactivityPeriod;
    }

    public void setInactivityPeriod(int inactivityPeriod) {
        this.inactivityPeriod = inactivityPeriod;
    }

    public InactivityTimer getInactivityTimer() {
        return inactivityTimer;
    }

    public void setInactivityTimer(InactivityTimer inactivityTimer) {
        this.inactivityTimer = inactivityTimer;
    }

    public InactivityEventHandler getInactivityEventHandler() {
        return inactivityEventHandler;
    }

    public void setInactivityEventHandler(InactivityEventHandler inactivityEventHandler) {
        this.inactivityEventHandler = inactivityEventHandler;
    }

    public boolean isInactive() {
        return isInactive;
    }

    public void setInactive(boolean inactive) {
        isInactive = inactive;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
}

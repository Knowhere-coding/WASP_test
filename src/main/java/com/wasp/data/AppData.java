package com.wasp.data;

import com.wasp.handler.InactivityEventHandler;
import com.wasp.handler.InactivityTimer;
import javafx.scene.Parent;

public class AppData {
    private static final AppData INSTANCE = new AppData();
    private String username;
    private String password;
    private AccountData selectedAccount;
    private InactivityTimer inactivityTimer;
    private InactivityEventHandler inactivityEventHandler;
    private boolean isInactive;
    private Parent root;

    private AppData() {}

    public static AppData getInstance() {
        return INSTANCE;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AccountData getSelectedAccount() {
        return selectedAccount;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSelectedAccount(AccountData selectedAccount) {
        this.selectedAccount = selectedAccount;
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

    public Parent getRoot() {
        return root;
    }

    public void setRoot(Parent root) {
        this.root = root;
    }
}

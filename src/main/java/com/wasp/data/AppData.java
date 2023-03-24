package com.wasp.data;

public class AppData {
    private static final AppData instance = new AppData();
    private String username;
    private String password;
    private AccountData selectedAccount;

    private AppData() {}

    public static AppData getInstance() {
        return instance;
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
}

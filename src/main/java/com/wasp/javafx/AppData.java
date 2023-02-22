package com.wasp.javafx;

public class AppData {
    private static final AppData instance = new AppData();
    private String username;
    private String password;

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

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

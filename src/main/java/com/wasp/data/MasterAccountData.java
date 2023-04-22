package com.wasp.data;

import java.util.HashMap;
import java.util.Map;

public class MasterAccountData implements BaseData {
    private String masterUsername;
    private String masterPassword;
    private String textMasterPassword;
    private String hiddenMasterPassword;

    public MasterAccountData() {}

    public MasterAccountData(String masterUsername, String masterPassword) {
        this.masterUsername = masterUsername;

        this.masterPassword = masterPassword;
        this.textMasterPassword = masterPassword;
        this.hiddenMasterPassword = masterPassword.replaceAll(".", "*");
    }

    public String getMasterUsername() {
        return masterUsername;
    }

    public void setMasterUsername(String masterUsername) {
        this.masterUsername = masterUsername;
    }

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
        this.textMasterPassword = masterPassword;
        this.hiddenMasterPassword = masterPassword.replaceAll(".", "*");
    }

    @Override
    public String toString() {
        return "MasterAccountData{" +
                "username='" + masterUsername + '\'' +
                ", password='" + masterPassword + '\'' +
                '}';
    }

    @Override
    public String[] getValues() {
        return new String[]{masterUsername, masterPassword};
    }

    @Override
    public Map<String, String> getMappedValues() {
        return new HashMap<>() {{
            put("username", masterUsername);
            put("password", masterPassword);
        }};
    }

    @Override
    public void hidePassword() {
        this.masterPassword = hiddenMasterPassword;
    }

    @Override
    public void unhidePassword() {
        this.masterPassword = textMasterPassword;
    }
}

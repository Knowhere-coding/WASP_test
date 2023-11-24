package com.wasp.data;

import java.util.HashMap;
import java.util.Map;

public class MasterAccountData implements BaseData {
    private String masterUsername;
    private String masterPassword;

    public MasterAccountData() {}

    public MasterAccountData(String masterUsername, String masterPassword) {
        this.masterUsername = masterUsername;

        this.masterPassword = masterPassword;
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
}

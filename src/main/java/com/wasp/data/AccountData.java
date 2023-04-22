package com.wasp.data;

import java.util.HashMap;
import java.util.Map;

public class AccountData implements BaseData {
    private int id;
    private String siteName;
    private String url;
    private String username;
    private String email;
    private String password;
    private String textPassword;
    private String hiddenPassword;
    private String changeDate;
    private int expiration;
    private String category;
    private String additionalInformation;

    public AccountData() {}

    public AccountData(int id, String siteName, String url, String username, String email, String password, String additionalInformation, String changeDate, int expiration, String category) {
        this.id = id;
        this.siteName = siteName;
        this.url = url;
        this.username = username;
        this.email = email;

        this.password = password;
        this.textPassword = password;
        this.hiddenPassword = password.replaceAll(".", "*");

        this.additionalInformation = additionalInformation;

        this.changeDate = changeDate;
        this.expiration = expiration;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.textPassword = password;
        this.hiddenPassword = password.replaceAll(".", "*");
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public int getExpiration() {
        return expiration;
    }

    public void setExpiration(int expiration) {
        this.expiration = expiration;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Override
    public String toString() {
        return "OpenCsvEntry{" +
                "id=" + id +
                ", siteName='" + siteName + '\'' +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", additionalInformation='" + additionalInformation + '\'' +
                ", changeDate='" + changeDate + '\'' +
                ", expiration=" + expiration +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public String[] getValues() {
        return new String[]{String.valueOf(id), siteName, url, username, email, password, additionalInformation, changeDate, String.valueOf(expiration), category};
    }

    @Override
    public Map<String, String> getMappedValues() {
        return new HashMap<>(){{
            put("ID", String.valueOf(id));
            put("siteName", siteName);
            put("url", url);
            put("username", username);
            put("email", email);
            put("password", password);
            put("additionalInformation", additionalInformation);
            put("changeDate", changeDate);
            put("expiration", String.valueOf(expiration));
            put("category", category);
        }};
    }

    @Override
    public void hidePassword() {
        this.password = hiddenPassword;
    }

    @Override
    public void unhidePassword() {
        this.password = textPassword;
    }
}

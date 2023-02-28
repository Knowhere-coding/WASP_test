package com.wasp.data;

public class Entry {
    private int id;
    private String siteName;
    private String url;
    private String username;
    private String email;
    private String password;
    private String changeDate;
    private int expiration;
    private String category;

    public Entry(String[] elements) {
        this.id = Integer.parseInt(elements[0]);
        this.siteName = elements[1];
        this.url = elements[2];
        this.username = elements[3];
        this.email = elements[4];
        this.password = elements[5];
        this.changeDate = elements[6];
        this.expiration = Integer.parseInt(elements[7]);
        this.category = elements[8];
    }

    public Entry(int id, String siteName, String url, String username, String email, String password, String changeDate, int expiration, String category) {
        this.id = id;
        this.siteName = siteName;
        this.url = url;
        this.username = username;
        this.email = email;
        this.password = password;
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

    @Override
    public String toString() {
        return "id: " + id + "; siteName: " + siteName + "; url: " + url + "; username" + username + "; email" + email + "; password" + password + "; changeDate" + changeDate + "; expiration" + expiration + "; category" + category;
    }
}

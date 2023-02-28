package com.wasp.controller;

import com.wasp.Main;

public class BaseController {

    protected Main mainApp;

    public void initialize() {}

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
}

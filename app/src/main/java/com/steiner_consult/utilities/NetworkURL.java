package com.steiner_consult.utilities;

/**
 * Created by Philipp on 04.02.15.
 */
public enum NetworkURL {

    REGISTERACCOUNT("account/", "register/", "Register the AppUser"),
    LOGIN("account/", "login/", "Login the AppUser"),
    PRAYER_CREATE("prayer/", "create/", "Create a Prayer"),
    PRAYER_LIST("prayer/", "prayers/", "Get Prayers by Creator");

    private String path;
    private String description;
    private String protokol = "https://";
    private String baseDomain = "asktoagree.herokuapp.com/";
    private String controller;

    NetworkURL(String controller, String path, String description) {
        this.controller = controller;
        this.path = path;
        this.description = description;
    }

    public String getUrl() {
        return protokol + baseDomain + controller + path;
    }
}

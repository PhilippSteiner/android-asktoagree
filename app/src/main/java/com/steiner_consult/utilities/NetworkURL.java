package com.steiner_consult.utilities;

/**
 * Created by Philipp on 04.02.15.
 */
public enum NetworkURL {

    REGISTERACCOUNT("account/", "register/", "Register the AppUser");

    private String path;
    private String description;
    private String protokoll = "https://";
    private String baseDomain = "asktoagree.herokuapp.com/";
    private String controller;

    NetworkURL(String controller, String path, String description) {
        this.controller = controller;
        this.path = path;
        this.description = description;
    }

    public String getUrl() {
        return protokoll + baseDomain + controller + path;
    }
}

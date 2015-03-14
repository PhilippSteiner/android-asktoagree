package com.steiner_consult.utilities;

/**
 * Created by Philipp on 04.02.15.
 */
public enum NetworkURL {

    REGISTER_ACCOUNT("account/", "register/", "Register the AppUser"),
    LOGIN("account/", "login/", "Login the AppUser"),
    LOGOUT("account/", "logout/", "Logout the AppUser"),
    PRAYER_CREATE("prayer/", "create/", "Create a Prayer"),
    PRAYER_LIST("prayer/", "prayers/", "Get Prayers by Creator"),
    USER_LIST("friend/", "users/", "Get all other Users"),
    FRIEND_INVITE("friend/", "invite/", "Invite a User"),
    FRIEND_REQUEST("friend/", "requests/", "Get the users requests"),
    FRIEND_RESPONSE("friend/", "responds/", "Get the users responds"),
    FRIENDS_LIST("friend/", "friends/", "Get all Friends"),
    FRIEND_CONFIRM("friend/", "confirm/", "Confirm FriendRequest"),
    USER_SEARCH("friend/", "search/", "Search for existing users");

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

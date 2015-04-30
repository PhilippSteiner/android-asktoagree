package com.steiner_consult.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Philipp on 12.03.15.
 */
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class AppUser {

    private Long id;
    @JsonIgnore
    private String authToken;
    @JsonIgnore
    private boolean isPrayerSharedWith;
    private String email;
    private String password;
    private Date creationDate;
    private String firstname;
    private String lastname;
    private String username;
    private String clienttoken;
    private ArrayList<Prayer> prayers = new ArrayList<>();
    private ArrayList<AppUser> friendRequest = new ArrayList<>();
    private ArrayList<AppUser> friendResponse = new ArrayList<>();
    private ArrayList<Prayer> sharedprayers = new ArrayList<>();
    private ArrayList<Prayer> agreedprayers = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getClienttoken() {
        return clienttoken;
    }

    public void setClienttoken(String clienttoken) {
        this.clienttoken = clienttoken;
    }

    public ArrayList<Prayer> getPrayers() {
        return prayers;
    }

    public void setPrayers(ArrayList<Prayer> prayers) {
        this.prayers = prayers;
    }

    public ArrayList<AppUser> getFriendRequest() {
        return friendRequest;
    }

    public void setFriendRequest(ArrayList<AppUser> friendRequest) {
        this.friendRequest = friendRequest;
    }

    public ArrayList<AppUser> getFriendResponse() {
        return friendResponse;
    }

    public void setFriendResponse(ArrayList<AppUser> friendResponse) {
        this.friendResponse = friendResponse;
    }

    public ArrayList<Prayer> getSharedprayers() {
        return sharedprayers;
    }

    public void setSharedprayers(ArrayList<Prayer> sharedprayers) {
        this.sharedprayers = sharedprayers;
    }

    public ArrayList<Prayer> getAgreedprayers() {
        return agreedprayers;
    }

    public void setAgreedprayers(ArrayList<Prayer> agreedprayers) {
        this.agreedprayers = agreedprayers;
    }
    @JsonIgnore
    public boolean isPrayerSharedWith() {
        return isPrayerSharedWith;
    }

    public void setPrayerSharedWith(boolean isPrayerSharedWith) {
        this.isPrayerSharedWith = isPrayerSharedWith;
    }
}

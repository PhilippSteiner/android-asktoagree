package com.steiner_consult.models.requests;

import com.steiner_consult.utilities.AppConfig;

/**
 * Created by Philipp on 16.12.14.
 */
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String clienttoken;


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

    public String getClienttoken() {
        return clienttoken;
    }

    public void setClienttoken(String clienttoken) {
        this.clienttoken = clienttoken;
    }
}

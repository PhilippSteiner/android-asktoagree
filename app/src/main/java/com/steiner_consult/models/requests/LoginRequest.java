package com.steiner_consult.models.requests;

/**
 * Created by Philipp on 05.02.15.
 */
public class LoginRequest {

    private String email;
    private String password;
    private String clienttoken;

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

    public String getClienttoken() {
        return clienttoken;
    }

    public void setClienttoken(String clienttoken) {
        this.clienttoken = clienttoken;
    }
}

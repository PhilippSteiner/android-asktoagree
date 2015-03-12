package com.steiner_consult.models.responses;

import com.steiner_consult.models.AppUser;

import java.util.ArrayList;

/**
 * Created by Philipp on 12.03.15.
 */
public class UsersResponse extends BaseResponse {

    private ArrayList<AppUser> users = new ArrayList<>();

    public ArrayList<AppUser> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<AppUser> users) {
        this.users = users;
    }
}

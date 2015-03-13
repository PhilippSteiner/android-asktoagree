package com.steiner_consult.interfaces;

import com.steiner_consult.models.AppUser;

import java.util.ArrayList;

/**
 * Created by Philipp on 13.03.15.
 */
public interface FriendFragment {

    public void setListAdapterData(ArrayList<AppUser> appUsers);
}

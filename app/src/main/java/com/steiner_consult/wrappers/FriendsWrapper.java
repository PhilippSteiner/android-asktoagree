package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.AppUser;

/**
 * Created by Philipp on 13.03.15.
 */
public class FriendsWrapper {

    private TextView username;

    public FriendsWrapper(View row) {
        username = (TextView) row.findViewById(R.id.username);
    }

    public void setData(AppUser appUser) {
        username.setText(appUser.getUsername());
    }
}

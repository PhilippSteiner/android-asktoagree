package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.AppUser;

/**
 * Created by Philipp on 17.03.15.
 */
public class ShareFriendWrapper {

    private TextView username;
    private CheckBox sharePrayer;

    public ShareFriendWrapper(View row) {
        username = (TextView) row.findViewById(R.id.username);
        sharePrayer = (CheckBox) row.findViewById(R.id.checkbox_share);
    }

    public void setData(AppUser appUser) {
        username.setText(appUser.getUsername());
    }
}

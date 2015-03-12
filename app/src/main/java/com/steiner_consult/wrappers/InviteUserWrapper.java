package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.TextView;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.AppUser;


/**
 * Created by Philipp on 12.03.15.
 */
public class InviteUserWrapper {

    private TextView username;
    private AppUser appUser;

    public InviteUserWrapper(View row, BaseActivity baseActivity) {
        username = (TextView) row.findViewById(R.id.username);

    }

    public void setData(AppUser appUser) {
        this.appUser = appUser;
        setUsername();
    }

    private void setUsername() {
        username.setText(appUser.getUsername());
    }
}

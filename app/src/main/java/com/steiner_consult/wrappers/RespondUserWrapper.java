package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.fragments.RespondFragment;
import com.steiner_consult.models.AppUser;

/**
 * Created by Philipp on 13.03.15.
 */
public class RespondUserWrapper {

    private TextView username;
    private Button accept;
    private RespondFragment respondFragment;
    private AppUser appUser;

    public RespondUserWrapper(View row, RespondFragment respondFragment) {
        this.respondFragment = respondFragment;
        username = (TextView) row.findViewById(R.id.username);
        accept = (Button) row.findViewById(R.id.button_accept);
    }

    public void setData(AppUser appUser) {
        this.appUser = appUser;
        setUsername();
        setButton();
    }

    private void setUsername() {
        username.setText(appUser.getUsername());
    }

    private void setButton() {
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accept.setText(respondFragment.getString(R.string.button_accepted));
                accept.setEnabled(false);
                respondFragment.confirmFriendship(appUser);
            }
        });
    }


}

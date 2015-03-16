package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.fragments.InviteFragment;
import com.steiner_consult.fragments.SearchFragment;
import com.steiner_consult.models.AppUser;

/**
 * Created by Philipp on 16.03.15.
 */
public class SearchUserWrapper implements View.OnClickListener {

    private TextView username;
    private AppUser appUser;
    private SearchFragment searchFragment;

    public SearchUserWrapper(View row, SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
        username = (TextView) row.findViewById(R.id.username);
        Button invite = (Button) row.findViewById(R.id.button_invite);
        invite.setOnClickListener(this);
    }

    public void setData(AppUser appUser) {
        this.appUser = appUser;
        setUsername();
    }

    private void setUsername() {
        username.setText(appUser.getUsername());
    }

    @Override
    public void onClick(View v) {
        searchFragment.sendInvite(appUser);
    }
}

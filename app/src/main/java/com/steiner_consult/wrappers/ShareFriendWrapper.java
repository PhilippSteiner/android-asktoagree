package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.fragments.CreatePrayerFragment;
import com.steiner_consult.models.AppUser;

/**
 * Created by Philipp on 17.03.15.
 */
public class ShareFriendWrapper {

    private TextView username;
    private CheckBox sharePrayer;
    private CreatePrayerFragment createPrayerFragment;

    public ShareFriendWrapper(View row, CreatePrayerFragment createPrayerFragment) {
        this.createPrayerFragment = createPrayerFragment;
        username = (TextView) row.findViewById(R.id.username);
        sharePrayer = (CheckBox) row.findViewById(R.id.checkbox_share);
    }

    public void setData(final AppUser appUser, final int position) {
        username.setText(appUser.getUsername());
        sharePrayer.setChecked(appUser.isPrayerSharedWith());
        sharePrayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPrayerFragment.handleCheckboxClick(position, sharePrayer.isChecked());
            }
        });
    }
}

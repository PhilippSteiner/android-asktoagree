package com.steiner_consult.asktoagree;

import android.view.MenuItem;

import com.steiner_consult.fragments.BaseFragment;
import com.steiner_consult.fragments.CreatePrayerFragment;

/**
 * Created by Philipp on 31.01.15.
 */
public class OptionsMenuHandler {

    private static OptionsMenuHandler instance;

    private OptionsMenuHandler() {}

    public static OptionsMenuHandler getInstance() {
        if (instance == null) {
            instance = new OptionsMenuHandler();
        }
        return instance;
    }

    public boolean handleClick(BaseFragment baseFragment, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create:
                baseFragment.goToFragment(CreatePrayerFragment.newInstance());
                return true;
            case R.id.action_settings:
                //TODO: Handle SettingsClick
                return true;
            default:
                return false;
        }


    }
}

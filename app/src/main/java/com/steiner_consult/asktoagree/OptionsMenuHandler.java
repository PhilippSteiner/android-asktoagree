package com.steiner_consult.asktoagree;

import android.content.Intent;
import android.view.MenuItem;

import com.steiner_consult.fragments.BaseFragment;
import com.steiner_consult.fragments.CreatePrayerFragment;
import com.steiner_consult.workers.LogoutWorker;

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
                Intent intent = new Intent(baseFragment.getActivity().getApplicationContext(), LoginActivity.class);
                baseFragment.getActivity().startActivity(intent);
                //TODO: Handle SettingsClick
                return true;
            case R.id.action_logout:
                new LogoutWorker((BaseActivity)baseFragment.getActivity()).logoutUser();
            default:
                return false;
        }


    }
}

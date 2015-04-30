package com.steiner_consult.asktoagree;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.steiner_consult.fragments.BaseFragment;
import com.steiner_consult.fragments.CreatePrayerFragment;
import com.steiner_consult.fragments.MyPrayerPagerFragment;
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

    public boolean handleClick(BaseActivity baseActivity, MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create:
                goToFragment(baseActivity);
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(baseActivity.getApplicationContext(), LoginActivity.class);
                baseActivity.startActivity(intent);
                return true;
            case R.id.action_logout:
                LogoutWorker logoutWorker = new LogoutWorker(baseActivity);
                logoutWorker.logoutUser();
                return true;
            default:
                return false;
        }
    }

    private void goToFragment(BaseActivity baseActivity) {
        FragmentManager fragmentManager = baseActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, CreatePrayerFragment.newInstance())
                .addToBackStack(MyPrayerPagerFragment.class.getName())
                .commit();
    }
}

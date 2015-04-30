package com.steiner_consult.asktoagree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.facebook.appevents.AppEventsLogger;
import com.steiner_consult.utilities.AppConfig;

/**
 * Created by Philipp on 09.03.15.
 */
public class LauncherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        if (isLoggedIn())
            intent = new Intent(getApplicationContext(), MainActivity.class);
        else
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    private boolean isLoggedIn() {
        return getSharedPreferences(AppConfig.PREFS_NAMESPACE, Context.MODE_PRIVATE).getString(AppConfig.AUTH_TOKEN_HEADER, null) != null;
    }
}

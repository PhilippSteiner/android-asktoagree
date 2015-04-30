package com.steiner_consult.asktoagree;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;

import com.facebook.appevents.AppEventsLogger;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.steiner_consult.models.LocalUser;
import com.steiner_consult.models.responses.LoginResponse;
import com.steiner_consult.utilities.AppConfig;

/**
 * Created by Philipp on 22.12.14.
 */
public class BaseActivity extends ActionBarActivity {

    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefEditor;

    public void storeUserInPreferences(LoginResponse loginResponse) {
        prefEditor = getApplicationContext().getSharedPreferences(AppConfig.PREFS_NAMESPACE, Context.MODE_PRIVATE).edit();
        prefEditor.putString(AppConfig.PREFS_USERNAME, loginResponse.getUsername());
        prefEditor.apply();
    }

    public LocalUser getUserFromPreferences() {
        sharedPreferences = getApplicationContext().getSharedPreferences(AppConfig.PREFS_NAMESPACE, Context.MODE_PRIVATE);
        LocalUser localUser = new LocalUser();
        localUser.setUsername(sharedPreferences.getString(AppConfig.PREFS_USERNAME, null));
        return localUser;
    }

    public ProgressDialog getProgressDialog() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
        }
        return progressDialog;
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


}

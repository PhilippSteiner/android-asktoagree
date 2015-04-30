package com.steiner_consult;

import android.app.Application;

import com.facebook.FacebookSdk;

/**
 * Created by Philipp on 07.04.15.
 */
public class AskToAgreeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(getApplicationContext());
    }
}

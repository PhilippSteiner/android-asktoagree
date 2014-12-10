package com.steiner_consult.logins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

/**
 * Created by Philipp on 10.12.14.
 */
public class FacebookClient {

    private String TAG = "FacebookClient";


    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    private UiLifecycleHelper uiHelper;

    public FacebookClient(Activity activity, Bundle savedInstanceState) {
        uiHelper = new UiLifecycleHelper(activity, callback);
        uiHelper.onCreate(savedInstanceState);
    }


    public void onResume() {
        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null &&
                (session.isOpened() || session.isClosed()) ) {
            onSessionStateChange(session, session.getState(), null);
        }
        uiHelper.onResume();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        uiHelper.onActivityResult(requestCode, resultCode, data);
    }

    public void onPause() {
        uiHelper.onPause();
    }

    public void onDestroy() {
        uiHelper.onDestroy();
    }

    public void onSaveInstanceState(Bundle outState) {
        uiHelper.onSaveInstanceState(outState);
    }

}

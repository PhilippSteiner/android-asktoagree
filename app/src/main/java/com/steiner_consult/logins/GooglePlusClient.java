package com.steiner_consult.logins;

import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.*;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.steiner_consult.Utils.AppConfig;
import com.steiner_consult.asktoagree.LoginActivity;
import com.steiner_consult.asktoagree.R;

/**
 * Created by Philipp on 09.12.14.
 */
public class GooglePlusClient implements ConnectionCallbacks, OnConnectionFailedListener{

    private static String TAG = "GooglePlusClient";

    /* Track whether the sign-in button has been clicked so that we know to resolve
 * all issues preventing sign-in without waiting.
 */
    private boolean mSignInClicked;

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
     * us from starting further intents.
     */
    private boolean mIntentInProgress;

    /* Store the connection result from onConnectionFailed callbacks so that we can
 * resolve them when the user clicks sign-in.
 */

    private ConnectionResult mConnectionResult;
    public Person person;
    private LoginActivity loginActivity;

    public GooglePlusClient(LoginActivity activity) {
        this.loginActivity = activity;
        buildGoogleAPIClient();

    }

    private void buildGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(loginActivity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
    }

    // Called onStart of LoginActivity
    public void connect() {
        mGoogleApiClient.connect();
    }


    // Called onStop of LoginActivity
    public void disconnect() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode != loginActivity.RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.gplus_btn_sign_in
                && !mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }

        if (view.getId() == R.id.gplus_btn_sign_out) {
            if (mGoogleApiClient.isConnected()) {
                Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                        .setResultCallback(new ResultCallback<Status>() {

                            public void onResult(Status status) {

                                // mGoogleApiClient is now disconnected and access has been revoked.
                                // Trigger app logic to comply with the developer policies
                                //TODO: delete data from Server on Revoke Access
                            }

                        });
                mGoogleApiClient.disconnect();
                loginActivity.updateView(AppConfig.LOGGED_OUT);
                Toast.makeText(loginActivity, "User is logged out!", Toast.LENGTH_LONG).show();
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        // We've resolved any connection errors.  mGoogleApiClient can be used to
        // access Google APIs on behalf of the user.
        mSignInClicked = false;
        person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        Toast.makeText(loginActivity, "User " + person.getDisplayName() + " is connected!", Toast.LENGTH_LONG).show();
        loginActivity.updateView(AppConfig.LOGGED_IN);
        //new RetrieveGPlusAccessTokenTask().execute(); // TODO: Check if we need the Token for offline access to the Google+ API
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
        loginActivity.updateView(AppConfig.LOGGED_OUT);
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress) {
            // Store the ConnectionResult so that we can use it later when the user clicks
            // 'sign-in'.
            mConnectionResult = result;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }


    /* A helper method to resolve the current ConnectionResult error. */
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                loginActivity.startIntentSenderForResult(mConnectionResult.getResolution().getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    public Boolean getGooglePlayServiceStatus(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(loginActivity);
        if (resultCode == ConnectionResult.SUCCESS)
        {
            // Google Play Services installed correctly, continue
            return true;
        }
        else if (resultCode == ConnectionResult.SERVICE_MISSING ||
                resultCode == ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED ||
                resultCode == ConnectionResult.SERVICE_DISABLED)
        {
            // Google Play Services not installed / too old / disabled.
            // Show dialog to install or update
            return false;
        }
        else
        {
            // Something else went wrong, handle this yourself
            return false;
        }
    }


    /*
    private class RetrieveGPlusAccessTokenTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... params) {
            String access_token = "empty_token";

            Bundle appActivities = new Bundle();
            //appActivities.putString(GoogleAuthUtil.KEY_REQUEST_VISIBLE_ACTIVITIES,
            //      "<APP-ACTIVITY1> <APP-ACTIVITY2>");
            String scopes = "oauth2:server:client_id:" + AppConfig.SERVER_CLIENT_ID + ":api_scope:" + Scopes.PLUS_LOGIN;

            try {
                access_token = GoogleAuthUtil.getToken(
                        loginActivity,                                              // Context context
                        Plus.AccountApi.getAccountName(mGoogleApiClient),  // String accountName
                        scopes,                                            // String scope
                        appActivities                                      // Bundle bundle
                );

            } catch (IOException transientEx) {
                // network or server error, the call is expected to succeed if you try again later.
                // Don't attempt to call again immediately - the request is likely to
                // fail, you'll hit quotas or back-off.
                Log.e(TAG, transientEx.toString());
            } catch (UserRecoverableAuthException e) {
                // Requesting an authorization code will always throw
                // UserRecoverableAuthException on the first call to GoogleAuthUtil.getToken
                // because the user must consent to offline access to their data.  After
                // consent is granted control is returned to your loginActivity in onActivityResult
                // and the second call to GoogleAuthUtil.getToken will succeed.
                loginActivity.startActivityForResult(e.getIntent(), RC_SIGN_IN);
            } catch (GoogleAuthException authEx) {
                // Failure. The call is not expected to ever succeed so it should not be
                // retried.
                Log.e(TAG, authEx.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            return access_token;
        }

        @Override
        protected void onPostExecute(String token) {
            Log.d(TAG, "Token or Result: " + token);
        }
    }

    */

    //TODO: Remove old code


}

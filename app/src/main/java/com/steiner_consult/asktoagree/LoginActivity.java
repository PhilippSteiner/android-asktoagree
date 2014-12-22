package com.steiner_consult.asktoagree;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.steiner_consult.Utils.AppConfig;
import com.steiner_consult.asynctasks.UserRegisterWorker;
import com.steiner_consult.logins.FacebookClient;
import com.steiner_consult.logins.GooglePlusClient;

import java.util.Arrays;

/**
 * Created by Philipp on 09.12.14.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private GooglePlusClient googlePlusClient;
    private SignInButton googleSignInButton, googleSignOutButton;

    private FacebookClient facebookClient;
    private LoginButton facebookLoginButton;
    private Button createUserButton;
    private UserRegisterWorker userRegisterWorker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createUserButton = (Button) findViewById(R.id.login_button_createAccount);
        createUserButton.setOnClickListener(this);

        facebookClient = new FacebookClient(this, savedInstanceState);
        facebookLoginButton = (LoginButton) findViewById(R.id.authButton);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile"));


        googlePlusClient = new GooglePlusClient(this);

        googleSignInButton = (SignInButton) findViewById(R.id.gplus_btn_sign_in);
        googleSignOutButton = (SignInButton) findViewById(R.id.gplus_btn_sign_out);


        if (googlePlusClient.getGooglePlayServiceStatus()) {
            googleSignInButton.setOnClickListener(this);
            googleSignOutButton.setOnClickListener(this);
            setGoogleButtonText(getResources().getString(R.string.gplus_sign_in_button_name), googleSignInButton);
        } else {
            googleSignInButton.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        googlePlusClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googlePlusClient.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        facebookClient.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        facebookClient.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookClient.onActivityResult(requestCode, resultCode, data);
        googlePlusClient.onActivityResult(requestCode, resultCode);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        facebookClient.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        facebookClient.onDestroy();
    }

    public void updateView(int loginStatus) {
        String buttonText;
        switch (loginStatus) {
            case AppConfig.LOGGED_OUT:
                googleSignInButton.setVisibility(View.VISIBLE);
                googleSignOutButton.setVisibility(View.GONE);
                buttonText = getResources().getString(R.string.gplus_sign_in_button_name);
                break;
            case AppConfig.LOGGED_IN:
                googleSignInButton.setVisibility(View.GONE);
                googleSignOutButton.setVisibility(View.VISIBLE);
                buttonText = String.format(getResources().getString(R.string.gplus_signed_in_as_text), googlePlusClient.person.getDisplayName());
                break;
            default:
                buttonText = "";
                break;
        }
        setGoogleButtonText(buttonText, googleSignOutButton);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_button_createAccount) {
            createAccount();
        }
        googlePlusClient.onClick(v);
    }

    private void setGoogleButtonText(String text, SignInButton signInButton) {
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(text);
                return;

            }
        }
    }

    private void createAccount() {
        userRegisterWorker = new UserRegisterWorker(this);
        userRegisterWorker.createUser();

    }


    //TODO: Check for GooglePlayServices


}

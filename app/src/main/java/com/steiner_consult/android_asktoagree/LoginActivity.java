package com.steiner_consult.android_asktoagree;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.SignInButton;
import com.steiner_consult.Utils.AppConfig;
import com.steiner_consult.logins.GooglePlusClient;

/**
 * Created by Philipp on 09.12.14.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private GooglePlusClient googlePlusClient;
    private SignInButton googleSignInButton, googleSignOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googlePlusClient.onActivityResult(requestCode, resultCode);

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


    //TODO: Check for GooglePlayServices


}

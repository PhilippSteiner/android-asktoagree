package com.steiner_consult.android_asktoagree;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.SignInButton;
import com.steiner_consult.logins.GooglePlusClient;

/**
 * Created by Philipp on 09.12.14.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    private GooglePlusClient googlePlusClient;
    private SignInButton googleSignInButton, googleSignOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        googlePlusClient = new GooglePlusClient(this);

        googleSignInButton = (SignInButton) findViewById(R.id.gplus_btn_sign_in);
        googleSignOutButton = (SignInButton) findViewById(R.id.gplus_btn_sign_out);

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

    public void updateView(int loginStatus) {}

    @Override
    public void onClick(View v) {
        googlePlusClient.onClick(v);
    }


    //TODO: Check for GooglePlayServices


}

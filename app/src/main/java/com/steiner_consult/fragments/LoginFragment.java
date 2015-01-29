package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AppEventsLogger;
import com.facebook.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.steiner_consult.asktoagree.LoginActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.logins.FacebookClient;
import com.steiner_consult.logins.GooglePlusClient;
import com.steiner_consult.utilities.AppConfig;

import java.util.Arrays;

/**
 * Created by Philipp on 29.01.15.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    LoginActivity loginActivity;
    private GooglePlusClient googlePlusClient;
    private SignInButton googleSignInButton, googleSignOutButton;
    private FacebookClient facebookClient;
    private LoginButton facebookLoginButton;
    private Button registerButton;




    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_login, container, false);

        loginActivity = (LoginActivity) getActivity();

        registerButton = (Button) rootView.findViewById(R.id.login_button_registerAccount);
        registerButton.setOnClickListener(this);

        facebookClient = new FacebookClient(getActivity(), savedInstanceState);
        facebookLoginButton = (LoginButton) rootView.findViewById(R.id.authButton);
        //facebookLoginButton.setPadding(0, 0, 0, 0);
        facebookLoginButton.setBackgroundResource(R.drawable.facebook_button_blue);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile"));


        googlePlusClient = new GooglePlusClient(this);

        googleSignInButton = (SignInButton) rootView.findViewById(R.id.gplus_btn_sign_in);
        googleSignInButton.setBackgroundResource(0);
        googleSignInButton.setPadding(0,0,0,0);
        googleSignOutButton = (SignInButton) rootView.findViewById(R.id.gplus_btn_sign_out);


        if (googlePlusClient.getGooglePlayServiceStatus()) {
            googleSignInButton.setOnClickListener(this);
            googleSignOutButton.setOnClickListener(this);
            setGoogleButtonText(getResources().getString(R.string.gplus_sign_in_button_name), googleSignInButton);
        } else {
            googleSignInButton.setVisibility(View.GONE);
        }


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        googlePlusClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        googlePlusClient.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        facebookClient.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(loginActivity);
    }

    @Override
    public void onPause() {
        super.onPause();
        facebookClient.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(loginActivity);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        facebookClient.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
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
        if(v.getId() == R.id.login_button_registerAccount) {
            goToRegistrationFragment();
        }
        googlePlusClient.onClick(v);
    }

    private void goToRegistrationFragment() {
        RegistrationFragment registrationFragment = RegistrationFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, registrationFragment)
                .addToBackStack(LoginFragment.class.getName())
                .commit();
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

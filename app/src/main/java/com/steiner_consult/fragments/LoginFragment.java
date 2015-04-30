package com.steiner_consult.fragments;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;
import com.google.android.gms.common.SignInButton;
import com.steiner_consult.asktoagree.LoginActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.logins.GooglePlusClient;
import com.steiner_consult.models.requests.LoginRequest;
import com.steiner_consult.utilities.AppConfig;
import com.steiner_consult.workers.LoginWorker;


/**
 * Created by Philipp on 29.01.15.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private LoginActivity loginActivity;
    private GooglePlusClient googlePlusClient;
    private SignInButton googleSignInButton, googleSignOutButton;
    private EditText email, password;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private final String TAG = getClass().getName();

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_login, container, false);

        loginActivity = (LoginActivity) getActivity();

        /* Login */
        email = (EditText) rootView.findViewById(R.id.login_edittext_email);
        email.setTypeface(Typeface.DEFAULT);
        password = (EditText) rootView.findViewById(R.id.login_edittext_password);
        password.setTypeface(Typeface.DEFAULT);
        Button login = (Button) rootView.findViewById(R.id.login_button_login);
        login.setOnClickListener(this);
        TextView forgotpassword = (TextView) rootView.findViewById(R.id.login_link_forgotpassword);
        forgotpassword.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgotpassword.setOnClickListener(this);

        loginButton = (LoginButton) rootView.findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Success!");


                String appLinkUrl, previewImageUrl;

                appLinkUrl = "https://fb.me/830277370372700";
                previewImageUrl = "http://en.wikipedia.org/wiki/Smiley#/media/File:Smiley.svg";

                if (AppInviteDialog.canShow()) {
                    AppInviteContent content = new AppInviteContent.Builder()
                            .setApplinkUrl(appLinkUrl)
                            .setPreviewImageUrl(previewImageUrl)
                            .build();
                    AppInviteDialog.show(getActivity(), content);
                }

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Cancel!");
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d(TAG, "Error " + exception.toString());
            }
        });



        /* Register */


        googlePlusClient = new GooglePlusClient(this);

        googleSignInButton = (SignInButton) rootView.findViewById(R.id.gplus_btn_sign_in);
        googleSignInButton.setBackgroundResource(0);
        googleSignInButton.setPadding(0, 0, 0, 0);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        if (v.getId() == R.id.login_button_login) {
            loginUser();
        }
        if (v.getId() == R.id.login_link_forgotpassword) {
            goToForgotPassword();
        }

        googlePlusClient.onClick(v);
    }

    private void loginUser() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email.getText().toString());
        loginRequest.setPassword(password.getText().toString());
        LoginWorker loginWorker = new LoginWorker(loginActivity);
        loginWorker.loginUser(loginRequest);
    }

    private void goToForgotPassword() {
        ForgotPasswordFragment forgotPasswordFragment = ForgotPasswordFragment.newInstance();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, forgotPasswordFragment)
                .addToBackStack(ForgotPasswordFragment.class.getName())
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

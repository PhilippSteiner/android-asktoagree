package com.steiner_consult.asktoagree;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import com.steiner_consult.fragments.LoginFragment;


/**
 * Created by Philipp on 09.12.14.
 */
public class LoginActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        LoginFragment loginFragment = LoginFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, loginFragment)
                .commit();

    }

}

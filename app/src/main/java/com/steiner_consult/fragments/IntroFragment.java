package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.steiner_consult.asktoagree.R;

/**
 * Created by Philipp on 08.02.15.
 */
public class IntroFragment extends Fragment implements View.OnClickListener {

    public static IntroFragment newInstance() {
        return new IntroFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_intro, container, false);

        Button signIn = (Button) rootView.findViewById(R.id.intro_button_signin);
        Button signUp = (Button) rootView.findViewById(R.id.intro_button_signup);

        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment = null;
        if (v.getId() == R.id.intro_button_signin) {
            fragment = LoginFragment.newInstance();
        }
        if (v.getId() == R.id.intro_button_signup) {
            fragment = RegistrationFragment.newInstance();
        }
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .addToBackStack(IntroFragment.class.getName())
                .commit();
    }
}

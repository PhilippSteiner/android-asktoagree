package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.steiner_consult.asktoagree.LoginActivity;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.requests.RegisterRequest;
import com.steiner_consult.workers.UserRegisterWorker;

/**
 * Created by Philipp on 29.01.15.
 */
public class RegistrationFragment extends Fragment implements  View.OnClickListener {

    private Button createUserButton;
    private UserRegisterWorker userRegisterWorker;
    private EditText firstname, lastname, username, email, password;
    private RegisterRequest registerRequest;


    public static RegistrationFragment newInstance() {
        return new RegistrationFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_register, container, false);


        createUserButton = (Button) rootView.findViewById(R.id.register_button_createAccount);
        createUserButton.setOnClickListener(this);

        firstname = (EditText) rootView.findViewById(R.id.register_edittext_firstname);
        lastname = (EditText) rootView.findViewById(R.id.register_edittext_lasttname);
        email = (EditText) rootView.findViewById(R.id.register_edittext_email);
        username = (EditText) rootView.findViewById(R.id.register_edittext_username);
        password = (EditText) rootView.findViewById(R.id.register_edittext_password);

        registerRequest = new RegisterRequest();
        return rootView;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.register_button_createAccount) {
            createAccount();
        }
    }

    private void createAccount() {
        userRegisterWorker = new UserRegisterWorker((LoginActivity) getActivity());

        registerRequest.setFirstname(firstname.getText().toString());
        registerRequest.setLastname(lastname.getText().toString());
        registerRequest.setEmail(email.getText().toString());
        registerRequest.setUsername(username.getText().toString());
        registerRequest.setPassword(password.getText().toString());

        userRegisterWorker.createUser(registerRequest);

    }
}

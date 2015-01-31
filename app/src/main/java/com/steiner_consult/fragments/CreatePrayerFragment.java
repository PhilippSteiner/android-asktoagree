package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.steiner_consult.asktoagree.MainActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.Prayer;
import com.steiner_consult.workers.CreatePrayerWorker;

/**
 * Created by Philipp on 29.01.15.
 */
public class CreatePrayerFragment extends BaseFragment {

    private Button createPrayerButton;
    private EditText title, text;
    private CreatePrayerWorker createPrayerWorker;


    public static CreatePrayerFragment newInstance() {
        return new CreatePrayerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_myprayer_create, container, false);

        title = (EditText) rootView.findViewById(R.id.myprayer_title);
        text = (EditText) rootView.findViewById(R.id.myprayer_text);


        createPrayerButton = (Button) rootView.findViewById(R.id.myprayer_button_create);
        createPrayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPrayer();
            }
        });

        return rootView;
    }

    private void createPrayer() {
        Prayer prayer = new Prayer();
        prayer.setTitle(title.getText().toString());
        prayer.setText(text.getText().toString());

        createPrayerWorker = new CreatePrayerWorker((MainActivity) getActivity());
        createPrayerWorker.createPrayer(prayer);

    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }
}

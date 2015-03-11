package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.Prayer;

/**
 * Created by Philipp on 08.03.15.
 */
public class ViewPrayerFragment extends BaseFragment {

    private Prayer prayer;
    private TextView title;

    public static ViewPrayerFragment newInstance(Prayer prayer) {
        ViewPrayerFragment viewPrayerFragment = new ViewPrayerFragment();
        viewPrayerFragment.init(prayer);
        return viewPrayerFragment;
    }

    private void init(Prayer prayer) {
        this.prayer = prayer;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_myprayer_view, container, false);
        title = (TextView) rootView.findViewById(R.id.title);
        title.setText(prayer.getTitle());

        return rootView;
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }
}

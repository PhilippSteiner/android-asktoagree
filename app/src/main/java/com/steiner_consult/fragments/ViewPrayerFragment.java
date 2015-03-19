package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.AppUser;
import com.steiner_consult.models.Prayer;

/**
 * Created by Philipp on 08.03.15.
 */
public class ViewPrayerFragment extends BaseFragment {

    private Prayer prayer;
    private TextView title, text;
    private LinearLayout friendsLayout;

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
        text = (TextView) rootView.findViewById(R.id.text);
        title.setText(prayer.getTitle());
        text.setText(prayer.getText());
        friendsLayout = (LinearLayout) rootView.findViewById(R.id.layout_sharedwith);

        setupFriendsList();

        return rootView;
    }


    private void setupFriendsList() {
        for (AppUser friend: prayer.getSharedwith()) {
            addToList(friend);
        }
    }

    private void addToList(AppUser friend) {
        TextView username = new TextView(getActivity());
        username.setText(friend.getUsername());
        friendsLayout.addView(username);
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }
}

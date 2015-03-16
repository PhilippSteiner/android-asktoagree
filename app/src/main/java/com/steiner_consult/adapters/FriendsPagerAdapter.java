package com.steiner_consult.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.steiner_consult.fragments.FriendsFragment;
import com.steiner_consult.fragments.FriendsPagerFragment;
import com.steiner_consult.fragments.InviteFragment;
import com.steiner_consult.fragments.RequestFragment;
import com.steiner_consult.fragments.RespondFragment;
import com.steiner_consult.fragments.SearchFragment;
import com.steiner_consult.slider.TabItem;

import java.util.List;

/**
 * Created by Philipp on 12.03.15.
 */
public class FriendsPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES = { "Invite", "Request", "Respond", "Friends", "Search" };

    public FriendsPagerAdapter(FriendsPagerFragment friendsPagerFragment) {
        super(friendsPagerFragment.getChildFragmentManager());
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InviteFragment.newInstance();
            case 1:
                return RequestFragment.newInstance();
            case 2:
                return RespondFragment.newInstance();
            case 3:
                return FriendsFragment.newInstance();
            case 4:
                return SearchFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }
}

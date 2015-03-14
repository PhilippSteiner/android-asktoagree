package com.steiner_consult.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.steiner_consult.fragments.FriendsPagerFragment;
import com.steiner_consult.fragments.InviteFragment;
import com.steiner_consult.slider.TabItem;

import java.util.List;

/**
 * Created by Philipp on 12.03.15.
 */
public class FriendsPagerAdapter extends FragmentPagerAdapter {

    private List<TabItem> tabItemList;

    public FriendsPagerAdapter(FriendsPagerFragment friendsPagerFragment, List<TabItem> tabItemList) {
        super(friendsPagerFragment.getChildFragmentManager());
        this.tabItemList = tabItemList;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InviteFragment.newInstance();
            case 1:
                return InviteFragment.newInstance();
            case 2:
                return InviteFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabItemList.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return tabItemList.size();
    }
}

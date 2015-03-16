package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.steiner_consult.adapters.FriendsPagerAdapter;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.slider.SlidingTabLayout;

/**
 * Created by Philipp on 12.03.15.
 */
public class FriendsPagerFragment extends BaseFragment {

    private ViewPager viewPager;
    private FriendsPagerAdapter friendsPagerAdapter;
    private PagerSlidingTabStrip pagerSlidingTabStrip;

    public static FriendsPagerFragment newInstance() {
        return new FriendsPagerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_friendspager, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        friendsPagerAdapter = new FriendsPagerAdapter(this);
        viewPager.setAdapter(friendsPagerAdapter);
        pagerSlidingTabStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.pageslider);
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setIndicatorColor(getResources().getColor(R.color.background_green));
        return rootView;
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }
}

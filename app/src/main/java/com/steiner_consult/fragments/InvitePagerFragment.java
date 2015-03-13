package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.steiner_consult.asktoagree.R;

/**
 * Created by Philipp on 12.03.15.
 */
public class InvitePagerFragment extends BaseFragment {

    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_invitepager, container, false);
        initializeInviteTabs();
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);



        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }
}

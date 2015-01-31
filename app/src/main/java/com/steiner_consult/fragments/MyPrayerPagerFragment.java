package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.steiner_consult.asktoagree.OptionsMenuHandler;
import com.steiner_consult.asktoagree.R;

/**
 * Created by Philipp on 29.01.15.
 */
public class MyPrayerPagerFragment extends BaseFragment {

    public static MyPrayerPagerFragment newInstance() {
        return new MyPrayerPagerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_myprayerpager, container, false);

        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_myprayerpagerfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return OptionsMenuHandler.getInstance().handleClick(this, item);
    }


    @Override
    public void goToFragment(BaseFragment baseFragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, baseFragment)
                .addToBackStack(MyPrayerPagerFragment.class.getName())
                .commit();
    }
}

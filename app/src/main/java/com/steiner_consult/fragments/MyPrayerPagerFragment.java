package com.steiner_consult.fragments;

import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.OptionsMenuHandler;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.Prayer;
import com.steiner_consult.workers.MyPrayersWorker;
import com.steiner_consult.wrappers.MyPrayerWrapper;

import java.util.ArrayList;

/**
 * Created by Philipp on 29.01.15.
 */
public class MyPrayerPagerFragment extends BaseFragment {

    private ListView listView;
    private Prayer[] prayers;
    private MyPrayersListAdapter myPrayersListAdapter;
    private MyPrayersWorker myPrayersWorker;

    public static MyPrayerPagerFragment newInstance() {
        return new MyPrayerPagerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_myprayerpager, container, false);
        setHasOptionsMenu(true);

        listView = (ListView) rootView.findViewById(R.id.listview);
        myPrayersWorker = new MyPrayersWorker(this);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        myPrayersWorker.loadPrayers();
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

    public void setData(Prayer[] prayers) {
        this.prayers = prayers;
        myPrayersListAdapter = new MyPrayersListAdapter((BaseActivity) getActivity());
        listView.setAdapter(myPrayersListAdapter);
    }

    private class MyPrayersListAdapter extends ArrayAdapter<Prayer> {

        public MyPrayersListAdapter(BaseActivity baseActivity) {
            super(baseActivity, R.layout.list_item_my_prayers, prayers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyPrayerWrapper wrapper;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_my_prayers, parent, false);
                wrapper = new MyPrayerWrapper(convertView, (BaseActivity) getActivity());
                convertView.setTag(wrapper);
            } else {
                wrapper = (MyPrayerWrapper) convertView.getTag();
            }
            wrapper.setData(prayers[position]);
            return convertView;
        }
    }
}

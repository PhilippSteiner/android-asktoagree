package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.Prayer;
import com.steiner_consult.workers.SharedPrayerWorker;
import com.steiner_consult.wrappers.SharedPrayerWrapper;

import java.util.ArrayList;

/**
 * Created by Philipp on 22.03.15.
 */
public class SharedPrayersFragment extends BaseFragment {

    private ListView listView;
    private ArrayList<Prayer> prayers;
    private SharedPrayersListAdapter sharedPrayersListAdapter;


    public static SharedPrayersFragment newInstance() {
        return new SharedPrayersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_shared, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview);
        SharedPrayerWorker sharedPrayerWorker = new SharedPrayerWorker(this);
        sharedPrayerWorker.loadSharedPrayers();
        return rootView;
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }

    public void setData(ArrayList<Prayer> prayers) {
        this.prayers = prayers;
        sharedPrayersListAdapter = new SharedPrayersListAdapter((BaseActivity) getActivity());
        listView.setAdapter(sharedPrayersListAdapter);
    }

    private class SharedPrayersListAdapter extends ArrayAdapter<Prayer> {

        public SharedPrayersListAdapter(BaseActivity baseActivity) {
            super(baseActivity, R.layout.list_item_shared_prayer, prayers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SharedPrayerWrapper wrapper;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_shared_prayer, parent, false);
                wrapper = new SharedPrayerWrapper(convertView);
                convertView.setTag(wrapper);
            } else {
                wrapper = (SharedPrayerWrapper) convertView.getTag();
            }
            wrapper.setData(prayers.get(position));
            return convertView;
        }
    }
}
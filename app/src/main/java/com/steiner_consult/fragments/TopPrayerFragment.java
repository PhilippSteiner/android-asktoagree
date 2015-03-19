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
import com.steiner_consult.wrappers.MyPrayerWrapper;
import com.steiner_consult.wrappers.TopPrayerWrapper;

import java.util.ArrayList;

/**
 * Created by Philipp on 19.03.15.
 */
public class TopPrayerFragment extends BaseFragment {

    private ListView listView;
    private ArrayList<Prayer> prayers = new ArrayList<>();

    public static TopPrayerFragment newInstance() {
        return new TopPrayerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_topprayer, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview);

        return rootView;
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }

    private class MyPrayersListAdapter extends ArrayAdapter<Prayer> {

        public MyPrayersListAdapter(BaseActivity baseActivity) {
            super(baseActivity, R.layout.list_item_prayer_top, prayers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           TopPrayerWrapper wrapper;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_prayer_top, parent, false);
                wrapper = new TopPrayerWrapper(convertView);
                convertView.setTag(wrapper);
            } else {
                wrapper = (TopPrayerWrapper) convertView.getTag();
            }
            wrapper.setData(prayers.get(position));
            return convertView;
        }
    }
}

package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.interfaces.EndlessScrollListener;
import com.steiner_consult.models.Prayer;
import com.steiner_consult.workers.TopPrayerWorker;
import com.steiner_consult.wrappers.MyPrayerWrapper;
import com.steiner_consult.wrappers.TopPrayerWrapper;

import java.util.ArrayList;

/**
 * Created by Philipp on 19.03.15.
 */
public class TopPrayerFragment extends BaseFragment {

    private ListView listView;
    private ArrayList<Prayer> prayers = new ArrayList<>();
    private TopPrayersListAdapter topPrayersListAdapter;
    private TopPrayerWorker topPrayerWorker;
    private View footer;

    public static TopPrayerFragment newInstance() {
        return new TopPrayerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_topprayer, container, false);
        Log.d(getClass().getName(), "onCreate()");
        listView = (ListView) rootView.findViewById(R.id.listview);
        topPrayersListAdapter = new TopPrayersListAdapter();
        listView.setAdapter(topPrayersListAdapter);
        topPrayerWorker = new TopPrayerWorker(this);
        footer = inflater.inflate(R.layout.loading_footer, null, false);
        listView.addFooterView(footer);
        listView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                Log.d("Fragment", "Loading More.... Page: " + page);
                topPrayerWorker.loadNextPage(page);
                footer.setVisibility(View.VISIBLE);
            }
        });

        return rootView;
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }

    public void addData(ArrayList<Prayer> prayers) {
        topPrayersListAdapter.addAll(prayers);
        topPrayersListAdapter.notifyDataSetChanged();
        footer.setVisibility(View.GONE);
    }

    private class TopPrayersListAdapter extends ArrayAdapter<Prayer> {

        public TopPrayersListAdapter() {
            super(getActivity(), R.layout.list_item_prayer_top);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           TopPrayerWrapper wrapper;
            Prayer prayer = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_prayer_top, parent, false);
                wrapper = new TopPrayerWrapper(convertView);
                convertView.setTag(wrapper);
            } else {
                wrapper = (TopPrayerWrapper) convertView.getTag();
            }
            wrapper.setData(prayer);
            return convertView;
        }
    }
}

package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.interfaces.FriendFragment;
import com.steiner_consult.models.AppUser;
import com.steiner_consult.workers.FriendsWorker;
import com.steiner_consult.wrappers.RequestUserWrapper;

import java.util.ArrayList;

/**
 * Created by Philipp on 12.03.15.
 */
public class RequestFragment extends BaseFragment implements FriendFragment {

    private FriendsWorker friendsWorker;
    private ArrayList<AppUser> appUsers;
    private UserListAdapter userListAdapter;
    private ListView listView;

    public static RequestFragment newInstance() {
        return new RequestFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_invite_request, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressbar);
        friendsWorker = new FriendsWorker(this);
        friendsWorker.loadRequests();
        return rootView;
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }

    @Override
    public void setListAdapterData(ArrayList<AppUser> appUsers) {
        this.appUsers = appUsers;
        userListAdapter = new UserListAdapter((BaseActivity) getActivity());
        listView.setAdapter(userListAdapter);
    }

    private class UserListAdapter extends ArrayAdapter<AppUser> {

        public UserListAdapter(BaseActivity baseActivity) {
            super(baseActivity, R.layout.list_item_user_request, appUsers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RequestUserWrapper wrapper;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user_request, parent, false);
                wrapper = new RequestUserWrapper(convertView);
                convertView.setTag(wrapper);
            } else {
                wrapper = (RequestUserWrapper) convertView.getTag();
            }
            wrapper.setData(appUsers.get(position));
            return convertView;
        }
    }
}

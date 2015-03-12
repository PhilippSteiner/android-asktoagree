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
import com.steiner_consult.models.AppUser;
import com.steiner_consult.workers.FriendsWorker;
import com.steiner_consult.wrappers.InviteUserWrapper;
import java.util.ArrayList;

/**
 * Created by Philipp on 12.03.15.
 */
public class InviteFragment extends BaseFragment {

    private ListView listView;
    private ArrayList<AppUser> appUsers;
    private UserListAdapter userListAdapter;

    public static InviteFragment newInstance() {
        return new InviteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_invite, container, false);

        listView = (ListView) rootView.findViewById(R.id.listview);
        FriendsWorker friendsWorker = new FriendsWorker(this);
        friendsWorker.loadUsers();

        return rootView;
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }


    public void setData(ArrayList<AppUser> users) {
        appUsers = users;
        userListAdapter = new UserListAdapter((BaseActivity) getActivity());
        listView.setAdapter(userListAdapter);
    }

    private class UserListAdapter extends ArrayAdapter<AppUser> {

        public UserListAdapter(BaseActivity baseActivity) {
            super(baseActivity, R.layout.list_item_user_invite, appUsers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            InviteUserWrapper wrapper;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user_invite, parent, false);
                wrapper = new InviteUserWrapper(convertView, (BaseActivity) getActivity());
                convertView.setTag(wrapper);
            } else {
                wrapper = (InviteUserWrapper) convertView.getTag();
            }
            wrapper.setData(appUsers.get(position));
            return convertView;
        }
    }
}

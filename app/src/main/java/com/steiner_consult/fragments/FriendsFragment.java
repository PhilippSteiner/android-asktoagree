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
import com.steiner_consult.interfaces.FriendFragment;
import com.steiner_consult.models.AppUser;
import com.steiner_consult.workers.FriendsWorker;
import com.steiner_consult.wrappers.FriendsWrapper;
import com.steiner_consult.wrappers.RespondUserWrapper;

import java.util.ArrayList;

/**
 * Created by Philipp on 13.03.15.
 */
public class FriendsFragment extends BaseFragment implements FriendFragment {

    private FriendsWorker friendsWorker;
    private ArrayList<AppUser> appUsers;
    private UserListAdapter userListAdapter;
    private ListView listView;

    public static FriendsFragment newInstance() {
        return new FriendsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_invite_response, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview);
        friendsWorker = new FriendsWorker(this);
        friendsWorker.loadFriends();
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
            super(baseActivity, R.layout.list_item_user_friend, appUsers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            FriendsWrapper wrapper;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user_friend, parent, false);
                wrapper = new FriendsWrapper(convertView);
                convertView.setTag(wrapper);
            } else {
                wrapper = (FriendsWrapper) convertView.getTag();
            }
            wrapper.setData(appUsers.get(position));
            return convertView;
        }
    }
}

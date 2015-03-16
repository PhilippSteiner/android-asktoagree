package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.interfaces.FriendFragment;
import com.steiner_consult.models.AppUser;
import com.steiner_consult.workers.FriendsWorker;
import com.steiner_consult.wrappers.InviteUserWrapper;
import com.steiner_consult.wrappers.SearchUserWrapper;

import java.util.ArrayList;

/**
 * Created by Philipp on 14.03.15.
 */
public class SearchFragment  extends BaseFragment implements FriendFragment {

    private FriendsWorker friendsWorker;
    private Button searchButton;
    private ListView listView;
    private EditText searchText;
    private UserListAdapter userListAdapter;
    private ArrayList<AppUser> appUsers = new ArrayList<>();

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_search, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview);
        progressBar= (ProgressBar) rootView.findViewById(R.id.progressbar);
        userListAdapter = new UserListAdapter((BaseActivity) getActivity());
        listView.setAdapter(userListAdapter);
        searchText = (EditText) rootView.findViewById(R.id.search_text);
        searchButton = (Button) rootView.findViewById(R.id.button_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friendsWorker.searchUsers(searchText.getText().toString());
            }
        });
        friendsWorker = new FriendsWorker(this);


        return rootView;
    }

    public void sendInvite(AppUser appUser) {
        friendsWorker.sendInvite(appUser);
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }

    @Override
    public void setListAdapterData(ArrayList<AppUser> appUsers) {
        this.appUsers = appUsers;
        userListAdapter.clear();
        userListAdapter.addAll(appUsers);
        userListAdapter.notifyDataSetChanged();
    }

    private SearchFragment getFragment() {
        return this;
    }

    private class UserListAdapter extends ArrayAdapter<AppUser> {

        public UserListAdapter(BaseActivity baseActivity) {
            super(baseActivity, R.layout.list_item_user_invite, appUsers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            SearchUserWrapper wrapper;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_user_invite, parent, false);
                wrapper = new SearchUserWrapper(convertView, getFragment());
                convertView.setTag(wrapper);
            } else {
                wrapper = (SearchUserWrapper) convertView.getTag();
            }
            wrapper.setData(appUsers.get(position));
            return convertView;
        }
    }
}

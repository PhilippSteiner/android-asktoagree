package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.MainActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.interfaces.FriendFragment;
import com.steiner_consult.models.AppUser;
import com.steiner_consult.models.Prayer;
import com.steiner_consult.workers.CreatePrayerWorker;
import com.steiner_consult.workers.FriendsWorker;
import com.steiner_consult.wrappers.ShareFriendWrapper;

import java.util.ArrayList;

/**
 * Created by Philipp on 29.01.15.
 */
public class CreatePrayerFragment extends BaseFragment implements View.OnClickListener, FriendFragment {

    private Button createPrayerButton;
    private EditText title, text;
    private CreatePrayerWorker createPrayerWorker;
    private CheckBox privacy;
    private FriendsWorker friendsWorker;
    private ArrayList<AppUser> friends = new ArrayList<>();
    private ListView friendsListView;
    private UserListAdapter userListAdapter;



    public static CreatePrayerFragment newInstance() {
        return new CreatePrayerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_myprayer_create, container, false);
        title = (EditText) rootView.findViewById(R.id.myprayer_title);
        text = (EditText) rootView.findViewById(R.id.myprayer_text);
        privacy = (CheckBox) rootView.findViewById(R.id.privacy);
        privacy.setOnClickListener(this);
        createPrayerButton = (Button) rootView.findViewById(R.id.myprayer_button_create);
        createPrayerButton.setOnClickListener(this);
        friendsListView = (ListView) rootView.findViewById(R.id.listview);
        userListAdapter = new UserListAdapter((BaseActivity) getActivity());
        friendsListView.setAdapter(userListAdapter);
        friendsWorker = new FriendsWorker(this);
        friendsWorker.loadFriendsToSharePrayer();
        return rootView;
    }


    private void createPrayer() {
        Prayer prayer = new Prayer();
        prayer.setTitle(title.getText().toString());
        prayer.setText(text.getText().toString());
        prayer.setPrivacy(privacy.isChecked());
        for(AppUser friend : friends) {
            if(friend.isPrayerSharedWith())
            prayer.getSharedwith().add(friend);
        }
        createPrayerWorker = new CreatePrayerWorker((MainActivity) getActivity());
        createPrayerWorker.createPrayer(prayer);

    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.myprayer_button_create)
            createPrayer();
        if (v.getId() == R.id.privacy) {
            showFriendsList(privacy.isChecked());
        }
    }

    public void handleCheckboxClick(int position, boolean isChecked) {
        friends.get(position).setPrayerSharedWith(isChecked);
    }

    private void showFriendsList(boolean hide) {
        if (hide)
            friendsListView.setVisibility(View.GONE);
        else
            friendsListView.setVisibility(View.VISIBLE);
    }

    private CreatePrayerFragment getFragment() {
        return this;
    }

    @Override
    public void setListAdapterData(ArrayList<AppUser> appUsers) {
        this.friends = appUsers;
        userListAdapter.clear();
        userListAdapter.addAll(appUsers);
        userListAdapter.notifyDataSetChanged();
    }

    private class UserListAdapter extends ArrayAdapter<AppUser> {

        public UserListAdapter(BaseActivity baseActivity) {
            super(baseActivity, R.layout.list_item_dialog_friend, friends);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ShareFriendWrapper wrapper;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_dialog_friend, parent, false);
                wrapper = new ShareFriendWrapper(convertView, getFragment());
                convertView.setTag(wrapper);
            } else {
                wrapper = (ShareFriendWrapper) convertView.getTag();
            }
            wrapper.setData(friends.get(position), position);
            return convertView;
        }
    }
}

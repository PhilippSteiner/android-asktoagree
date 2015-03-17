package com.steiner_consult.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.steiner_consult.wrappers.FriendsWrapper;
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
    private ArrayList<AppUser> appUsers;
    private View dialogView;
    private ListView dialogList;
    private UserListAdapter userListAdapter;
    private AlertDialog dialog;


    public static CreatePrayerFragment newInstance() {
        return new CreatePrayerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_myprayer_create, container, false);
        dialogView = inflater.inflate(R.layout.dialog_layout_friends_share, null);
        title = (EditText) rootView.findViewById(R.id.myprayer_title);
        text = (EditText) rootView.findViewById(R.id.myprayer_text);
        privacy = (CheckBox) rootView.findViewById(R.id.privacy);
        privacy.setOnClickListener(this);
        createPrayerButton = (Button) rootView.findViewById(R.id.myprayer_button_create);
        createPrayerButton.setOnClickListener(this);
        creatShareDialog();
        friendsWorker = new FriendsWorker(this);
        friendsWorker.loadFriendsToSharePrayer();
        return rootView;
    }

    private void creatShareDialog() {
        userListAdapter = new UserListAdapter((BaseActivity) getActivity());
        dialogList = (ListView) dialogView.findViewById(R.id.listview);
        dialogList.setAdapter(userListAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView)
                .setTitle(R.string.dialog_share_title)
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        dialog = builder.create();
    }

    private void createPrayer() {
        Prayer prayer = new Prayer();
        prayer.setTitle(title.getText().toString());
        prayer.setText(text.getText().toString());

        createPrayerWorker = new CreatePrayerWorker((MainActivity) getActivity());
        createPrayerWorker.createPrayer(prayer);

    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }

    private void triggerAlertDialog() {
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.myprayer_button_create)
            createPrayer();
        if (v.getId() == R.id.privacy) {
            if (privacy.isChecked()) {

            } else {
                triggerAlertDialog();
            }
        }
    }

    @Override
    public void setListAdapterData(ArrayList<AppUser> appUsers) {
        this.appUsers = appUsers;
        userListAdapter.clear();
        userListAdapter.addAll(appUsers);
        userListAdapter.notifyDataSetChanged();
    }

    private class UserListAdapter extends ArrayAdapter<AppUser> {

        public UserListAdapter(BaseActivity baseActivity) {
            super(baseActivity, R.layout.list_item_dialog_friend, appUsers);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ShareFriendWrapper wrapper;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_dialog_friend, parent, false);
                wrapper = new ShareFriendWrapper(convertView);
                convertView.setTag(wrapper);
            } else {
                wrapper = (ShareFriendWrapper) convertView.getTag();
            }
            wrapper.setData(appUsers.get(position));
            return convertView;
        }
    }
}

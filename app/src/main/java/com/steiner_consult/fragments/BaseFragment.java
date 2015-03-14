package com.steiner_consult.fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.slider.TabItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Philipp on 31.01.15.
 */
public abstract class BaseFragment extends Fragment {

    protected List<TabItem> tabsList = new ArrayList<TabItem>();

    protected ProgressDialog progressDialog;


    public ProgressDialog getProgressDialog() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
        }
        return progressDialog;
    }

    public abstract void goToFragment(BaseFragment baseFragment);


    protected void initializeInviteTabs() {
        int[] threeTabNames = { R.string.tab_friends, R.string.tab_request, R.string.tab_respond};
        addTabs(threeTabNames);
    }

    private void addTabs(int[] tabTexte) {
        Locale l = Locale.getDefault();
        for( int tabText : tabTexte){
            tabsList.add(new TabItem(
                    getActivity().getString(tabText).toUpperCase(l),
                    getResources().getColor(R.color.background_green),
                    getResources().getColor(R.color.background_green)
            ));
        }
    }

}

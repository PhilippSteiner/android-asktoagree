package com.steiner_consult.fragments;

import android.support.v4.app.Fragment;
import android.widget.ProgressBar;

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

    protected ProgressBar progressBar;

    public abstract void goToFragment(BaseFragment baseFragment);

    public ProgressBar getLoadingBar() {
        return progressBar;
    }

}

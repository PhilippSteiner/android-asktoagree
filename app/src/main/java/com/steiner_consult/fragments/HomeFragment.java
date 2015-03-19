package com.steiner_consult.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.*;
import android.widget.TextView;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.workers.BibelWorker;

/**
 * Created by Philipp on 19.03.15.
 */
public class HomeFragment extends BaseFragment {

    private TextView versesTextView;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout_home, container, false);

        versesTextView = (TextView) rootView.findViewById(R.id.verses);

        BibelWorker bibelWorker = new BibelWorker(this);
        bibelWorker.loadBibleText();

        return rootView;
    }

    public void setText(String verses) {
        versesTextView.setText(Html.fromHtml(verses));
    }

    @Override
    public void goToFragment(BaseFragment baseFragment) {

    }
}

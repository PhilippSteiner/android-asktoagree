package com.steiner_consult.asktoagree;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by Philipp on 22.12.14.
 */
public class BaseActivity extends ActionBarActivity {

    private ProgressDialog progressDialog;


    public ProgressDialog getProgressDialog() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
        }
        return progressDialog;
    }
}

package com.steiner_consult.asktoagree;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Philipp on 22.12.14.
 */
public class BaseActivity extends Activity {

    private ProgressDialog progressDialog;


    public ProgressDialog getProgressDialog() {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");
        }
        return progressDialog;
    }
}

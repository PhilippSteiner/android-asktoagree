package com.steiner_consult.workers;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.models.Prayer;

/**
 * Created by Philipp on 31.01.15.
 */
public class CreatePrayerWorker {

    private final String TAG = this.getClass().getName();
    private BaseActivity baseActivity;
    private Prayer prayer;

    public CreatePrayerWorker(BaseActivity activity) {
        this.baseActivity = activity;
    }

    public void createPrayer(Prayer prayer) {
        this.prayer = prayer;
    }
}

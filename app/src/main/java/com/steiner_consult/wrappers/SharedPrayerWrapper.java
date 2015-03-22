package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.Prayer;


/**
 * Created by Philipp on 22.03.15.
 */
public class SharedPrayerWrapper extends BasePrayerWrapper {

    private Button agree;

    public SharedPrayerWrapper(View row) {
        super(row);
        agree = (Button) row.findViewById(R.id.agree);

    }

    @Override
    public void setData(Prayer prayer) {
        super.setData(prayer);
        agree.setEnabled(!prayer.isAgreed());
    }

}

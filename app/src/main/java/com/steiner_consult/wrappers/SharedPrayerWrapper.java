package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.Button;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.fragments.SharedPrayersFragment;
import com.steiner_consult.models.Prayer;


/**
 * Created by Philipp on 22.03.15.
 */
public class SharedPrayerWrapper extends BasePrayerWrapper {

    private Button agree;
    private SharedPrayersFragment sharedPrayersFragment;

    public SharedPrayerWrapper(View row, SharedPrayersFragment sharedPrayersFragment) {
        super(row);
        this.sharedPrayersFragment = sharedPrayersFragment;
        agree = (Button) row.findViewById(R.id.agree);

    }

    @Override
    public void setData(final Prayer prayer) {
        super.setData(prayer);
        agree.setEnabled(!prayer.isAgreed());
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrayersFragment.agreeToPrayer(prayer);
            }
        });
    }

}

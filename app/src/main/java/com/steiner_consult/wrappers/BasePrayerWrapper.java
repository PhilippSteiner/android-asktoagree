package com.steiner_consult.wrappers;

import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.Prayer;

/**
 * Created by Philipp on 22.03.15.
 */
public abstract class BasePrayerWrapper {

    private TextView title, created, creator;

    public BasePrayerWrapper(View row) {
        title = (TextView) row.findViewById(R.id.title);
        created = (TextView) row.findViewById(R.id.created);
        creator = (TextView) row.findViewById(R.id.creator);
    }

    public void setData(Prayer prayer) {
        title.setText(prayer.getTitle());
        created.setText(DateUtils.getRelativeTimeSpanString(prayer.getCreationDate().getTime(), System.currentTimeMillis(), DateUtils.MINUTE_IN_MILLIS));
        creator.setText("by " + prayer.getCreator().getUsername());
    }

}

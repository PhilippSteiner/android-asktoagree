package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.Prayer;

/**
 * Created by Philipp on 07.03.15.
 */
public class MyPrayerWrapper {

    private View row;
    private TextView title, text;
    private Prayer prayer;

    public MyPrayerWrapper(View row) {
        this.row = row;
    }

    public void setData(Prayer prayer) {
        this.prayer = prayer;
        title = (TextView) row.findViewById(R.id.title);
        text = (TextView) row.findViewById(R.id.text);

        setTitle();
    }

    private void setTitle() {
        title.setText(prayer.getTitle());
    }

    private void setText() {
        text.setText(prayer.getText());
    }
}

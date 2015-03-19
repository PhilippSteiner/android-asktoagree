package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.fragments.TopPrayerFragment;
import com.steiner_consult.models.Prayer;

import org.w3c.dom.Text;

/**
 * Created by Philipp on 19.03.15.
 */
public class TopPrayerWrapper {

    private TextView title, creator, agreed;
    private Prayer prayer;

    public TopPrayerWrapper(View row) {
        title = (TextView) row.findViewById(R.id.title);
        creator = (TextView) row.findViewById(R.id.creator);

    }

    public void setData(Prayer prayer) {
        this.prayer = prayer;
        setTitle();
    }

    private void setTitle() {
        title.setText(prayer.getTitle());
    }
}

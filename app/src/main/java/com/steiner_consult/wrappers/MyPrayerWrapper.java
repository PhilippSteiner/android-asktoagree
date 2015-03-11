package com.steiner_consult.wrappers;

import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.TextView;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.fragments.MyPrayerPagerFragment;
import com.steiner_consult.fragments.ViewPrayerFragment;
import com.steiner_consult.models.Prayer;

/**
 * Created by Philipp on 07.03.15.
 */
public class MyPrayerWrapper implements View.OnClickListener {

    private View row;
    private TextView title, text, created;
    private Prayer prayer;
    private BaseActivity baseActivity;

    public MyPrayerWrapper(View row, BaseActivity baseActivity) {
        this.row = row;
        this.baseActivity = baseActivity;
    }

    public void setData(Prayer prayer) {
        this.prayer = prayer;
        title = (TextView) row.findViewById(R.id.title);
        text = (TextView) row.findViewById(R.id.text);
        created = (TextView) row.findViewById(R.id.created);

        setTitle();
        setCreated();
        row.setOnClickListener(this);
    }

    private void setTitle() {
        title.setText(prayer.getTitle());
    }

    private void setText() {
        text.setText(prayer.getText());
    }

    private void setCreated() {
        created.setText(prayer.getCreationDate().toString());
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = baseActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, ViewPrayerFragment.newInstance(prayer))
                .addToBackStack(MyPrayerPagerFragment.class.getName())
                .commit();
    }
}

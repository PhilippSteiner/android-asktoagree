package com.steiner_consult.wrappers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.fragments.MyPrayerPagerFragment;
import com.steiner_consult.fragments.ViewPrayerFragment;
import com.steiner_consult.models.Prayer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Philipp on 07.03.15.
 */
public class MyPrayerWrapper extends BasePrayerWrapper implements View.OnClickListener {

    private View row;
    private TextView shared, agreed;
    private BaseActivity baseActivity;
    private MyPrayerPagerFragment myPrayerPagerFragment;

    public MyPrayerWrapper(View row, MyPrayerPagerFragment myPrayerPagerFragment) {
        super(row);
        this.row = row;
        this.myPrayerPagerFragment = myPrayerPagerFragment;
        this.baseActivity = (BaseActivity) myPrayerPagerFragment.getActivity();
        shared = (TextView) row.findViewById(R.id.shared);
        agreed = (TextView) row.findViewById(R.id.agreed);
    }

    @Override
    public void setData(Prayer prayer) {
        super.setData(prayer);
        setShared();
        setAgreed();
        row.setOnClickListener(this);
        row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                createDeleteDialog().show();
                return true;
            }
        });
    }


    private void setShared() {
        if (!prayer.isPrivacy())
            shared.setText(prayer.getSharedwith().size() + " shares");
    }

    private void setAgreed() {
        agreed.setText(prayer.getAgreedwith().size() + " agreed");
    }

    private AlertDialog createDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        builder.setTitle(R.string.delete_prayer)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myPrayerPagerFragment.deletePrayer(prayer);
                        dialog.cancel();
                    }
                });
        return builder.create();
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

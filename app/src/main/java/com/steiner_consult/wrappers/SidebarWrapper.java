package com.steiner_consult.wrappers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.SidebarItem;

/**
 * Created by Philipp on 27.01.15.
 */
public class SidebarWrapper {

    private View row;
    private TextView name;
    private ImageView icon;
    private SidebarItem sidebarItem;

    public SidebarWrapper(View row) {
        this.row = row;
    }

    public void setData(SidebarItem sidebarItem) {
        this.sidebarItem = sidebarItem;
        name = (TextView) row.findViewById(R.id.sidebar_name);
        icon = (ImageView) row.findViewById(R.id.sidebar_icon);

        setName();
        setIcon();
    }

    private void setName() {
        name.setText(sidebarItem.getName());
    }

    private void setIcon() {
        icon.setImageResource(sidebarItem.getIcon());
    }
}

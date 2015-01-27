package com.steiner_consult.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.steiner_consult.asktoagree.BaseActivity;
import com.steiner_consult.asktoagree.R;
import com.steiner_consult.models.SidebarItem;
import com.steiner_consult.wrappers.SidebarWrapper;

import java.util.List;

/**
 * Created by Philipp on 27.01.15.
 */
public class SidebarAdapter extends ArrayAdapter<SidebarItem> {

    private BaseActivity baseActivity;


    public SidebarAdapter(BaseActivity baseActivity, List<SidebarItem> sidebarItemList) {
        super(baseActivity, R.layout.list_item_sidebaritems, sidebarItemList);
        this.baseActivity = baseActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SidebarWrapper sidebarWrapper;
        SidebarItem sidebarItem = getItem(position);

        if(convertView == null) {
            convertView = baseActivity.getLayoutInflater().inflate(R.layout.list_item_sidebaritems, null);
            sidebarWrapper = new SidebarWrapper(convertView);
            convertView.setTag(sidebarWrapper);
        } else {
            sidebarWrapper = (SidebarWrapper) convertView.getTag();
        }
        sidebarWrapper.setData(sidebarItem);
        return convertView;
    }
}

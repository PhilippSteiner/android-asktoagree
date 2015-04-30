package com.steiner_consult.asktoagree;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.steiner_consult.adapters.SidebarAdapter;
import com.steiner_consult.fragments.FriendsPagerFragment;
import com.steiner_consult.fragments.HomeFragment;
import com.steiner_consult.fragments.MyPrayerPagerFragment;

import com.steiner_consult.fragments.ProfileFragment;
import com.steiner_consult.fragments.SharedPrayersFragment;
import com.steiner_consult.fragments.TopPrayerFragment;
import com.steiner_consult.models.SidebarItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipp on 27.01.15.
 */
public class SidebarDrawer {

    private BaseActivity baseActivity;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private SidebarAdapter sidebarAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private TextView userName;

    public SidebarDrawer(BaseActivity baseActivity, Toolbar toolbar) {
        this.baseActivity = baseActivity;
        this.toolbar = toolbar;
    }

    public void initializeSidebarDrawer() {
        drawerLayout = (DrawerLayout) baseActivity.findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(baseActivity, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.setDrawerListener(drawerToggle);
        drawerList = (ListView) baseActivity.findViewById(R.id.left_drawer);
        LayoutInflater inflater = baseActivity.getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.drawer_header, drawerList, false);
        drawerList.addHeaderView(header);
        sidebarAdapter = new SidebarAdapter(baseActivity, getSidebarItemList());
        drawerList.setAdapter(sidebarAdapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        userName = (TextView) header.findViewById(R.id.username);
        userName.setText(baseActivity.getUserFromPreferences().getUsername());
    }

    private List<SidebarItem> getSidebarItemList() {
        List<SidebarItem> sidebarItemList = new ArrayList<>();
        sidebarItemList.add(new SidebarItem("Top Prayers", R.drawable.ic_drawer_top));
        sidebarItemList.add(new SidebarItem("My Prayers", R.drawable.ic_drawer_my));
        sidebarItemList.add(new SidebarItem("Brothers and Sisters", R.drawable.ic_drawer_invite));
        sidebarItemList.add(new SidebarItem("Home", R.drawable.ic_drawer_top));
        sidebarItemList.add(new SidebarItem("My Shared Prayers", R.drawable.ic_drawer_invite));
        return sidebarItemList;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Log.d(getClass().getName(), "Sidebar: " + position);
        if (position > 0) {
            SidebarItem sidebarItem = sidebarAdapter.getItem(position - 1);
            setTitle(sidebarItem.getName());
        } else {
            setTitle("Profile");
        }

        Fragment fragment;

        switch (position) {
            case 0: fragment = ProfileFragment.newInstance();
                break;
            case 1: fragment = TopPrayerFragment.newInstance();
                break;
            case 2: fragment = MyPrayerPagerFragment.newInstance();
                break;
            case 3: fragment = FriendsPagerFragment.newInstance();
                break;
            case 4: fragment = HomeFragment.newInstance();
                break;
            case 5: fragment = SharedPrayersFragment.newInstance();
                break;
            default: fragment = MyPrayerPagerFragment.newInstance();
                break;
        }

        FragmentManager fragmentManager = baseActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);

    }

    private void setTitle(String name) {
        baseActivity.getSupportActionBar().setTitle(name);
    }

    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

}

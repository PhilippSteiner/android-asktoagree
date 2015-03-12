package com.steiner_consult.asktoagree;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.steiner_consult.adapters.SidebarAdapter;
import com.steiner_consult.fragments.InviteFragment;
import com.steiner_consult.fragments.MyPrayerPagerFragment;
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



    public SidebarDrawer(BaseActivity baseActivity) {
        this.baseActivity = baseActivity;
    }

    public void initializeSidebarDrawer() {
        drawerLayout = (DrawerLayout) baseActivity.findViewById(R.id.drawer_layout);
        drawerList = (ListView) baseActivity.findViewById(R.id.left_drawer);
        sidebarAdapter = new SidebarAdapter(baseActivity, getSidebarItemList());
        drawerList.setAdapter(sidebarAdapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        //drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        drawerToggle = new ActionBarDrawerToggle(
                baseActivity,          /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };

        drawerLayout.setDrawerListener(drawerToggle);
    }

    private List<SidebarItem> getSidebarItemList() {
        List<SidebarItem> sidebarItemList = new ArrayList<>();
        sidebarItemList.add(new SidebarItem("Top Prayers", R.drawable.ic_drawer_top));
        sidebarItemList.add(new SidebarItem("My Prayers", R.drawable.ic_drawer_my));
        sidebarItemList.add(new SidebarItem("Invite Brothers and Sisters", R.drawable.ic_drawer_invite));
        return sidebarItemList;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        SidebarItem sidebarItem = sidebarAdapter.getItem(position);
        Fragment fragment;

        switch (position) {
            case 0: fragment = MyPrayerPagerFragment.newInstance();
                break;
            case 1: fragment = MyPrayerPagerFragment.newInstance();
                break;
            case 2: fragment = InviteFragment.newInstance();
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
        setTitle(sidebarItem.getName());
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

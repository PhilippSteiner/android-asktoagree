package com.steiner_consult.models;

/**
 * Created by Philipp on 27.01.15.
 */
public class SidebarItem {

    private String name;
    private int icon;

    public SidebarItem(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

package com.example.foodorderapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private int tabCount;
    public ViewPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i) {
        if(i == 0){
            return new total_order_frag();
        }else if(i == 1){
            return new total_pending_order_frag();
        }else if(i == 2) {
            return new total_completed_order_frag();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

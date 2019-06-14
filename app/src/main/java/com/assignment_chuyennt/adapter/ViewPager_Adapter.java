package com.assignment_chuyennt.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.assignment_chuyennt.fragment.Fragment_Hotel;
import com.assignment_chuyennt.fragment.Fragment_Room;

public class ViewPager_Adapter extends FragmentStatePagerAdapter {
    public ViewPager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new Fragment_Hotel();
                break;
            case 1:
                fragment = new Fragment_Room();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Hotel";
                break;
            case 1:
                title = "Room";
                break;
        }
        return title;
    }
}

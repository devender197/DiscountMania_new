package com.virtualskillset.discountmania.user;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    private ArrayList<Fragment> fragments;
    public FragmentAdapter(FragmentManager fm, int NumOfTabs, ArrayList<Fragment> fragments) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
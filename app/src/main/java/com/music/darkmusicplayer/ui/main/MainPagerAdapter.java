package com.music.darkmusicplayer.ui.main;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.music.darkmusicplayer.ui.base.BaseFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;
    private BaseFragment[] mBaseFragments;


    public MainPagerAdapter(FragmentManager fm, String[] mTitles, BaseFragment[] mBaseFragments) {
        super(fm);
        //uper(fm);
        this.mTitles = mTitles;
        this.mBaseFragments = mBaseFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mBaseFragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        if (mTitles == null) return 0;
        return mTitles.length;
    }
}

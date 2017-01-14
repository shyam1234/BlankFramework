package com.malviya.blankframework.adapters;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.malviya.blankframework.fragments.MainHomeFragment;
import com.malviya.blankframework.fragments.WardFragment;
import com.malviya.blankframework.fragments.OptionsFragment;

/**
 * Created by Admin on 24-12-2016.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return  new MainHomeFragment();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return  new WardFragment();
            case 2: // Fragment # 1 - This will show SecondFragment
                return  new OptionsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }



}

package com.malviya.blankframework.activities;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.MyPagerAdapter;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


/**
 * Created by Admin on 24-12-2016.
 */

public class NewDashboard extends AppCompatActivity implements OnTabSelectListener, ViewPager.OnPageChangeListener {

    private FrameLayout mContainer;
    private BottomBar mBottomBar;
    private ViewPager mViewPage;
    private MyPagerAdapter mAdapterViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dashboard);
        init();
        initView();
        initRegistration();

    }

    private void init() {
        mAdapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
    }


    private void initView() {
        mContainer = (FrameLayout) findViewById(R.id.contentContainer);
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);
        mViewPage = (ViewPager) findViewById(R.id.vpPager);
        mViewPage.addOnPageChangeListener(this);
    }


    private void initRegistration() {
        mBottomBar.setOnTabSelectListener(this);
        mViewPage.setAdapter(mAdapterViewPager);

        //You can easily add badges for showing an unread message count or new items / whatever you like.
        // BottomBarTab nearby = mBottomBar.getTabWithId(R.id.tab_home);
        // nearby.setBadgeCount(5);
        //nearby.removeBadge/();

    }


    @Override
    public void onTabSelected(@IdRes int tabId) {
//        FragmentTransaction lFragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (tabId) {
            case R.id.tab_home:
                //lFragmentTransaction.replace(R.id.contentContainer, new HomeFragment());
                mViewPage.setCurrentItem(0);
                break;
            case R.id.tab_profile:
                mViewPage.setCurrentItem(1);
                break;
            case R.id.tab_setting:
                mViewPage.setCurrentItem(2);
                break;
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Toast.makeText(NewDashboard.this, "Selected page position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageSelected(int position) {
        mBottomBar.selectTabAtPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Toast.makeText(NewDashboard.this, "Selected page position: " + state, Toast.LENGTH_SHORT).show();
    }
}

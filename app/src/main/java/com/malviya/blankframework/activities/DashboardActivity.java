package com.malviya.blankframework.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.MyPagerAdapter;
import com.malviya.blankframework.fragments.HomeFragment;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.Utils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


/**
 * Created by Admin on 24-12-2016.
 */

public class DashboardActivity extends AppCompatActivity implements OnTabSelectListener, ViewPager.OnPageChangeListener, Handler.Callback {
    private final int NOTICE_BOARD = 0;
    private final int ATTENDANCE = 1;
    private final int HOMEWORK = 2;
    private final int DIARY = 3;
    private final int MESSAGE = 4;
    private final int EVENTS = 5;
    private final int GALLARY = 6;
    private final int FEEDBACK = 7;
    private final int FEE = 8;
    //private FrameLayout mContainer;
    private BottomBar mBottomBar;
    private ViewPager mViewPage;
    private MyPagerAdapter mAdapterViewPager;
    private ImageView mImgProfile;
    private TextView mTextViewTitle;
    private Handler mHandlerNavigationToDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dashboard);
        init();
        initView();
        initRegistration();

    }

    private void init() {
        mHandlerNavigationToDetail = new Handler(this);
        mAdapterViewPager = new MyPagerAdapter(getSupportFragmentManager(), mHandlerNavigationToDetail);
    }


    private void initView() {
        mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mImgProfile = (ImageView) findViewById(R.id.imageview_profile);
        //----------------------------------------------------------------------------


        // mContainer = (FrameLayout) findViewById(R.id.contentContainer);
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
                mTextViewTitle.setText(getResources().getString(R.string.tab_home));
                break;
            case R.id.tab_ward:
                mViewPage.setCurrentItem(1);
                mTextViewTitle.setText(getResources().getString(R.string.tab_wards));
                break;
            case R.id.tab_setting:
                mViewPage.setCurrentItem(2);
                mTextViewTitle.setText(getResources().getString(R.string.tab_options));
                break;
        }

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //Toast.makeText(DashboardActivity.this, "Selected page position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageSelected(int position) {
        mBottomBar.selectTabAtPosition(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //Toast.makeText(DashboardActivity.this, "Selected page position: " + state, Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean handleMessage(Message message) {
        int index = (int) message.obj;
        Toast.makeText(this, "position " + index, Toast.LENGTH_SHORT).show();
        switch (index) {
            case NOTICE_BOARD:
                // navigateFragment(new NoticeboardFragment());
                break;
            case ATTENDANCE:
                break;
            case HOMEWORK:
                break;
            case DIARY:
                break;
            case MESSAGE:
                break;
            case EVENTS:
                break;
            case GALLARY:
                break;
            case FEEDBACK:
                break;
            case FEE:
                break;

        }
        return false;
    }

//    private void navigateFragment(Fragment fragment) {
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.replace(R.id.lin_home, fragment);
//        ft.commit();
//        ft.setCustomAnimations(R.anim.left, R.anim.right);
//    }


    @Override
    public void onBackPressed() {
        try {
            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                String name = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
                switch (mBottomBar.getCurrentTabPosition()) {
                    case 0:
                        switch (name) {
                            case HomeFragment.TAG:
                               // getSupportFragmentManager().popBackStack(HomeFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                finish();
                                Utils.animLeftToRight(DashboardActivity.this);
                                break;
                            default:
                                getSupportFragmentManager().popBackStack();
                                break;
                        }
                        break;
                    case 1:
                        mBottomBar.selectTabAtPosition(0);
                        break;
                    case 2:
                        mBottomBar.selectTabAtPosition(1);
                        break;
                }

                AppLog.log("onBackPressed", "" + getSupportFragmentManager().getBackStackEntryCount());
                AppLog.log("onBackPressed", "name " + name);
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                Utils.animLeftToRight(DashboardActivity.this);
                AppLog.log("onBackPressed", "finish ");
            }
        } catch (Exception e) {
            AppLog.errLog("onBackPressed", e.getMessage());
        }
    }
}

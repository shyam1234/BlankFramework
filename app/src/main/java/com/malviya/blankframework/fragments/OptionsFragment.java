package com.malviya.blankframework.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.activities.DashboardActivity;
import com.malviya.blankframework.activities.ProfileActivity;
import com.malviya.blankframework.activities.SettingActivity;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class OptionsFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout relShareTab;
    private RelativeLayout relSettingTab;
    private RelativeLayout relLogoutTab;
    private RelativeLayout relMyProfileTab;
    private TextView mTextViewTitle;
    private ImageView mImgProfile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_options, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        initView();
    }

    private void init() {
       /* DashboardActivity.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch ((Integer) msg.obj){
                    case 2:
                        initView();
                        return true;
                }
                return false;
            }
        });*/
    }

    private void initView() {
        mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_options);
        mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        //-----------------------------------------------------------
        relShareTab = (RelativeLayout) getView().findViewById(R.id.rel_options_share_app);
        relSettingTab = (RelativeLayout) getView().findViewById(R.id.rel_options_setting);
        relLogoutTab = (RelativeLayout) getView().findViewById(R.id.rel_options_logout);
        relMyProfileTab = (RelativeLayout) getView().findViewById(R.id.rel_options_profile);
        setListener();
    }

    private void setListener() {
        relShareTab.setOnClickListener(this);
        relSettingTab.setOnClickListener(this);
        relLogoutTab.setOnClickListener(this);
        relMyProfileTab.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_options_share_app:
                break;
            case R.id.rel_options_setting:
                navigateToNextPage(SettingActivity.class);
                break;
            case R.id.rel_options_logout:
                UserInfo.logout();
                break;
            case R.id.rel_options_profile:
                navigateToNextPage(ProfileActivity.class);
                break;
        }
    }


    private void navigateToNextPage(Class mClass) {
        Intent i = new Intent(getActivity(), mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }


}

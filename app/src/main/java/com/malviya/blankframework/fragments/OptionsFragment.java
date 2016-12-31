package com.malviya.blankframework.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class OptionsFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout relShareTab;
    private RelativeLayout relSettingTab;
    private RelativeLayout relLogoutTab;

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
        initView();
    }

    private void initView() {
        relShareTab = (RelativeLayout) getView().findViewById(R.id.rel_options_share_app);
        relSettingTab = (RelativeLayout) getView().findViewById(R.id.rel_options_setting);
        relLogoutTab = (RelativeLayout) getView().findViewById(R.id.rel_options_logout);
        setListener();
    }

    private void setListener() {
        relShareTab.setOnClickListener(this);
        relSettingTab.setOnClickListener(this);
        relLogoutTab.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_options_share_app:
                break;
            case R.id.rel_options_setting:
                break;
            case R.id.rel_options_logout:
                break;
        }
    }

}

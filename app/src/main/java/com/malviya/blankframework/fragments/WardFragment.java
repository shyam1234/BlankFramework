package com.malviya.blankframework.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.malviya.blankframework.R;
import com.malviya.blankframework.activities.DashboardActivity;
import com.malviya.blankframework.adapters.WardChildRowAdapter;
import com.malviya.blankframework.database.CommonInfo;
import com.malviya.blankframework.interfaces.ICallBack;
import com.malviya.blankframework.models.TableStudentDetailsDataModel;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.RenderImageByPicasso;
import com.malviya.blankframework.utils.SharedPreferencesApp;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class WardFragment extends Fragment  {
    private static final String TAG = "WardFragment";
    private FrameLayout mFramLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        AppLog.log("WardFragment","onCreate");
    }

    private void init() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_ward, null);
        AppLog.log("WardFragment","onCreateView");
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppLog.log("WardFragment","onActivityCreated");
        initView();
    }

    private void initView() {
        mFramLayout = (FrameLayout)getView().findViewById(R.id.framelayout_holder);
        Utils.navigateWardFragment(getFragmentManager(),new WardFragment1(),TAG);
    }

}


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
import android.widget.Toast;

import com.malviya.blankframework.R;
import com.malviya.blankframework.activities.DashboardActivity;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

/**
 * Created by Admin on 24-12-2016.
 */

public class FeedbackFragment extends Fragment implements View.OnClickListener {
    public final static String TAG="FeedbackFragment";
    public FeedbackFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_feedback, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        DashboardActivity.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch ((Integer) msg.what) {
                    case 1:
                        Toast.makeText(getContext(), "student id : " + UserInfo.studentId, Toast.LENGTH_SHORT).show();
                        DashboardActivity.mHandler.removeMessages(1);
                        initView();
           //             fetchDataFromServer();
                        return true;
                }
                return false;
            }
        });
    }

    private void initView() {

        setListener();
    }

    private void setListener() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    private void navigateToNextPage(Class mClass) {
        Intent i = new Intent(getActivity(), mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }


}

package com.malviya.blankframework.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.activities.DashboardActivity;
import com.malviya.blankframework.adapters.AttendanceAdapter;
import com.malviya.blankframework.models.AttendanceDataModel;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class AttendanceFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "AttendanceFragment";
    private RecyclerView mRecycleViewAttendance;
    private ArrayList<AttendanceDataModel> mAttendanceList;
    private AttendanceAdapter mAttendanceAdapter;
    private TextView mTextViewCourse;
    private TextView mTextViewTerm;

    public AttendanceFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mAttendanceList = new ArrayList<AttendanceDataModel>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_attendance, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        //------------------------------------
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_attendance);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        mImgProfile  = DashboardActivity.mImgProfile;
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        initRecyclerView();
        mTextViewCourse = (TextView) getView().findViewById(R.id.textview_attendance_course_value);
        mTextViewTerm = (TextView) getView().findViewById(R.id.textview_attendance_term_value);
        setListener();
    }

    private void initRecyclerView() {
        mRecycleViewAttendance = (RecyclerView) getView().findViewById(R.id.recyclerview_attendance);
        mRecycleViewAttendance.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewAttendance.setLayoutManager(manager);
        mAttendanceAdapter = new AttendanceAdapter(getContext(), mAttendanceList);
        mRecycleViewAttendance.setAdapter(mAttendanceAdapter);
    }

    private void setListener() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
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

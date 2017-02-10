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
import com.malviya.blankframework.adapters.AttendanceAdapter;
import com.malviya.blankframework.adapters.ResultAdapter;
import com.malviya.blankframework.models.AttendanceDataModel;
import com.malviya.blankframework.utils.RenderImageByPicasso;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class ResultFragment extends Fragment implements View.OnClickListener {
    public final static String TAG="ResultFragment";
    private TextView mTextViewSubjectSemster;
    private ImageView mImageViewBack;
    private TextView mTextViewTotalScore;
    private TextView mTextViewAchievementIndex;
    private ResultAdapter mAttendanceAdapter;
    private ArrayList<AttendanceDataModel> mAttendanceList;

    public ResultFragment() {

    }

    private RecyclerView mRecycleViewResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mAttendanceList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_results, null);
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
        mTextViewTitle.setText(R.string.tab_result);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        RenderImageByPicasso.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
         mTextViewSubjectSemster = (TextView) getView().findViewById(R.id.textview_results_subject_sem);
        mImageViewBack = (ImageView) getView().findViewById(R.id.imageview_results_arrow);
        mTextViewTotalScore = (TextView)getView().findViewById(R.id.textview_results_total_score_value);
        mTextViewAchievementIndex = (TextView)getView().findViewById(R.id.textview_results_achivement_index_value);
        initRecyclerView();
        setListener();
    }


    private void initRecyclerView() {
        mRecycleViewResult = (RecyclerView) getView().findViewById(R.id.recyclerview_results);
        mRecycleViewResult.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewResult.setLayoutManager(manager);
        mAttendanceAdapter = new ResultAdapter(getContext(), mAttendanceList);
        mRecycleViewResult.setAdapter(mAttendanceAdapter);
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

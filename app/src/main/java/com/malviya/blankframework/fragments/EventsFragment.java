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
import com.malviya.blankframework.adapters.EventsAdapter;
import com.malviya.blankframework.models.TableNewsMasterDataModel;
import com.malviya.blankframework.utils.GetPicassoImage;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class EventsFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "EventsFragment";
    private RecyclerView mRecycleViewNews;
    private ArrayList<TableNewsMasterDataModel> mNewsList;
    private EventsAdapter mNewsAdapter;

    public EventsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mNewsList = new ArrayList<TableNewsMasterDataModel>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_events, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        fetchDataFromServer();
    }

    private void fetchDataFromServer() {

    }

    private void initView() {
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_events);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        setListener();
        initRecyclerView();
    }


    private void initRecyclerView() {
        mRecycleViewNews = (RecyclerView) getView().findViewById(R.id.recyclerview_news);
        mRecycleViewNews.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewNews.setLayoutManager(manager);
        mNewsAdapter = new EventsAdapter(getContext(), mNewsList, this);
        mRecycleViewNews.setAdapter(mNewsAdapter);
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

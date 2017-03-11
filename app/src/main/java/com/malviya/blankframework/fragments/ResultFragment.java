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
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.activities.ResultListActivity;
import com.malviya.blankframework.adapters.ResultAdapter;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableStudentOverallResultSummary;
import com.malviya.blankframework.models.GetMobileMenuDataModel;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableResultMasterDataModel;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.parser.ParseResponse;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.GetPicassoImage;
import com.malviya.blankframework.utils.SharedPreferencesApp;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 24-12-2016.
 */

public class ResultFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "ResultFragment";
    public static String selected_sem = "";
    private TextView mTextViewSubjectSemster;
    private ImageView mImageViewBack;
    private TextView mTextViewTotalScore;
    private TextView mTextViewAchievementIndex;
    private ResultAdapter mResultAdapter;
    private ArrayList<TableResultMasterDataModel> mResultSummaryList;

    public ResultFragment() {

    }

    private RecyclerView mRecycleViewResult;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mResultSummaryList = new ArrayList<TableResultMasterDataModel>();
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
        fetchDataFromServer();
    }

    private void initView() {
        selected_sem = "";
        //------------------------------------
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_result);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        mTextViewSubjectSemster = (TextView) getView().findViewById(R.id.textview_results_subject_sem);
        mImageViewBack = (ImageView) getView().findViewById(R.id.imageview_results_arrow);
        mTextViewTotalScore = (TextView) getView().findViewById(R.id.textview_results_total_score_value);
        mTextViewAchievementIndex = (TextView) getView().findViewById(R.id.textview_results_achivement_index_value);
        initRecyclerView();
        setListener();
    }


    private void initRecyclerView() {
        mRecycleViewResult = (RecyclerView) getView().findViewById(R.id.recyclerview_results);
        mRecycleViewResult.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewResult.setLayoutManager(manager);
        mResultAdapter = new ResultAdapter(getContext(), mResultSummaryList);
        mRecycleViewResult.setAdapter(mResultAdapter);
    }


    private void setListener() {
        mImageViewBack.setOnClickListener(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        AppLog.log(TAG, "onResume");
        if (selected_sem.trim().length() > 0)
            mTextViewSubjectSemster.setText(selected_sem);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
            case R.id.imageview_results_arrow:
                navigateToNextPage(ResultListActivity.class);
                break;
        }
    }


    private void fetchDataFromServer() {
        TableStudentOverallResultSummary table = new TableStudentOverallResultSummary();
        table.openDB(getContext());
        mResultSummaryList = table.getData(UserInfo.menuCode);
        table.closeDB();
        //----------------------------------------------------------
        if (Utils.isInternetConnected(getContext())) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
            body.put(WSContant.TAG_PARENTID, "" + UserInfo.parentId);
            body.put(WSContant.TAG_USERID, "" + UserInfo.studentId);
            body.put(WSContant.TAG_USERTYPE, "" + UserInfo.currUserType);
            body.put(WSContant.TAG_REFERENCEDATE, "" + UserInfo.timeTableRefDate);
            body.put(WSContant.TAG_LASTRETRIEVED, "" + Utils.getLastRetrivedTimeForNews());
            Utils.showProgressBar(getContext());
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEMENU, header, body, WSContant.TAG_NEWS, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    mResultSummaryList.clear();
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_GETMOBILEMENU);
                    GetMobileMenuDataModel holder = ((GetMobileMenuDataModel) obj.getModel());
                    if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                        mResultSummaryList = holder.getMessageBody().getStudentOverallResultSummary();
                        saveDataIntoTable(holder);
                        SharedPreferencesApp.getInstance().saveLastLoginTime(Utils.getCurrTime());
                        initRecyclerView();
                    } else {
                        Toast.makeText(getContext(), R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
                    }
                    Utils.dismissProgressBar();
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    Utils.dismissProgressBar();
                }
            });

        } else {
            initRecyclerView();
        }
    }


    private void saveDataIntoTable(GetMobileMenuDataModel holder) {
        try {
            TableStudentOverallResultSummary table = new TableStudentOverallResultSummary();
            table.insert(holder.getMessageBody().getStudentOverallResultSummary());
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from saveDataIntoTable");
        }
    }


    private void navigateToNextPage(Class mClass) {
        Intent i = new Intent(getActivity(), mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }


}

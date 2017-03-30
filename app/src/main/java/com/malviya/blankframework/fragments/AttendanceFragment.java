package com.malviya.blankframework.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.malviya.blankframework.activities.AttendanceDetails;
import com.malviya.blankframework.activities.DashboardActivity;
import com.malviya.blankframework.adapters.AttendanceAdapter;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableAttendanceDetails;
import com.malviya.blankframework.models.AttendanceDataModel;
import com.malviya.blankframework.models.GetMobileAttendanceDetailDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableAttendanceDataModel;
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

public class AttendanceFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "AttendanceFragment";
    private RecyclerView mRecycleViewAttendance;
    private ArrayList<AttendanceDataModel> mAttendanceList;
    private AttendanceAdapter mAttendanceAdapter;
    private TextView mTextViewCourse;
    private TextView mTextViewTerm;
    private ArrayList<TableAttendanceDataModel> mAttendanceDetailsList;

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
        fetchDataFromServer();
        DashboardActivity.mHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch ((Integer) msg.what) {
                    case 1:
                        Toast.makeText(getContext(), "student id : " + UserInfo.studentId, Toast.LENGTH_SHORT).show();
                        DashboardActivity.mHandler.removeMessages(1);
                        initView();
                        return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        //------------------------------------
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_attendance);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
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
        mTextViewTerm.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
            case R.id.textview_attendance_term_value:
                navigateToNextPage(AttendanceDetails.class);
                break;
        }
    }


    private void fetchDataFromServer() {
        TableAttendanceDetails table = new TableAttendanceDetails();
        table.openDB(getContext());
        mAttendanceDetailsList = table.getValueBySem(UserInfo.menuCode);
        table.closeDB();
        //----------------------------------------------------------
        if (Utils.isInternetConnected(getContext())) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_DATELASTRETRIEVED, SharedPreferencesApp.getInstance().getLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCEDETAIL));
            header.put(WSContant.TAG_NEW, SharedPreferencesApp.getInstance().getLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCEDETAIL));
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_STUDENTID, "" + UserInfo.studentId);

            Utils.showProgressBar(getContext());
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEATTENDANCEDETAIL, header, body, WSContant.TAG_MOBILEATTENDANCEDETAIL, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    //mAttendanceDetailsList.clear();
                    ParseResponse obj = new ParseResponse(response, GetMobileAttendanceDetailDataModel.class, ModelFactory.MODEL_GETMOBILEATTDANCEDETAIL);
                    GetMobileAttendanceDetailDataModel holder = ((GetMobileAttendanceDetailDataModel) obj.getModel());
                    if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                        mAttendanceDetailsList = holder.getMessageBody().getStudentAttendanceDetailList();
                        saveDataIntoTable(holder);
                        SharedPreferencesApp.getInstance().setLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCEDETAIL,Utils.getCurrTime());
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


    private void saveDataIntoTable(GetMobileAttendanceDetailDataModel holder) {
        try {
            TableAttendanceDetails table = new TableAttendanceDetails();
            table.insert(holder.getMessageBody().getStudentAttendanceDetailList());
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from saveDataIntoTable " + e.getMessage());
        }
    }

    private void navigateToNextPage(Class mClass) {
        Intent i = new Intent(getActivity(), mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }
}

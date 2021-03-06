package com.malviya.blankframework.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.AttendanceDetailsActivityAdapter;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableAttendanceDetails;
import com.malviya.blankframework.models.TableAttendanceDetailsDataModel;
import com.malviya.blankframework.models.TableCourseMasterDataModel;
import com.malviya.blankframework.utils.GetPicassoImage;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;

/**
 * Created by Admin on 24-12-2016.
 */

public class AttendanceDetailActivity extends Activity implements View.OnClickListener {
    public final static String TAG = "AttendanceDetailActivity";
    private RecyclerView mRecycleViewAttendance;
    private TextView mTextViewCourse;
    private TextView mTextViewTerm;
    private ArrayList<TableAttendanceDetailsDataModel> mAttendanceDetailsList;
    private TableCourseMasterDataModel mStudentDetails;
    private AttendanceDetailsActivityAdapter mAttendanceAdapter;

    public AttendanceDetailActivity() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initView();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getSerializable(WSContant.TAG_NEW) != null) {
                mStudentDetails = (TableCourseMasterDataModel) bundle.getSerializable(WSContant.TAG_NEW);
            }
        }
        mAttendanceDetailsList = new ArrayList<>();
        fetchDataFromServer();
    }

    private void initView() {
        setContentView(R.layout.fragment_attendance_details);
        //------------------------------------
        TextView mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_attendance);
        ImageView mImgProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(AttendanceDetailActivity.this, UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        initRecyclerView();
        mTextViewCourse = (TextView) findViewById(R.id.textview_attendance_course_value);
        mTextViewTerm = (TextView) findViewById(R.id.textview_attendance_term_value);
        //--For Course and Term
        mTextViewCourse.setText(mStudentDetails.getCourse());
        mTextViewTerm.setText(mStudentDetails.getSemester());
        setListener();
    }

    private void initRecyclerView() {
        mRecycleViewAttendance = (RecyclerView) findViewById(R.id.recyclerview_attendance);
        mRecycleViewAttendance.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewAttendance.setLayoutManager(manager);

        mAttendanceAdapter = new AttendanceDetailsActivityAdapter(AttendanceDetailActivity.this, mAttendanceDetailsList);
        mRecycleViewAttendance.setAdapter(mAttendanceAdapter);

    }

    private void setListener() {
        mTextViewTerm.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.textview_attendance_term_value:
                //navigateToNextPage(AttendanceDetailCalendarActivity.class);
                break;
            case R.id.lin_attendance:
                int position = (Integer) view.getTag();
                navigateToNextPage(AttendanceDetailCalendarActivity.class, mAttendanceDetailsList.get(position));
                break;
        }
    }


    private void fetchDataFromServer() {
        TableAttendanceDetails table = new TableAttendanceDetails();
        table.openDB(this);
        mAttendanceDetailsList = table.getValue(mStudentDetails.getReferenceId());
        table.closeDB();
        /*//----------------------------------------------------------
        if (Utils.isInternetConnected(this)) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_DATELASTRETRIEVED, SharedPreferencesApp.getInstance().getLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCEDETAIL));
            header.put(WSContant.TAG_NEW, SharedPreferencesApp.getInstance().getLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCEDETAIL));
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_STUDENTID, "" + UserInfo.studentId);

            Utils.showProgressBar(this);
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
                        Toast.makeText(AttendanceDetailActivity.this, R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
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
        */
    }


    private void navigateToNextPage(Class mClass, TableAttendanceDetailsDataModel tableStudentAttendanceDetailListDataModel) {
        Intent i = new Intent(this, mClass);
        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WSContant.TAG_NEW, tableStudentAttendanceDetailListDataModel);
        i.putExtras(bundle);
        startActivity(i);
        Utils.animRightToLeft(this);
    }
}

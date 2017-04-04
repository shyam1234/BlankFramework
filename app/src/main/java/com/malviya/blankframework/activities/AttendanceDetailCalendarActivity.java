package com.malviya.blankframework.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableAbsenseDetails;
import com.malviya.blankframework.models.GetMobileAttendanceDetailDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableAbsenseDetailsDataModel;
import com.malviya.blankframework.models.TableAttendanceDetailsDataModel;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.parser.ParseResponse;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.CalendarView;
import com.malviya.blankframework.utils.GetPicassoImage;
import com.malviya.blankframework.utils.SharedPreferencesApp;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by 23508 on 2/7/2017.
 */

public class AttendanceDetailCalendarActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "AttendanceDetailCalendarActivity";
    private TableAttendanceDetailsDataModel mTableStudentAttendanceDetailListDataModel;
    private TextView mTextViewCourse;
    private TextView mTextViewSemster;
    private TextView mTextViewSubject;
    private ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel> mMessageBodyList;
    private CalendarView mCalendarView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);
        init();
        initView();
        fetchDataFromServer();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getSerializable(WSContant.TAG_NEW) != null) {
                mTableStudentAttendanceDetailListDataModel = (TableAttendanceDetailsDataModel) bundle.getSerializable(WSContant.TAG_NEW);
            }
        } else {
            mTableStudentAttendanceDetailListDataModel = new TableAttendanceDetailsDataModel();
        }
        mMessageBodyList = new ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel>();
    }

    private void initView() {
        //------------------------------------
        TextView mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_attendance);
        ImageView mImgProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(this, UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        mTextViewCourse = (TextView) findViewById(R.id.textview_attendance_detail_holder_term_course_value);
        mTextViewSemster = (TextView) findViewById(R.id.textview_attendance_detail_holder_term_value);
        mTextViewSubject = (TextView) findViewById(R.id.textview_attendance_detail_holder_subject_value);
        mCalendarView = (CalendarView) findViewById(R.id.calendar_view);

        mTextViewCourse.setText(mTableStudentAttendanceDetailListDataModel.getCourse());
        mTextViewSemster.setText(mTableStudentAttendanceDetailListDataModel.getSemester());
        mTextViewSubject.setText(mTableStudentAttendanceDetailListDataModel.getSubject());

    }


    private void refreshCalendar() {
        HashSet<Date> events = new HashSet<>();
        for (TableAbsenseDetailsDataModel.MessageBodyDataModel holder : mMessageBodyList) {
            events.add(Utils.getDate(holder.getAbsenceDate()));
        }
        mCalendarView.updateCalendar(events);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Utils.animLeftToRight(AttendanceDetailCalendarActivity.this);
    }


    private void fetchDataFromServer() {
        TableAbsenseDetails table = new TableAbsenseDetails();
        table.openDB(this);
        mMessageBodyList = table.getValue(mTableStudentAttendanceDetailListDataModel.getSubjectId(),
                mTableStudentAttendanceDetailListDataModel.getReferenceId());
        table.closeDB();
        //----------------------------------------------------------
        if (Utils.isInternetConnected(this)) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_DATELASTRETRIEVED, SharedPreferencesApp.getInstance().getLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCE_ABSENT));
            header.put(WSContant.TAG_NEW, Utils.getCurrTime());
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_REFERENCEID, "" + mTableStudentAttendanceDetailListDataModel.getReferenceId());
            body.put(WSContant.TAG_SUBJECTID, "" + mTableStudentAttendanceDetailListDataModel.getSubjectId());
            Utils.showProgressBar(this);
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEATTENDANCEABSENCEDETAIL, header, body, WSContant.TAG_MOBILEATTENDANCE_ABSENT, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    //mAttendanceDetailsList.clear();
                    ParseResponse obj = new ParseResponse(response, GetMobileAttendanceDetailDataModel.class, ModelFactory.MODEL_GETMOBILEATTDANCEABSENT);
                    TableAbsenseDetailsDataModel holder = ((TableAbsenseDetailsDataModel) obj.getModel());
                    if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                        mMessageBodyList = holder.getMessageBody();
                        saveDataIntoTable(holder.getMessageBody());
                        SharedPreferencesApp.getInstance().setLastRetrieveTime(WSContant.TAG_MOBILEATTENDANCE_ABSENT, Utils.getCurrTime());
                        refreshCalendar();
                    } else {
                        Toast.makeText(AttendanceDetailCalendarActivity.this, R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
                    }
                    Utils.dismissProgressBar();
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    Utils.dismissProgressBar();
                }
            });
        } else {
            refreshCalendar();
        }
    }


    private void saveDataIntoTable(ArrayList<TableAbsenseDetailsDataModel.MessageBodyDataModel> list) {
        try {
            TableAbsenseDetails table = new TableAbsenseDetails();
            table.openDB(this);
            table.insert(list);
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG, "saveDataIntoTable " + e.getMessage());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
        }
    }
}

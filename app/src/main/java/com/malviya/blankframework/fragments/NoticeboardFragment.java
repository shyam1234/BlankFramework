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
import com.malviya.blankframework.activities.DashboardActivity;
import com.malviya.blankframework.adapters.NoticeboardAdapter;
import com.malviya.blankframework.constant.Constant;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableNewsMaster;
import com.malviya.blankframework.database.TableNoticeBoard;
import com.malviya.blankframework.database.TableStudentOverallFeeSummary;
import com.malviya.blankframework.database.TableStudentOverallResultSummary;
import com.malviya.blankframework.models.GetMobileMenuDataModel;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableFeeMasterDataModel;
import com.malviya.blankframework.models.TableNewsMasterDataModel;
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
public class NoticeboardFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "NoticeboardFragment";
    private RecyclerView mRecycleViewNews;
    //private ArrayList<TableNoticeBoardDataModel> mNoticeboardList;
    private NoticeboardAdapter mNoticeboardAdapter;
    private ArrayList<Object> mCommonList;
    private TextView mTextViewTitle;

    public NoticeboardFragment() {
        AppLog.log(TAG, "NoticeboardFragment");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        AppLog.log(TAG, "onStart");
    }


    private void init() {
        //mNoticeboardList = new ArrayList<TableNoticeBoardDataModel>();
        mCommonList = new ArrayList<Object>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_news, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //AppLog.log(TAG, "onActivityCreated");
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
                        fetchDataFromServer();
                        return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_noticeboard);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        mRecycleViewNews = (RecyclerView) getView().findViewById(R.id.recyclerview_news);
        //------------------------------------
        //initRecyclerView();
        setLangSelection();

    }

    private void initRecyclerView() {
        // mRecycleViewNews = (RecyclerView) getView().findViewById(R.id.recyclerview_news);
        mRecycleViewNews.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewNews.setLayoutManager(manager);
        mNoticeboardAdapter = new NoticeboardAdapter(getContext(), mCommonList/*mNoticeboardList*/, this);
        mRecycleViewNews.setAdapter(mNoticeboardAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserInfo.menuCode = Constant.TAG_NOTICEBOARD;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
            case R.id.lin_noticeboard_row_activity_fee_row_holder:
                UserInfo.menuCode = Constant.TAG_FEE;
                int position = (Integer) view.getTag();
                Utils.navigateFragmentMenu(getFragmentManager(), new FeeFragment(), FeeFragment.TAG);
                break;
            case R.id.lin_noticeboard_row_fee_holder:
                UserInfo.menuCode = Constant.TAG_FEE;
                int position1 = (Integer) view.getTag();
                Utils.navigateFragmentMenu(getFragmentManager(), new FeeFragment(), FeeFragment.TAG);
                break;
            case R.id.lin_noticeboard_news_row_holder:
                UserInfo.menuCode = Constant.TAG_NEWS;
                int position2 = (Integer) view.getTag();
                Utils.navigateFragmentMenu(getFragmentManager(), new NewsFragment(), NewsFragment.TAG);
                break;
            case R.id.btn_view_pay_now:
                int position3 = (Integer) view.getTag();
                Toast.makeText(getContext(), "coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_download_details:
                int position4 = (Integer) view.getTag();
                Toast.makeText(getContext(), "coming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.lin_noticeboard_row_result_row_holder:
                UserInfo.menuCode = Constant.TAG_RESULT;
                int position5 = (Integer) view.getTag();
                Utils.navigateFragmentMenu(getFragmentManager(), new ResultFragment(), ResultFragment.TAG);
                break;

               /*
                switch (mCommonList.get(position).getMenuCode()) {
                    case Constant.TAG_FEE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new FeeFragment(), FeeFragment.TAG);
                        break;
                    case Constant.TAG_ATTENDANCE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new AttendanceFragment(), AttendanceFragment.TAG);
                        break;
                    case Constant.TAG_HOMEWORK:
                        Utils.navigateFragmentMenu(getFragmentManager(), new HomeworkFragment(), HomeworkFragment.TAG);
                        break;
                    case Constant.TAG_DIARY:
                        Utils.navigateFragmentMenu(getFragmentManager(), new DiaryFragment(), DiaryFragment.TAG);
                        break;
                    case Constant.TAG_MESSAGE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new MessageFragment(), MessageFragment.TAG);
                        break;
                    case Constant.TAG_EVENTS:
                        Utils.navigateFragmentMenu(getFragmentManager(), new EventsFragment(), EventsFragment.TAG);
                        break;
                    case Constant.TAG_GALLERY:
                        Utils.navigateFragmentMenu(getFragmentManager(), new GalleryFragment(), GalleryFragment.TAG);
                        break;
                    case Constant.TAG_FEEDBACK:
                        Utils.navigateFragmentMenu(getFragmentManager(), new FeedbackFragment(), FeedbackFragment.TAG);
                        break;
                    case Constant.TAG_NEWS:
                        Utils.navigateFragmentMenu(getFragmentManager(), new NewsFragment(), NewsFragment.TAG);
                        break;
                    case Constant.TAG_TIMETABLE:
                        Utils.navigateFragmentMenu(getFragmentManager(), new TimeTableFragment(), TimeTableFragment.TAG);
                        break;
                    case Constant.TAG_RESULT:
                        Utils.navigateFragmentMenu(getFragmentManager(), new ResultFragment(), ResultFragment.TAG);
                        break;
                }*/

        }
    }


    private void fetchDataFromServer() {
        if (Utils.isInternetConnected(getContext())) {
            //call to WS and validate given credential----
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            // header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
            //header.put(WSContant.TAG_NEW, Utils.getCurrTime());
            //-Utils-for body
            Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
            body.put(WSContant.TAG_PARENTID, "" + UserInfo.parentId);
            body.put(WSContant.TAG_USERID, "" + UserInfo.studentId);
            body.put(WSContant.TAG_USERTYPE, "" + UserInfo.currUserType);
            body.put(WSContant.TAG_REFERENCEDATE, "" + UserInfo.timeTableRefDate);
            body.put(WSContant.TAG_LASTRETRIEVED, "" + UserInfo.timeTableRefDate);//SharedPreferencesApp.getLastGetMobileMenuTime());
            Utils.showProgressBar(getContext());
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEMENU, header, body, WSContant.TAG_NEWS, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_GETMOBILEMENU);
                    GetMobileMenuDataModel holder = ((GetMobileMenuDataModel) obj.getModel());
                    bindData(holder);
                    Utils.dismissProgressBar();
                    AppLog.log(TAG, "fetchDataFromServe1r3333 " + mCommonList.size());
                }


                @Override
                public void onErrorResponse(VolleyError response) {
                    Utils.dismissProgressBar();
                }
            });
        } else {
            readFromTable();
            initRecyclerView();
        }

    }


    private void bindData(GetMobileMenuDataModel holder) {
        if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
            //mCommonList  = holder.getMessageBody().getNoticeBoardMenuList();
            //Collections.sort(mNoticeboardList,Collections.<TableNoticeBoardDataModel>reverseOrder());
            saveDataIntoTable(holder);
            readFromTable();
            SharedPreferencesApp.getInstance().saveGetMobileMenuTime(Utils.getCurrTime());
            initRecyclerView();
        } else {
            Toast.makeText(getContext(), R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
        }
    }

    private void readFromTable() {
        mCommonList.clear();
        TableNewsMaster table1 = new TableNewsMaster();
        table1.openDB(getContext());
        for (TableNewsMasterDataModel model : table1.getDataByStudent(UserInfo.studentId)) {
            mCommonList.add(model);
        }

        table1.closeDB();

        TableStudentOverallResultSummary table2 = new TableStudentOverallResultSummary();
        table2.openDB(getContext());
        for (TableResultMasterDataModel model123 : table2.getData(UserInfo.parentId, UserInfo.studentId)) {
            mCommonList.add(model123);
            AppLog.log(TAG, "getData+++ " + mCommonList.size() + " model123 " + model123);
        }
        table2.closeDB();

        TableStudentOverallFeeSummary table3 = new TableStudentOverallFeeSummary();
        table3.openDB(getContext());
        for (TableFeeMasterDataModel model3 : table3.getData(UserInfo.parentId, UserInfo.studentId)) {
            mCommonList.add(model3);
        }
        table3.closeDB();
    }

    private void saveDataIntoTable(GetMobileMenuDataModel holder) {
        try {
            //-------------------------------------------------------------
            TableNoticeBoard table = new TableNoticeBoard();
            table.openDB(getContext());
            table.insert(holder.getMessageBody().getNoticeBoardMenuList());
            table.closeDB();
            //-------------------------------------------------------------
            TableNewsMaster table1 = new TableNewsMaster();
            table1.openDB(getContext());
            table1.insert(holder.getMessageBody().getNewsMasterMenuList());
            table1.closeDB();

            TableStudentOverallResultSummary table2 = new TableStudentOverallResultSummary();
            table2.openDB(getContext());
            table2.insert(holder.getMessageBody().getStudentOverallResultSummary());
            table2.closeDB();

            TableStudentOverallFeeSummary table3 = new TableStudentOverallFeeSummary();
            table3.openDB(getContext());
            table3.insert(holder.getMessageBody().getStudentOverallFeeSummary());
            table3.closeDB();
            //-------------------------------------------------------------
        } catch (Exception e) {
            AppLog.errLog(TAG, " saveDataIntoTable " + e.getMessage());
        }
    }


    private void navigateToNextPage(Class<?> page) {
        Intent i = new Intent(getActivity(), page);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        //bundle.putSerializable(Constant.TAG_HOLDER, mNoticeboardList.get(position));
        i.putExtras(bundle);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }


    public void setLangSelection() {
        Utils.langConversion(getContext(), mTextViewTitle, new String[]{WSContant.TAG_LANG_NOTICEBOARD}, getString(R.string.tab_noticeboard), UserInfo.lang_pref);
    }
}

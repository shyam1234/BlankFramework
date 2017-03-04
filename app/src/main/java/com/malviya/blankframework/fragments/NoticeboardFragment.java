package com.malviya.blankframework.fragments;

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
import com.malviya.blankframework.adapters.NoticeboardAdapter;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableFeeMasterMaster;
import com.malviya.blankframework.database.TableNewsMaster;
import com.malviya.blankframework.database.TableNoticeBoard;
import com.malviya.blankframework.database.TableResultMaster;
import com.malviya.blankframework.models.GetMobileMenuDataModel;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableNoticeBoardDataModel;
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
    private ArrayList<TableNoticeBoardDataModel> mNoticeboardList;
    private NoticeboardAdapter mNoticeboardAdapter;

    public NoticeboardFragment() {
        AppLog.log(TAG, "NoticeboardFragment");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    private void init() {
        mNoticeboardList = new ArrayList<TableNoticeBoardDataModel>();
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
        AppLog.log(TAG, "onActivityCreated");
        initView();
        fetchDataFromServer();
    }

    private void initView() {
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_noticeboard);
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        mRecycleViewNews = (RecyclerView) getView().findViewById(R.id.recyclerview_news);
        //------------------------------------
        initRecyclerView();

    }

    private void initRecyclerView() {
        // mRecycleViewNews = (RecyclerView) getView().findViewById(R.id.recyclerview_news);
        mRecycleViewNews.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewNews.setLayoutManager(manager);
        mNoticeboardAdapter = new NoticeboardAdapter(getContext(), mNoticeboardList, this);
        mRecycleViewNews.setAdapter(mNoticeboardAdapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_back:
                getActivity().onBackPressed();
                break;
            case R.id.imageview_news_row_thumbnil:
                int position = (Integer) view.getTag();
                //on banner click redirect to detail page
                //navigateToNextPage(position);
                Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void fetchDataFromServer() {
        //call to WS and validate given credential----
        Map<String, String> header = new HashMap<>();
        header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
        // header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
        //header.put(WSContant.TAG_NEW, Utils.getCurrTime());
        //-Utils-for body
        Map<String, String> body = new HashMap<>();
        body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
        body.put(WSContant.TAG_PARENTID, "" + UserInfo.parentId);
        body.put(WSContant.TAG_USERID, "" + UserInfo.studentId);
        body.put(WSContant.TAG_USERTYPE, "" + UserInfo.currUserType);
        body.put(WSContant.TAG_LASTRETRIEVED, "" + Utils.getLastRetrivedTimeForNews());
        Utils.showProgressBar(getContext());
        WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEMENU, header, body, WSContant.TAG_NEWS, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                mNoticeboardList.clear();
                ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_GETMOBILEMENU);
                GetMobileMenuDataModel holder = ((GetMobileMenuDataModel) obj.getModel());
                AppLog.log(TAG, "fetchDataFromServe1r3333 " + mNoticeboardList.size());
                bindData(holder);
                Utils.dismissProgressBar();
            }


            @Override
            public void onErrorResponse(VolleyError response) {
                Utils.dismissProgressBar();
            }
        });
    }


    private void bindData(GetMobileMenuDataModel holder) {
        if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
            mNoticeboardList  = holder.getMessageBody().getNoticeBoardMenuList();
            saveDataIntoTable(holder);
            SharedPreferencesApp.getInstance().saveLastLoginTime(Utils.getCurrTime());
            initRecyclerView();
        } else {
            Toast.makeText(getContext(), R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveDataIntoTable(GetMobileMenuDataModel holder) {
        try {
            //---------------------------------
            TableNoticeBoard table = new TableNoticeBoard();
            table.insert(holder.getMessageBody().getNoticeBoardMenuList());
            table.closeDB();
            TableNewsMaster table1 = new TableNewsMaster();
            table1.insert(holder.getMessageBody().getNewsMasterMenuList());
            table1.closeDB();
            TableResultMaster table2 = new TableResultMaster();
            table2.insert(holder.getMessageBody().getStudentOverallResultSummary());
            table2.closeDB();
            TableFeeMasterMaster table3 = new TableFeeMasterMaster();
            table3.insert(holder.getMessageBody().getStudentOverallFeeSummary());
            table3.closeDB();
            //---------------------------------------------
        } catch (Exception e) {
            AppLog.errLog(TAG, " saveDataIntoTable "+e.getMessage());
        }
    }


    private void navigateToNextPage(int position) {
//        Intent i = new Intent(getActivity(), NewsDetails.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Constant.TAG_HOLDER, mNoticeboardList.get(position));
//        i.putExtras(bundle);
//        startActivity(i);
//        Utils.animRightToLeft(getActivity());
    }


}

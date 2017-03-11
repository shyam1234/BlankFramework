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
import com.malviya.blankframework.activities.NewsDetails;
import com.malviya.blankframework.adapters.NewsAdapter;
import com.malviya.blankframework.constant.Constant;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableNewsMaster;
import com.malviya.blankframework.models.GetMobileMenuDataModel;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableNewsMasterDataModel;
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

public class NewsFragment extends Fragment implements View.OnClickListener {
    public final static String TAG = "NewsFragment";
    private RecyclerView mRecycleViewNews;
    private ArrayList<TableNewsMasterDataModel> mNewsList;
    private NewsAdapter mNewsAdapter;

    public NewsFragment() {
          AppLog.log(TAG,"NewsFragment");
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
        view = inflater.inflate(R.layout.fragment_news, null);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppLog.log(TAG,"onActivityCreated");
        initView();
        fetchDataFromServer();
    }

    private void initView() {
        AppLog.log(TAG,"initView 111 ");
        TextView mTextViewTitle = (TextView) getView().findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_news);
        AppLog.log(TAG,"initView 222 ");
        ImageView mImgProfile = (ImageView) getView().findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        AppLog.log(TAG,"initView 333 ");
        GetPicassoImage.setCircleImageByPicasso(getContext(), UserInfo.selectedStudentImageURL, mImgProfile);
        AppLog.log(TAG,"initView 444 ");
        ImageView mImgBack = (ImageView) getView().findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        //initRecyclerView();
        AppLog.log(TAG,"initView 555 ");

    }

    private void initRecyclerView() {
        mRecycleViewNews = (RecyclerView) getView().findViewById(R.id.recyclerview_news);
        mRecycleViewNews.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewNews.setLayoutManager(manager);
        mNewsAdapter = new NewsAdapter(getContext(), mNewsList, this);
        mRecycleViewNews.setAdapter(mNewsAdapter);
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
                navigateToNextPage(position);
                break;
        }
    }


    private void fetchDataFromServer() {
        TableNewsMaster table = new TableNewsMaster();
        table.openDB(getContext());
        mNewsList = table.getData(UserInfo.menuCode);
        table.closeDB();
        //----------------------------------------------------------
        if(Utils.isInternetConnected(getContext())){
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
            body.put(WSContant.TAG_REFERENCEDATE, "" + UserInfo.timeTableRefDate);
            body.put(WSContant.TAG_LASTRETRIEVED, "" + Utils.getLastRetrivedTimeForNews());
            Utils.showProgressBar(getContext());
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEMENU, header, body, WSContant.TAG_NEWS, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    mNewsList.clear();
                    ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_GETMOBILEMENU);
                    GetMobileMenuDataModel holder = ((GetMobileMenuDataModel) obj.getModel());
                    AppLog.log(TAG,"fetchDataFromServe1r3333 "+mNewsList.size());
                    if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                        //update UI and save data to table ---------------------
                        AppLog.log(TAG,"fetchDataFromServe1r "+mNewsList.size());
                        mNewsList = holder.getMessageBody().getNewsMasterMenuList();
                        AppLog.log(TAG,"fetchDataFromServe2r "+mNewsList.size());
                        saveDataIntoTable(holder);
                        AppLog.log(TAG,"fetchDataFromServe3r "+mNewsList.size());
                        //mNewsAdapter.notifyDataSetChanged();
                        SharedPreferencesApp.getInstance().saveLastLoginTime(Utils.getCurrTime());
                        initRecyclerView();

                        //-------------------------------------------------------
                        //navigateToNextPage();
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

        }else{
            initRecyclerView();
        }
    }


    private void saveDataIntoTable(GetMobileMenuDataModel holder) {
        try {
            TableNewsMaster table = new TableNewsMaster();
            table.insert(holder.getMessageBody().getNewsMasterMenuList());
            table.closeDB();
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from saveDataIntoTable");
        }
    }


    private void navigateToNextPage(int position) {
        Intent i = new Intent(getActivity(), NewsDetails.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.TAG_HOLDER, mNewsList.get(position));
        i.putExtras(bundle);
        startActivity(i);
        Utils.animRightToLeft(getActivity());
    }



}

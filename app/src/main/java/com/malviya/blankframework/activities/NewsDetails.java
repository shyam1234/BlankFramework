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
import com.malviya.blankframework.constant.Constant;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.models.GetMobileMenuDataHolder;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableNewsMasterDataModel;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.parser.ParseResponse;
import com.malviya.blankframework.utils.GetPicassoImage;
import com.malviya.blankframework.utils.SharedPreferencesApp;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 23508 on 2/7/2017.
 */

public class NewsDetails extends AppCompatActivity implements View.OnClickListener {

    private TableNewsMasterDataModel mNewsMasterDataModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            mNewsMasterDataModel = (TableNewsMasterDataModel) bundle.getSerializable(Constant.TAG_HOLDER);
        }
        initView();
        fetchDataFromServer();
    }

    private void initView() {
        //------------------------------------
        TextView mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.title_news_details);
        ImageView mImgProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(this, UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
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
        body.put(WSContant.TAG_REFERENCEID, "" + (mNewsMasterDataModel!=null?mNewsMasterDataModel.getReferenceId():""));
        WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEDETAILS, header, body, WSContant.TAG_GETMOBILEDETAILS, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_NEWS_DETAILS);
                GetMobileMenuDataHolder holder = ((GetMobileMenuDataHolder) obj.getModel());
                if (holder.getMessageResult().equalsIgnoreCase(WSContant.TAG_OK)) {
                    //update UI and save data to table ---------------------
                    //mNewsList = holder.getMessageBody().getNewsMasterMenuList();
                    //saveDataIntoTable(holder);
                    SharedPreferencesApp.getInstance().saveLastLoginTime(Utils.getCurrTime());
                    //mNewsAdapter.notifyDataSetChanged();
                    //-------------------------------------------------------
                    //navigateToNextPage();
                } else {
                    Toast.makeText(NewsDetails.this, R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onErrorResponse(VolleyError response) {

            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Utils.animLeftToRight(NewsDetails.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageview_back:
                onBackPressed();
                break;
        }
    }
}

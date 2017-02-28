package com.malviya.blankframework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.CustomPagerAdapter;
import com.malviya.blankframework.constant.Constant;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableDocumentMaster;
import com.malviya.blankframework.database.TableNewsMaster;
import com.malviya.blankframework.models.GetMobileDetailsDataModel;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableDocumentMasterDataModel;
import com.malviya.blankframework.models.TableNewsMasterDataModel;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.parser.ParseResponse;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.GetPicassoImage;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;
import com.malviya.blankframework.utils.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 23508 on 2/7/2017.
 */

public class NewsDetails extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private static final java.lang.String TAG = "NewsDetails";
    private TableNewsMasterDataModel mNewsMasterDataModel;
    private TextView mTextViewRefTitle;
    private TextView mTextViewPublishedBy;
    private TextView mTextViewTime;
    private WebView mWebViewNewsBody;
    private GetMobileDetailsDataModel mMobileDetailsHolder;
    private ViewPager mViewPagerNewsImages;
    private CustomPagerAdapter mCustomPagerAdapter;
    private int dotsCount;
    private ImageView[] dots;
    private LinearLayout pager_indicator;
    //private  String[] mImagesList = new String[1];


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        init();
        initView();
        fetchDataFromServer();

    }

    private void init() {
        //mNewsDetailsMessageBodyList = new ArrayList<GetMobileDetailsDataModel.MessageBodyDataModel>();
        // mDocumentList = new ArrayList<TableDocumentMasterDataModel>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mNewsMasterDataModel = (TableNewsMasterDataModel) bundle.getSerializable(Constant.TAG_HOLDER);
            AppLog.log(TAG, "ref id: " + mNewsMasterDataModel.getReferenceId());
        }
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
        mTextViewRefTitle = (TextView) findViewById(R.id.textview_news_details_reference_title);
        mTextViewPublishedBy = (TextView) findViewById(R.id.textview_news_details_published_by);
        mTextViewTime = (TextView) findViewById(R.id.textview_news_details_published_time);
        //--------------------------------
        mViewPagerNewsImages = (ViewPager) findViewById(R.id.viewpager_news_details);
        mWebViewNewsBody = (WebView) findViewById(R.id.webview_news_row_body);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        mViewPagerNewsImages.addOnPageChangeListener(this);
        mViewPagerNewsImages.setPageTransformer(true, new ZoomOutPageTransformer());
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
        body.put(WSContant.TAG_REFERENCEID, "" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""));
        Utils.showProgressBar(this);
        WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEDETAILS, header, body, WSContant.TAG_GETMOBILEDETAILS, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_NEWS_DETAILS);
                mMobileDetailsHolder = ((GetMobileDetailsDataModel) obj.getModel());
                mMobileDetailsHolder.getMessageBody().get(0).getMessageBodyHTML();
                //mNewsDetailsMessageBodyList = mMobileDetailsHolder.getMessageBody();
                //mDocumentList = mMobileDetailsHolder.getDocuments();
                saveDataIntoTable("" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""), mMobileDetailsHolder.getMessageBody(), mMobileDetailsHolder.getDocuments());
                bindDataWithUI();
                Utils.dismissProgressBar();
            }

            @Override
            public void onErrorResponse(VolleyError response) {
                  Utils.dismissProgressBar();
            }
        });
    }

    private void bindDataWithUI() {
        if (mMobileDetailsHolder != null) {
            mTextViewRefTitle.setText(mNewsMasterDataModel.getReferenceTitle());
            mTextViewPublishedBy.setText(mNewsMasterDataModel.getPublishedBy());
            mTextViewTime.setText(mNewsMasterDataModel.getPublishedOn());
            mWebViewNewsBody.loadData(mMobileDetailsHolder.getMessageBody().get(0).getMessageBodyHTML(), "text/html; charset=utf-8", "utf-8");
            //--------------------------------------------
            if (mMobileDetailsHolder.getDocuments() != null) {
                mCustomPagerAdapter = new CustomPagerAdapter(this, mMobileDetailsHolder.getDocuments(), this);
                mViewPagerNewsImages.setAdapter(mCustomPagerAdapter);
                mViewPagerNewsImages.setCurrentItem(0);
            }

            setUiPageViewController();
        }
    }


    private void saveDataIntoTable(String pRefId, ArrayList<GetMobileDetailsDataModel.MessageBodyDataModel> pMessageBodyList, ArrayList<TableDocumentMasterDataModel> pDocumentList) {
        TableNewsMaster newsTable = new TableNewsMaster();
        newsTable.openDB(this);
        newsTable.insertMessageBody(pRefId, pMessageBodyList);
        newsTable.closeDB();

        TableDocumentMaster table = new TableDocumentMaster();
        table.openDB(this);
        table.insert(pDocumentList);
        table.closeDB();
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
        switch (v.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.imageView:
                int posi = (Integer)v.getTag();
                navigateToNextPage(posi);
                break;
        }
    }


    private void setUiPageViewController() {
        dotsCount = mCustomPagerAdapter.getCount();
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(4, 0, 4, 0);
            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(dots!=null) {
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            }

            dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

            if (position + 1 == dotsCount) {
                // btnNext.setVisibility(View.GONE);
                // btnFinish.setVisibility(View.VISIBLE);
            } else {
                // btnNext.setVisibility(View.VISIBLE);
                // btnFinish.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void navigateToNextPage(int posi) {
        Intent i = new Intent(this, NewsScreenSlidePagerActivity.class);
        //i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        if (mMobileDetailsHolder.getDocuments() != null) {
            bundle.putSerializable(Constant.TAG_HOLDER, mMobileDetailsHolder.getDocuments());
        }
        bundle.putInt(Constant.TAG_POSITION, posi);
        i.putExtras(bundle);
        startActivity(i);
        Utils.animRightToLeft(this);
    }
}

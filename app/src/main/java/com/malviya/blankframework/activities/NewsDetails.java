package com.malviya.blankframework.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.CustomPagerAdapter;
import com.malviya.blankframework.adapters.NewsDetailsCommentAdapter;
import com.malviya.blankframework.adapters.NewsDetailsLikeAdapter;
import com.malviya.blankframework.constant.Constant;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableDocumentMaster;
import com.malviya.blankframework.database.TableNewsMaster;
import com.malviya.blankframework.models.GetMobileDetailsDataModel;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.NewsDetailsCommentLikeDataModel;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

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
    private BottomSheetBehavior<View> behavior;
    //private  String[] mImagesList = new String[1];
    private View bottomSheet;
    private BottomSheetDialog mBottomSheetDialog;
    private TextView mTextViewCommentTab;
    private TextView mTextViewLikeTab;
    private RecyclerView mRecycleViewCommentLike;
    private EditText mEditTextComment;
    private TextView mTextViewSend;
    private NewsDetailsLikeAdapter mNewsDetailsLikeAdapter;
    private NewsDetailsCommentLikeDataModel mNewsDetailsCommentLikeDataModel;
    private NewsDetailsCommentAdapter mNewsDetailsCommentAdapter;
    private RelativeLayout mRelComment;

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

        RelativeLayout rel_like = (RelativeLayout) findViewById(R.id.rel_inc_like_comment_like_holder);
        rel_like.setOnClickListener(this);
        RelativeLayout rel_comment = (RelativeLayout) findViewById(R.id.rel_inc_like_comment_comment_holder);
        rel_comment.setOnClickListener(this);
        initBottomSheetLayout();
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
//                Utils.dismissProgressBar();
                fetchCommentDataFromServer(0);
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
        if (JCVideoPlayer.backPress()) {
        } else if (behavior != null && behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            finish();
            Utils.animLeftToRight(NewsDetails.this);
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

        if (dots.length > 0)
            dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selecteditem_dot));

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (dots != null) {
            for (int i = 0; i < dotsCount; i++) {
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.nonselecteditem_dot));
            }
            if (dots.length > 0)
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
        Intent i = new Intent(this, NewsScreenSliderPagerActivity.class);
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

    @Override
    protected void onStop() {
        super.onStop();
        JCVideoPlayer.releaseAllVideos();
    }


    private void viewComment() {
        onShareClick();
    }


    private void initBottomSheetLayout() {
        // The View with the BottomSheetBehavior
        bottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheet.setVisibility(View.VISIBLE);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    ((LinearLayout) findViewById(R.id.lin_main_content)).setEnabled(false);
                    ((LinearLayout) findViewById(R.id.lin_main_content)).setVisibility(View.GONE);
                } else {
                    ((LinearLayout) findViewById(R.id.lin_main_content)).setEnabled(true);
                    ((LinearLayout) findViewById(R.id.lin_main_content)).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        //decide the initial height
        behavior.setPeekHeight(0);
        //-----------------------------------------------
        mTextViewCommentTab = (TextView) findViewById(R.id.textview_comment_like_page_comment);
        mTextViewLikeTab = (TextView) findViewById(R.id.textview_comment_like_page_like);
        mEditTextComment = (EditText) findViewById(R.id.edittext_send_comment_comment);
        mTextViewSend = (TextView) findViewById(R.id.textview_send_comment_send);
        mRelComment = (RelativeLayout) findViewById(R.id.rel_comment);
        //------------------------------------------------
        mTextViewSend.setOnClickListener(this);
        mTextViewCommentTab.setOnClickListener(this);
        mTextViewLikeTab.setOnClickListener(this);
        initRecyclerView();

    }

    private void initRecyclerView() {
        mRecycleViewCommentLike = (RecyclerView) findViewById(R.id.recyclerview_comment_like_page);
        mRecycleViewCommentLike.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewCommentLike.setLayoutManager(manager);

    }


    public void onShareClick() {
        if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN || behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            getComments();
        } else {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.imageView:
                int posi = (Integer) v.getTag();
                navigateToNextPage(posi);
                break;
            case R.id.rel_inc_like_comment_like_holder:
                doLike(((TextView) v.findViewById(R.id.textview_inc_bottom_like)));
                break;
            case R.id.rel_inc_like_comment_comment_holder:
                viewComment();
                break;
            case R.id.textview_comment_like_page_comment:
                getComments();
                break;
            case R.id.textview_comment_like_page_like:
                getLikes();
                break;
            case R.id.textview_send_comment_send:
                doComment(((EditText)findViewById(R.id.edittext_send_comment_comment)));
                break;
        }
    }


    private void getLikes() {
        mRelComment.setVisibility(View.GONE);
        mTextViewLikeTab.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mTextViewCommentTab.setBackgroundColor(Color.TRANSPARENT);
        mTextViewLikeTab.setTextColor(getResources().getColor(R.color.colorGreen));
        mTextViewCommentTab.setTextColor(getResources().getColor(R.color.colorWhite));
        mNewsDetailsLikeAdapter = new NewsDetailsLikeAdapter(NewsDetails.this, mNewsDetailsCommentLikeDataModel.getLikeMaster());
        mRecycleViewCommentLike.setAdapter(mNewsDetailsLikeAdapter);
        mNewsDetailsLikeAdapter.notifyDataSetChanged();

    }

    private void getComments() {
        mRelComment.setVisibility(View.VISIBLE);
        mTextViewCommentTab.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mTextViewLikeTab.setBackgroundColor(Color.TRANSPARENT);
        mTextViewCommentTab.setTextColor(getResources().getColor(R.color.colorGreen));
        mTextViewLikeTab.setTextColor(getResources().getColor(R.color.colorWhite));
        mNewsDetailsCommentAdapter = new NewsDetailsCommentAdapter(NewsDetails.this, mNewsDetailsCommentLikeDataModel.getCommentMaster());
        mRecycleViewCommentLike.setAdapter(mNewsDetailsCommentAdapter);
        mNewsDetailsCommentAdapter.notifyDataSetChanged();
    }

    private void fetchCommentDataFromServer(final int type) {

        //call to WS and validate given credential----
        Map<String, String> header = new HashMap<>();
        header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
        header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
        header.put(WSContant.TAG_NEW, Utils.getCurrTime());
        header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
        //-Utils-for body
        Map<String, String> body = new HashMap<>();
        body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
        body.put(WSContant.TAG_REFERENCEID, "" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""));
        // Utils.showProgressBar(this);
        WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILECOMMENTSNLIKES, header, body, WSContant.TAG_COMMENT, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_NEWS_DETAILS_COMMENTS_LIKE);
                mNewsDetailsCommentLikeDataModel = ((NewsDetailsCommentLikeDataModel) obj.getModel());
                mRecycleViewCommentLike.setAdapter(mNewsDetailsCommentAdapter);
                if((type==2) && mNewsDetailsCommentAdapter!=null){
                    getComments();
                    mRecycleViewCommentLike.smoothScrollToPosition(mNewsDetailsCommentLikeDataModel.getCommentMaster().size()-1);
                }else if((type==1) &&  mNewsDetailsLikeAdapter!=null){
                    getLikes();
                    mRecycleViewCommentLike.smoothScrollToPosition(mNewsDetailsCommentLikeDataModel.getLikeMaster().size()-1);
                }
                Utils.dismissProgressBar();
            }

            @Override
            public void onErrorResponse(VolleyError response) {
                Utils.dismissProgressBar();
            }
        });
    }


    private void doLike(final TextView textView) {
        final String pStr = textView.getText().toString().trim();
        Map<String, String> header = new HashMap<>();
        header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
        header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
        header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
        header.put(WSContant.TAG_NEW, Utils.getCurrTime());

        //-Utils-for body
        final Map<String, String> body = new HashMap<>();
        body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
        body.put(WSContant.TAG_REFERENCEID, "" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""));
        body.put(WSContant.TAG_USERID, "" + UserInfo.userId);
        body.put(WSContant.TAG_COMMENT, "");
        if (pStr.equalsIgnoreCase(getResources().getString(R.string.like))) {
            body.put(WSContant.TAG_ISLIKE, "1");
            body.put(WSContant.TAG_ISDELETE, "0");
            // textView.setText(getResources().getString(R.string.unlike));
        } else {
            // textView.setText(getResources().getString(R.string.like));
            body.put(WSContant.TAG_ISLIKE, "0");
            body.put(WSContant.TAG_ISDELETE, "1");
        }
        Utils.showProgressBar(this);
        WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_SAVELIKENCOMMENT, header, body, WSContant.TAG_SAVELIKECOMMENT, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                Utils.dismissProgressBar();
                if (response != null && response.length() > 0) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String resp = jsonObject.getString(WSContant.TAG_MESSAGERESULT);
                        if (resp.equalsIgnoreCase(WSContant.TAG_OK)) {
                            if (pStr.equalsIgnoreCase(getResources().getString(R.string.like))) {
                                textView.setText(getResources().getString(R.string.unlike));
                            } else {
                                textView.setText(getResources().getString(R.string.like));
                            }
                            fetchCommentDataFromServer(1);
                        } else {
                            Toast.makeText(NewsDetails.this, R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        AppLog.errLog(TAG, e.getMessage());
                    }
                }

            }

            @Override
            public void onErrorResponse(VolleyError response) {
                Utils.dismissProgressBar();
            }
        });
    }


    private void doComment(final EditText editText) {
        if(editText.getText().toString().trim().length()>0) {
            Map<String, String> header = new HashMap<>();
            header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
            header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
            header.put(WSContant.TAG_DATELASTRETRIEVED, Utils.getLastRetrivedTimeForNews());
            header.put(WSContant.TAG_NEW, Utils.getCurrTime());

            //-Utils-for body
            final Map<String, String> body = new HashMap<>();
            body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
            body.put(WSContant.TAG_REFERENCEID, "" + (mNewsMasterDataModel != null ? mNewsMasterDataModel.getReferenceId() : ""));
            body.put(WSContant.TAG_USERID, "" + UserInfo.userId);
            body.put(WSContant.TAG_COMMENT, editText.getText().toString().trim());
            body.put(WSContant.TAG_ISLIKE, "0");
            body.put(WSContant.TAG_ISDELETE, "0");
            Utils.showProgressBar(this);
            Utils.hideNativeKeyboard(this);
            WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_SAVELIKENCOMMENT, header, body, WSContant.TAG_SAVELIKECOMMENT, new IWSRequest() {
                @Override
                public void onResponse(String response) {
                    Utils.dismissProgressBar();
                    if (response != null && response.length() > 0) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String resp = jsonObject.getString(WSContant.TAG_MESSAGERESULT);
                            if (resp.equalsIgnoreCase(WSContant.TAG_OK)) {
                                editText.setText("");
                                Toast.makeText(NewsDetails.this, R.string.msg_success_msg_post, Toast.LENGTH_SHORT).show();
                                fetchCommentDataFromServer(2);
                            } else {
                                Toast.makeText(NewsDetails.this, R.string.msg_network_prob, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            AppLog.errLog(TAG, e.getMessage());
                        }
                    }
                }

                @Override
                public void onErrorResponse(VolleyError response) {
                    Utils.dismissProgressBar();
                }
            });
        }else{
            Toast.makeText(NewsDetails.this, R.string.msg_validate_comment, Toast.LENGTH_SHORT).show();
        }
    }


}

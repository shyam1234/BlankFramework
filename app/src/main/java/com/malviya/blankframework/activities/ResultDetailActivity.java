package com.malviya.blankframework.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.adapters.ResultDetailsAdapter;
import com.malviya.blankframework.constant.Constant;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableResultDetails;
import com.malviya.blankframework.models.LoginDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableResultDetailsDataModel;
import com.malviya.blankframework.models.TableResultMasterDataModel;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.parser.ParseResponse;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.DownloadFileAsync;
import com.malviya.blankframework.utils.GetPicassoImage;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 23508 on 2/7/2017.
 */

public class ResultDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final java.lang.String TAG = "ResultDetailActivity";
    private RecyclerView mRecycleViewResultList;
    private ResultDetailsAdapter mResultDetailsAdapter;
    private TableResultMasterDataModel mResultDataModel;
    private TableResultDetailsDataModel mMobileDetailsHolder;
    private Button mBtnDownload;
    private String dest_file_path = "db_name";
    private int totalsize;
    private int downloadedSize;
    private float per;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_detail);
        init();
        initView();
        fetchDataFromServer();
    }


    private void init() {
        mMobileDetailsHolder = new TableResultDetailsDataModel();
        // mResultDetailList = new ArrayList<TableResultDetailsDataModel>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mResultDataModel = (TableResultMasterDataModel) bundle.getSerializable(Constant.TAG_HOLDER);
            AppLog.log(TAG, "ref id: " + mResultDataModel.getReferenceId());
        }
    }

    private void initView() {
        //------------------------------------
        TextView mTextViewTitle = (TextView) findViewById(R.id.textview_title);
        mTextViewTitle.setText(R.string.tab_result_details);
        ImageView mImgProfile = (ImageView) findViewById(R.id.imageview_profile);
        mImgProfile.setVisibility(View.VISIBLE);
        GetPicassoImage.setCircleImageByPicasso(this, UserInfo.selectedStudentImageURL, mImgProfile);
        ImageView mImgBack = (ImageView) findViewById(R.id.imageview_back);
        mImgBack.setVisibility(View.VISIBLE);
        mImgBack.setOnClickListener(this);
        //------------------------------------
        mBtnDownload = (Button) findViewById(R.id.btn_results_download);
        mBtnDownload.setOnClickListener(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecycleViewResultList = (RecyclerView) findViewById(R.id.recyclerview_results_list);
        mRecycleViewResultList.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setSmoothScrollbarEnabled(true);
        mRecycleViewResultList.setLayoutManager(manager);
        mResultDetailsAdapter = new ResultDetailsAdapter(ResultDetailActivity.this, mMobileDetailsHolder, this);
        mRecycleViewResultList.setAdapter(mResultDetailsAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Utils.animLeftToRight(ResultDetailActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageview_back:
                onBackPressed();
                break;
            case R.id.imageview_resultlist_row_selection:
                int position = (Integer) v.getTag();
                ((ImageView) (v.findViewById(R.id.imageview_resultlist_row_selection))).setImageResource(R.drawable.selected);
                //ResultFragment.selected_sem = mResultDetailList.get(position).getSemester();
                finish();
                break;
            case R.id.btn_results_download:
                new DownloadFileAsync(this,WSContant.DOWNLOAD_FOLDER).execute( WSContant.URL_PRINT_OVERALL_RESULT,"" + UserInfo.studentId);
                break;
        }
    }


    private void fetchDataFromServer() {
        //call to WS and validate given credential----
        Map<String, String> header = new HashMap<>();
        header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
        header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
        //-Utils-for body
        Map<String, String> body = new HashMap<>();
        body.put(WSContant.TAG_MENUCODE, "" + UserInfo.menuCode);
        body.put(WSContant.TAG_REFERENCEID, "" + (mResultDataModel != null ? mResultDataModel.getReferenceId() : ""));
        body.put(WSContant.TAG_REFERENCEDATE, "" + UserInfo.timeTableRefDate);
        Utils.showProgressBar(this);
        WSRequest.getInstance().requestWithParam(WSRequest.POST, WSContant.URL_GETMOBILEDETAILS, header, body, WSContant.TAG_GETMOBILEDETAILS, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                ParseResponse obj = new ParseResponse(response, LoginDataModel.class, ModelFactory.MODEL_RESULTDETAILS);
                mMobileDetailsHolder = ((TableResultDetailsDataModel) obj.getModel());
                saveIntoDatabase();
                Utils.dismissProgressBar();
                initRecyclerView();
            }

            @Override
            public void onErrorResponse(VolleyError response) {
                Utils.dismissProgressBar();
            }
        });
    }

    private void saveIntoDatabase() {
        TableResultDetails table = new TableResultDetails();
        table.openDB(ResultDetailActivity.this);
        table.insert(mMobileDetailsHolder.getMessageBody());
        table.closeDB();
    }
}

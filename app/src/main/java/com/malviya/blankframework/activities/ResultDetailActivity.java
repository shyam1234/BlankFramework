package com.malviya.blankframework.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.malviya.blankframework.utils.GetPicassoImage;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okio.BufferedSink;
import okio.Okio;

/**
 * Created by 23508 on 2/7/2017.
 */

public class ResultDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static final java.lang.String TAG = "ResultDetailActivity";
    private RecyclerView mRecycleViewResultList;
    //private ArrayList<TableResultDetailsDataModel> mResultDetailList;
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
                //downloadResult();
               // downloadAndOpenPDF(WSContant.URL_PRINT_OVERALL_RESULT + UserInfo.studentId);
                new Background().execute(""+UserInfo.studentId);
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


    private void downloadResult() {
        Map<String, String> header = new HashMap<>();
        header.put(WSContant.TAG_TOKEN, UserInfo.authToken);
        header.put(WSContant.TAG_UNIVERSITYID, "" + UserInfo.univercityId);
        Utils.showProgressBar(this);
        WSRequest.getInstance().requestWithParam(WSRequest.GET, WSContant.URL_PRINT_OVERALL_RESULT + UserInfo.studentId, header, null, WSContant.TAG_PRINT_OVERALL_RESULT, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                AppLog.log("downloadResult", response);
            }

            @Override
            public void onErrorResponse(VolleyError response) {
                Utils.dismissProgressBar();
            }
        });
    }

    void downloadAndOpenPDF(final String download_file_url) {
        new Thread(new Runnable() {
            public void run() {
                Uri path = Uri.fromFile(downloadFile(download_file_url));
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch (ActivityNotFoundException e) {
                    AppLog.errLog("downloadAndOpenPDF", ("PDF Reader application is not installed in your device"));
                }
            }
        }).start();

    }


    private File downloadFile(String dwnload_file_path) {
        File file = null;
        try {

            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            // connect
            urlConnection.setRequestProperty(WSContant.TAG_TOKEN, UserInfo.authToken);
            urlConnection.setRequestProperty(WSContant.TAG_UNIVERSITYID, UserInfo.authToken);
            urlConnection.connect();
            // set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            // create a new file, to save the downloaded file
            file = new File(SDCardRoot, dest_file_path);
            FileOutputStream fileOutput = new FileOutputStream(file);
            // Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();
            // this is the total size of the file which we are downloading
            totalsize = urlConnection.getContentLength();
            Toast.makeText(this, "Starting PDF download...", Toast.LENGTH_SHORT).show();
            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                per = ((float) downloadedSize / totalsize) * 100;
                AppLog.log("Total PDF File size  : " + (totalsize / 1024) + " KB\n\nDownloading PDF " + (int) per + "% complete");
            }
            // close the output stream when complete //
            fileOutput.close();
            Toast.makeText(this, "Download Complete. Open PDF Application installed in the device.", Toast.LENGTH_SHORT).show();

        } catch (final MalformedURLException e) {
//            setTextError("Some error occured. Press back and try again.",     Color.RED);
        } catch (final IOException e) {
            //setTextError("Some error occured. Press back and try again.",     Color.RED);
        } catch (final Exception e) {
            //setTextError(Failed to download image. Please check your internet connection.",  Color.RED);
        }

        AppLog.log("downloadFile ", file.getAbsolutePath());
        return file;
    }


    class Background extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Utils.showProgressBar(ResultDetailActivity.this);
        }

        @Override
        protected void onPostExecute(String s) {
            Utils.dismissProgressBar();
            AppLog.log("Response", s);
        }

        @Override
        protected void onProgressUpdate(String... values) {

        }

        @Override
        protected String doInBackground(String... url) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(WSContant.URL_PRINT_OVERALL_RESULT + url[0])
                    .get()
                    .addHeader(WSContant.TAG_TOKEN,UserInfo.authToken)
                    .addHeader(WSContant.TAG_UNIVERSITYID,""+UserInfo.univercityId)
                    .build();
            try {
                String folder_main = "ServiceDesk";
                File mFolder = new File(Environment.getExternalStorageDirectory(), folder_main);
                if (!mFolder.exists()) {
                    mFolder.mkdir();
                }
                Response response = client.newCall(request).execute();
                AppLog.log("response pdf ",response.toString());
                File file = new File(mFolder, url[0] + ".pdf");
                BufferedSink sink = Okio.buffer(Okio.sink(file));
                sink.writeAll(response.body().source());
                sink.close();
                String resp = response.body().string();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return url[0];
        }
    }

}

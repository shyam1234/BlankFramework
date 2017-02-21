package com.malviya.blankframework.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableLanguage;
import com.malviya.blankframework.interfaces.ICallBack;
import com.malviya.blankframework.models.LanguageArrayDataModel;
import com.malviya.blankframework.models.ModelFactory;
import com.malviya.blankframework.models.TableLanguageDataModel;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.parser.ParseResponse;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.CustomDialogbox;
import com.malviya.blankframework.utils.SharedPreferencesApp;
import com.malviya.blankframework.utils.UserInfo;
import com.malviya.blankframework.utils.Utils;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 11-12-2016.
 */
public class SplashActivity extends AppCompatActivity {
    private final String TAG = SplashActivity.class.getName();
    private static final long TIME_DELAY = 2000;
    private Handler mHandler;
    private Runnable mRunnable;
    public static Context mContext;
    CircularProgressBar mCircularProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Utils.animRightToLeft(SplashActivity.this);
        init();
        initView();
        Utils.showProgressBar(100, mCircularProgressBar);
    }



    private void init() {
        mContext = this;
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                checkForLanguage();
            }
        };
        mHandler.postDelayed(mRunnable, TIME_DELAY);
        SharedPreferencesApp.getInstance();
        //------------------------------------------

    }

    private void initView() {
        mCircularProgressBar = (CircularProgressBar) findViewById(R.id.progressBar);
    }


    private void checkForLanguage() {
        TableLanguage table = new TableLanguage();
        table.openDB(SplashActivity.this);
        AppLog.log("splashActivity++", "checkForLanguage");
        table.read();
        //SharedPreferencesApp.getInstance().removeAll();
        Map<String, String> header = new HashMap<>();
        header.put(WSContant.TAG_UNIVERSITYID, SharedPreferencesApp.getInstance().getLastSavedUniversityID());
        header.put(WSContant.TAG_LANGUAGE_VERSION_DATE, SharedPreferencesApp.getInstance().getLastLangSync());
        WSRequest.getInstance().requestWithParam(WSRequest.GET, WSContant.URL_BASE, header, null, WSContant.TAG_LANG, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                AppLog.log("onResponse", "res++ " + response);
                ParseResponse obj = new ParseResponse(response, LanguageArrayDataModel.class, ModelFactory.MODEL_LANG);
                LanguageArrayDataModel holder = ((LanguageArrayDataModel) obj.getModel());
                bindDataWithLanguageDataModel(holder);
            }

            @Override
            public void onErrorResponse(VolleyError response) {
                AppLog.log("splashActivity++", "onErrorResponse");
                final CustomDialogbox dilog = new CustomDialogbox(SplashActivity.this, CustomDialogbox.TYPE_OK);
                dilog.setTitle(getResources().getString(R.string.msg_network_prob));
                dilog.show();
                dilog.getBtnOK().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dilog.dismiss();
                        finish();
                    }
                });
            }
        });
    }

   /* private void storeIntoDB(ParseResponse obj) {
        TableLanguage table = new TableLanguage();
        table.openDB(SplashActivity.this);
        //AppLog.log("table: ",((LanguageArrayDataModel) obj.getModel()).LanguageArray.get(3).EnglishVersion);
        boolean isAdded = table.insert(((LanguageArrayDataModel) obj.getModel()).LanguageArray);
        if (isAdded)
            Toast.makeText(this, "Language db updated for university id: " + SharedPreferencesApp.getInstance().universityID, Toast.LENGTH_SHORT).show();
        table.closeDB();
        SharedPreferencesApp.getInstance().saveLanguageUpdateHistory(SharedPreferencesApp.getInstance().universityID, Utils.getCurrTime());
        navigateToNextPage();
    }*/

    private void bindDataWithLanguageDataModel(LanguageArrayDataModel holder) {
        try {
            ArrayList<TableLanguageDataModel> list = new ArrayList<TableLanguageDataModel>();
            for (LanguageArrayDataModel.LanguageDataModel model : holder.LanguageArray) {
                //-- assign value to model
                TableLanguageDataModel obj = new TableLanguageDataModel();
                obj.setUniversityId(model.getUniversityId());
                obj.setBahasaVersion(model.getBahasaVersion());
                obj.setConversionCode(model.getConversionCode());
                obj.setConversionId(model.getConversionId());
                obj.setDateModified(model.getDateModified());
                obj.setEnglishVersion(model.getEnglishVersion());
                list.add(obj);
            }
            //saving into database
            TableLanguage table = new TableLanguage();
            table.openDB(getApplicationContext());
            boolean isAdded = table.insert(list);
            AppLog.log("lang inserted+++ 111 ", "" + isAdded);
            if (isAdded) {
                SharedPreferencesApp.getInstance().saveLastLangSync(Utils.getCurrTime());
                AppLog.log("splash ","Lang Sync for university id: " +SharedPreferencesApp.getInstance().getLastSavedUniversityID());
            }
            table.closeDB();
            AppLog.log("splash UserInfo.authToken  ", "" + UserInfo.authToken );
            AppLog.networkLog("splash networkLog.networkLog  ", "" + UserInfo.authToken );
            if( UserInfo.authToken !=null) {
                Utils.updateHomeTableAsPerDefaultChildSelection(new ICallBack() {
                    @Override
                    public void callBack() {
                        navigateToNextPage(DashboardActivity.class);
                    }
                });
            }else{
                navigateToNextPage(LoginActivity.class);
            }
        } catch (Exception e) {
            AppLog.errLog("SplashActivity bindDataWithLanguageDataModel", e.getMessage());
        }
    }


    private void navigateToNextPage(Class<?> activity) {
        Intent intent = new Intent(SplashActivity.this, activity);
        startActivity(intent);
        finish();
        Utils.animRightToLeft(SplashActivity.this);
    }
}

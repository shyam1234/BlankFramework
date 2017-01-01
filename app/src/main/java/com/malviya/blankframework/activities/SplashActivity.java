package com.malviya.blankframework.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.malviya.blankframework.R;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableLanguage;
import com.malviya.blankframework.models.LanguageArrayDataModel;
import com.malviya.blankframework.network.IWSRequest;
import com.malviya.blankframework.network.WSRequest;
import com.malviya.blankframework.parser.ParseResponse;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.SharePreferenceApp;
import com.malviya.blankframework.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 11-12-2016.
 */
public class SplashActivity extends AppCompatActivity {
    private final String TAG = SplashActivity.class.getName();
    private static final long TIME_DELAY = 3000;
    private Handler mHandler;
    private Runnable mRunnable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Utils.animRightToLeft(SplashActivity.this);
        init();
        SharePreferenceApp.getInstance();

    }

    private void init() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                //navigateToNextPage();
            }
        };
        mHandler.postDelayed(mRunnable, TIME_DELAY);
        //------------------------------------------
        checkForLanguage();
    }

    private void checkForLanguage() {
        TableLanguage table = new TableLanguage();
        table.openDB(SplashActivity.this);
        //AppLog.log("table: ",((LanguageArrayDataModel) obj.getModel()).LanguageArray.get(3).EnglishVersion);
        table.read();
        //SharePreferenceApp.getInstance().removeAll();
        Map<String, String> header = new HashMap<>();
        header.put(WSContant.TAG_UNIVERSITYID, SharePreferenceApp.getInstance().universityID);
        header.put(WSContant.TAG_LANGUAGE_VERSION_DATE, SharePreferenceApp.getInstance().languageLastUpdateTime);
        WSRequest.getInstance().requestWithParam(WSRequest.GET, WSContant.URL_BASE, header, null, WSContant.TAG_LOGIN, new IWSRequest() {
            @Override
            public void onResponse(String response) {
                //--parsing logic------------------------------------------------------------------
                ParseResponse obj = new ParseResponse(response, LanguageArrayDataModel.class, LanguageArrayDataModel.KEY);
                storeIntoDB(obj);
                //--parsing logic------------------------------------------------------------------
            }

            @Override
            public void onErrorResponse(VolleyError response) {

            }
        });
    }

    private void storeIntoDB(ParseResponse obj) {
        TableLanguage table = new TableLanguage();
        table.openDB(SplashActivity.this);
        //AppLog.log("table: ",((LanguageArrayDataModel) obj.getModel()).LanguageArray.get(3).EnglishVersion);
        boolean isAdded = table.insert(((LanguageArrayDataModel) obj.getModel()).LanguageArray);
        if(isAdded)
        Toast.makeText(this, "Language db updated for university id: "+SharePreferenceApp.getInstance().universityID, Toast.LENGTH_SHORT).show();
        table.closeDB();
        SharePreferenceApp.getInstance().saveLanguageUpdateHistory(SharePreferenceApp.getInstance().universityID, Utils.getCurrTime());
        navigateToNextPage();
    }

    private void navigateToNextPage() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        Utils.animRightToLeft(SplashActivity.this);
    }


}

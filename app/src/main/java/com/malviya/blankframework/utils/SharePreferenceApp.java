package com.malviya.blankframework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.constant.WSContant;

/**
 * Created by Admin on 31-12-2016.
 */

public class SharePreferenceApp {
    private static final String DEFAULT_SHAREPREF = "edurp_sharepref";
    private static SharePreferenceApp mInstance;
    public String universityID = "1";
    public String languageLastUpdateTime = "";

    private SharePreferenceApp() {
        //read all store value
        readSavedLanguageUpdateHistory();
    }

    public static SharePreferenceApp getInstance() {
        synchronized (SharePreferenceApp.class) {
            if (mInstance == null) {
                mInstance = new SharePreferenceApp();
            }
        }

        return mInstance;
    }

    public void readSavedLanguageUpdateHistory() {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                universityID = sharePref.getString(WSContant.TAG_UNIVERSITYID, "1");
                languageLastUpdateTime = sharePref.getString(WSContant.TAG_LANGUAGE_VERSION_DATE, "");
            } else {
                AppLog.log("readSavedLanguageUpdateHistory", "there is not university language history");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("Exception from saveLanguageUpdateHistory", e.getMessage());
        }
    }


    public void saveLanguageUpdateHistory(String pUniversity, String pLastUpdateDate) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_UNIVERSITYID, pUniversity);
            data.putString(WSContant.TAG_LANGUAGE_VERSION_DATE, pLastUpdateDate);
            data.commit();
            AppLog.log("saveLanguageUpdateHistory", "pUniversity: " + pUniversity + " : pLastUpdateDate " + pLastUpdateDate);
        } catch (Exception e) {
            AppLog.errLog("Exception from saveLanguageUpdateHistory", e.getMessage());
        }
    }


    public void removeLanguageUpdateHistory() {
        try {
            universityID = "1";
            languageLastUpdateTime = "";
            saveLanguageUpdateHistory(universityID, languageLastUpdateTime);
        } catch (Exception e) {
            AppLog.errLog("Exception from removeLanguageUpdateHistory", e.getMessage());
        }
    }


    public void removeAll() {
        removeLanguageUpdateHistory();
    }


}
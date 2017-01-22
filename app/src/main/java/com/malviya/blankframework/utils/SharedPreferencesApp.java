package com.malviya.blankframework.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.constant.WSContant;

/**
 * Created by Admin on 31-12-2016.
 */

public class SharedPreferencesApp {
    private static final String DEFAULT_SHAREPREF = "edurp_sharepref";
    private static SharedPreferencesApp mInstance;
    public String universityID = "1";
    public String languageLastUpdateTime = "";
    private String mCurrTime;
    private String mLastLoginTime;
    private String mLangRetrivTime;

    private SharedPreferencesApp() {
        //read all store value
        readSavedLanguageUpdateHistory();
    }

    public static SharedPreferencesApp getInstance() {
        synchronized (SharedPreferencesApp.class) {
            if (mInstance == null) {
                mInstance = new SharedPreferencesApp();
                //retrieve value from store
                getStoreData();
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

    public String getSavedLanguageTime() {
        SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
        try {
            if (sharePref != null) {
                return languageLastUpdateTime = sharePref.getString(WSContant.TAG_LANGUAGE_VERSION_DATE, "");
            } else {
                AppLog.log("readSavedLanguageTime", "there is not university language history");
            }
        } catch (Exception e) {
            AppLog.errLog("Exception from saveLanguageUpdateHistory", e.getMessage());
        } finally {
            sharePref = null;

        }
        return "";
    }


    public void saveLanguageUpdateHistory(String pUniversity, String pLastUpdateDate) {
        try {
            AppLog.log("saveLanguageUpdateHistory", "");
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
        removeSavedTime();
        removeAuthToken();
        UserInfo.clearUSerInfo();
    }


    //-----------------------------------------------------------

    public void saveTime(String currTime) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_SHAREDPREF_GET_LAST_TIME, currTime);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveTime: " + currTime);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveTime", e.getMessage());
        }
    }

    public String getSavedTime() {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                mCurrTime = sharePref.getString(WSContant.TAG_SHAREDPREF_GET_LAST_TIME, null);
            } else {
                AppLog.log("getSavedTime", "there is not savedTime ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getSavedTime", e.getMessage());
        } finally {
            return mCurrTime;
        }
    }

    public void removeSavedTime() {
        try {
            mCurrTime = null;
            saveTime(mCurrTime);
        } catch (Exception e) {
            AppLog.errLog(" removeSavedTime", e.getMessage());
        }
    }

    //-----------------------------------------------------------
    public void saveLastLoginTime(String currTime) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_LAST_LOGIN_TIME, currTime);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveLastLoginTime: " + currTime);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveLastLoginTime", e.getMessage());
        }
    }

    public String getLastLoginTime() {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                mLastLoginTime = sharePref.getString(WSContant.TAG_LAST_LOGIN_TIME, null);
            } else {
                AppLog.log("getLastLoginTime", "there is not savedTime ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getLastLoginTime", e.getMessage());
        } finally {
            return mLastLoginTime;
        }
    }

    public void removeLastLoginTime() {
        try {
            mLastLoginTime = null;
            saveTime(mLastLoginTime);
        } catch (Exception e) {
            AppLog.errLog(" removeLastLoginTime", e.getMessage());
        }
    }

    //-----------------------------------------------------------
/*    public void saveLangRetriveTime(String currTime) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_LANG_RETRIVE_TIME, currTime);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveLangRetriveTime: " + currTime);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveLangRetriveTime", e.getMessage());
        }
    }

    public String getLangRetriveTime() {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                mLangRetrivTime = sharePref.getString(WSContant.TAG_LANG_RETRIVE_TIME, null);
            } else {
                AppLog.log("getLangRetriveTime", "there is not savedTime ");
            }
            sharePref = null;
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getLangRetriveTime", e.getMessage());
        } finally {
            return mLangRetrivTime;
        }
    }

    public void removeLangRetriveTime() {
        try {
            mLangRetrivTime = null;
            saveTime(mLangRetrivTime);
        } catch (Exception e) {
            AppLog.errLog(" removeLangRetriveTime", e.getMessage());
        }
    }*/
//-----------------------------------------------------------------------------------
    public void saveAuthToken(String authToken, int id, String currUserType) {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor data = sharePref.edit();
            data.putString(WSContant.TAG_AUTHTOKEN, authToken);
            data.putInt(WSContant.TAG_USERID, id);
            data.putString(WSContant.TAG_USERTYPE, currUserType);
            data.commit();
            AppLog.log("sharePreferenceApp", "saveAuthToken: " + authToken);
        } catch (Exception e) {
            AppLog.errLog(" sharePreferenceApp saveAuthToken", e.getMessage());
        }
    }

    private static void getStoreData() {
        try {
            SharedPreferences sharePref = MyApplication.getInstance().getSharedPreferences(DEFAULT_SHAREPREF, Context.MODE_PRIVATE);
            if (sharePref != null) {
                UserInfo.userId= sharePref.getInt(WSContant.TAG_USERID, -1);
                UserInfo.authToken = sharePref.getString(WSContant.TAG_AUTHTOKEN, null);
                UserInfo.currUserType = sharePref.getString(WSContant.TAG_USERTYPE, null);
                AppLog.log("getUserInfo", " UserInfo.userId: " +  UserInfo.userId);
                AppLog.log("getUserInfo", " UserInfo.authToken: " +  UserInfo.authToken);
                AppLog.log("getUserInfo", " UserInfo.currUserType: " +  UserInfo.currUserType);
            } else {
                AppLog.log("getUserInfo", "there is not getStoreData ");
            }
        } catch (Exception e) {
            AppLog.errLog("sharePreferenceApp : getStoreData", e.getMessage());
        }
    }


    public void removeAuthToken() {
        try {
            saveAuthToken(null, -1, null);
        } catch (Exception e) {
            AppLog.errLog(" removeAuthToken", e.getMessage());
        }
    }

    //------------------------------------------------------------------------------------------------


}

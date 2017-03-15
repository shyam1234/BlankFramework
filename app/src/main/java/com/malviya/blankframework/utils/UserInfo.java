package com.malviya.blankframework.utils;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.malviya.blankframework.R;
import com.malviya.blankframework.activities.DashboardActivity;
import com.malviya.blankframework.activities.LoginActivity;
import com.malviya.blankframework.activities.SplashActivity;
import com.malviya.blankframework.application.MyApplication;

/**
 * Created by Admin on 14-01-2017.
 */

public class UserInfo {
    public static int parentId=-1;
    public static String parentName;
    public static int studentId=-1;
    public static String authToken;
    public static int univercityId;
    public static String tokenExp;
    public static String tokenIssue;
    public static int userId=-1;
    public static String currUserType;
    public static String selectedStudentImageURL="";
    public static String menuCode="";
    public static String timeTableRefDate="2016-02-04 09:54:14";
    public static String lang_pref;

    public static void clearUSerInfo() {
        userId = -1;
        parentName = null;
        parentId = -1;
        parentName = null;
        studentId = -1;
        authToken = null;
        univercityId = 0;
        tokenExp = null;
        tokenIssue = null;
        currUserType = null;
        selectedStudentImageURL = "";
        menuCode = null;

    }

    public static void logout() {
        clearUSerInfo();
        SharedPreferencesApp.getInstance().removeAll();
        Activity activity =null;
        if (SplashActivity.mContext != null) {
            activity = (Activity) SplashActivity.mContext;
        }else if (DashboardActivity.mContext != null) {
            activity = (Activity) DashboardActivity.mContext;
        }

        if(activity!=null) {
            Toast.makeText(MyApplication.getInstance().getApplicationContext(), R.string.msg_logout,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
            activity.finish();
            Utils.animLeftToRight(activity);
        }


    }
}


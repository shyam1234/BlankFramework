package com.malviya.blankframework.utils;

import android.app.Activity;
import android.content.Intent;

import com.malviya.blankframework.activities.DashboardActivity;
import com.malviya.blankframework.activities.LoginActivity;
import com.malviya.blankframework.activities.SplashActivity;

/**
 * Created by Admin on 14-01-2017.
 */

public class UserInfo {
    public static int parentId;
    public static String parentName;
    public static int studentId;
    public static String authToken;
    public static int univercityId;

    public static String tokenExp;
    public static String tokenIssue;
    public static int userId;
    public static String currUserType;

    public static void clearUSerInfo() {
        userId = -1;
        parentName = null;
        parentId = -1;
        parentName = null;
        studentId = -1;
        authToken = null;
        univercityId = -1;
        tokenExp = null;
        tokenIssue = null;
        currUserType = null;

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
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.finish();
            Utils.animRightToLeft(activity);
        }
    }
}


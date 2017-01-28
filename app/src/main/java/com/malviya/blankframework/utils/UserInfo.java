package com.malviya.blankframework.utils;

/**
 * Created by Admin on 14-01-2017.
 */

public class UserInfo {
    public static int parentId;
    public static String parentName;
    public static int studentId;
    public static String authToken;
    public static int univercityId;
    public static String university_logo_url;
    public static String tokenExp;
    public static String tokenIssue;
    public static int userId;
    public static String currUserType;

    public static void clearUSerInfo() {
        userId = -1;
        parentName = null;
        university_logo_url = null;
        parentId = -1;
        parentName = null;
        studentId = -1;
        authToken = null;
        univercityId = -1;
        tokenExp = null;
        tokenIssue = null;
        currUserType = null;

    }
}


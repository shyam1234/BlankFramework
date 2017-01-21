package com.malviya.blankframework.constant;

/**
 * Created by Admin on 26-11-2016.
 */

public interface WSContant {
    String URL_BASE = "http://stpl-edurpapi.azurewebsites.net/api";
    //------------------------------------------------------------------------------------
    String URL_LOGIN = URL_BASE + "/authenticate";
    String URL_FORGET = URL_BASE + "/Account/ForgetPassword?";
    String URL_CHANGEPASSWORD = URL_BASE + "/Account/ChangePassword?";
    String URL_REGISTRATION = URL_BASE + "/Account/Register";
    String URL_GETMOBILEHOME = URL_BASE + "/mobile/GetMobileHome";

    //-------------------------------------------------------------------------------------
    String TAG_LOGIN = "login";
    String TAG_LANG = "lang";
    String TAG_GETMOBILEHOME = "mobilehome";
    String TAG_AUTHORIZATION = "Authorization";
    String TAG_EMIALADDRESS = "EmailAddress";
    String TAG_PASSWORD = "Password";
    String TAG_CHANGE_PASSWORD = "change_password";
    String TAAG_DATA = "Data";
    String TAG_SUCCESS = "Success";
    String TAG_UNIVERSITYID = "UniversityId";
    String TAG_LANGUAGE_VERSION_DATE = "LanguageVersionDate";
    String TAG_TOKEN = "Token";
    String TAG_TOKEN_EXP = "TokenExpiresOn";
    String TAG_TOKEN_ISSUE = "TokenIssuedOn";
    String TAG_CONTENT_TYPE = "Content-Type";
    String TAG_DATELASTRETRIEVED = "DateLastRetrieved";
    String TAG_NEW = "NEW";
    String TAG_SHAREDPREF_NAME = "edurp_sharepref";
    String TAG_SHAREDPREF_GET_LAST_TIME = "get_last_time";
    String TAG_PARENTID= "ParentId";
    String TAG_STUDENTID= "StudentId";
    String TAG_ISMOBILE = "isMobile";
    String TAG_LANG_RETRIVE_TIME= "lan_retriev_time";
    String TAG_LAST_LOGIN_TIME= "last_login_time";
}

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

    String URL_GETMOBILEHOME = URL_BASE + "/mobile/GetMobileHome";
    String URL_GETMOBILEMENU= URL_BASE + "/mobile/GetMobileMenu";
    String URL_GETMOBILEDETAILS= URL_BASE + "/mobile/GetMobileDetails";

    String URL_SAVELIKENCOMMENT= URL_BASE + "/mobile/SaveLikeNComment";
    String URL_GETMOBILECOMMENTSNLIKES= URL_BASE + "/mobile/GetMobileCommentsnLikes";

    String URL_GETMOBILENOTICEBAORD= URL_BASE + "/mobile/GetMobileNoticeBoard";
    String URL_REGISTRATION = URL_BASE + "/Account/Register";
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
    String TAG_STUDENTID= "StudentId";
    String TAG_ISMOBILE = "isMobile";
    String TAG_LANG_RETRIVE_TIME= "lan_retriev_time";
    String TAG_LAST_LOGIN_TIME= "last_login_time";
    String TAG_AUTHTOKEN = "auth_token";
    String TAG_SAVED_USERID = "saved_userid";
    String TAG_USERTYPE_PARENT = "P";
    String TAG_USERTYPE_STUDENT = "S";
    String TAG_USER_TYPE = "user_type";
    String TAG_DEFAULT_CHILD = "default_child";
    String TAG_MENUCODE="MenuCode";
    String TAG_USERID="UserId";
    String TAG_PARENTID="ParentId";
    String TAG_USERTYPE="UserType";
    String TAG_LASTRETRIEVED="LastRetrieved";
    int TAG_UNAUTHORIZED_CODE = 401;
    int TAG_SUCCESS_CODE = 200;
    String TAG_COMMENT = "Comment";
    String TAG_ISLIKE = "IsLike";
    String TAG_ISDELETE = "IsDelete";
    String TAG_REFERENCEID = "ReferenceId";
    String TAG_MESSAGEBODYHTML = "MessageBodyHTML";
    String TAG_NEWS_GET_LAST_TIME = "get_news_last_time";
    String TAG_NEWS = "news";
    String TAG_OK = "OK";
    String TAG_GETMOBILEDETAILS = "getmobiledetails_ws";
    String TAG_NEWSBODY = "NewsBody";
    String TAG_IMAGE = "I";
    String TAG_VIDEO = "V";

    String TAG_SAVELIKECOMMENT = "SaveLikeNComment";
    String TAG_MESSAGERESULT="MessageResult";
    String TAG_MOBILENOTICEBOARD = "GetMobileNoticeBoard";
    String TAG_REFERENCEDATE = "ReferenceDate";
    String TAG_VALUE_NOT_PAID = "Not Paid";
    String TAG_ENG = "eng";
    String TAG_BHASHA = "bhasha";

    String TAG_LANG_LOGIN = "lblLogin";
    String TAG_LANG_USERNAME = "lblUserName";
    String TAG_LANG_PASSWORD = "lblPassword";
    String TAG_LANG_FORGOTPASSWORD = "lblForgetPassword";
    String TAG_LANG_SUCCESS = "lblSuccess";
    String TAG_LANG_PROCESSDING = "lblProceseding";
}


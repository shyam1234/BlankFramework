package com.malviya.blankframework.constant;

/**
 * Created by Admin on 26-11-2016.
 */

public interface WSContant {
    String URL_BASE = "http://stpl-edurpapi.azurewebsites.net/api";
    String URL_LOGIN = URL_BASE + "/authenticate";
    String URL_FORGET = URL_BASE + "/Account/ForgetPassword?EmailAddress=";
    //http://stpl-edurpapi.azurewebsites.net/api/Account/ChangePassword?EmailAddress=a1@a.com&Password=login@123
    String URL_CHANGEPASSWORD = URL_BASE + "/Account/ChangePassword?EmailAddress";
    String URL_REGISTRATION = URL_BASE + "/Account/Register";
    String TAG_LOGIN = "login";
    String TAG_AUTHORIZATION = "Authorization";
}

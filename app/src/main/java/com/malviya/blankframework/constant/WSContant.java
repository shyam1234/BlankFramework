package com.malviya.blankframework.constant;

/**
 * Created by Admin on 26-11-2016.
 */

public interface WSContant {
    String URL_BASE = "http://stpl-edurpapi.azurewebsites.net/api";
    String URL_LOGIN = URL_BASE + "/authenticate";
    String URL_FORGET = URL_BASE + "/Account/ForgetPassword?";
    String URL_CHANGEPASSWORD = URL_BASE + "/Account/ChangePassword?";
    String URL_REGISTRATION = URL_BASE + "/Account/Register";
    String TAG_LOGIN = "login";
    String TAG_AUTHORIZATION = "Authorization";
    String TAG_EMIALADDRESS = "EmailAddress";
    String TAG_PASSWORD = "Password";
    String TAG_CHANGE_PASSWORD = "change_password";
    String TAAG_DATA = "Data";
    String TAG_SUCCESS = "Success";
}

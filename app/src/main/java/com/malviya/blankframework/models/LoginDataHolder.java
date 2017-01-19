package com.malviya.blankframework.models;

import com.google.gson.annotations.SerializedName;
import com.malviya.blankframework.interfaces.IModel;

/**
 * Created by Admin on 30-12-2016.
 */

public class LoginDataHolder extends IModel {

    @SerializedName("Data")
    public Data data ;

    public LoginDataHolder(){
        data = new Data();
    }
    public static class Data  extends IModel{
        /*{
        "Data":{
        "UserId":43,
        "UniversityId":1,
        "UserName":"Admin1",
        "EmailAddress":"a1@a.com",
        "PhoneNumber":"858585858",
        "Password":"login@123",
        "LastLogin":null,
        "IsActive":true,
        "IsLocked":false,
        "CreatedOn":"2016-12-24T14:33:35.067",
        "PassAttemptCount":0,
        "AuthenticationToken":null,
        "Status":"C",
        "UserStatus":"Completed",
        "LastLoginIPAddress":null,
        "IsDeleted":null,
        "Address":null,
        "CityId":null,
        "CityName":null,
        "Districtid":null,
        "DistrictName":null,
        "StateId":null,
        "StateName":null,
        "CountryId":null,
        "DefaultLanguage":"B",
        "UserTypeId":88,
        "DeviceType":null,
        "UserType":"Admin",
        "DeviceToken":null,
        "DeviceIdentifier":null,
        "TOTAL":0,
        "RoleCode":"ADMIN,OPERATOR",
        "RoleId":"1,2"
        }
       }*/


        @SerializedName("UserId")
        public int UserId;
        @SerializedName("UniversityId")
        public int UniversityId;
        @SerializedName("UserName")
        public String UserName;
        @SerializedName("EmailAddress")
        public String EmailAddress;
        @SerializedName("PhoneNumber")
        public String PhoneNumber;
        @SerializedName("LastLogin")
        public String LastLogin;
        @SerializedName("IsActive")
        public boolean IsActive;
        @SerializedName("IsLocked")
        public boolean IsLocked;
        @SerializedName("CreatedOn")
        public String CreatedOn;
        @SerializedName("PassAttemptCount")
        public int PassAttemptCount;
        @SerializedName("AuthenticationToken")
        public String AuthenticationToken;
        @SerializedName("Status")
        public String Status;
        @SerializedName("UserStatus")
        public String UserStatus;
        @SerializedName("LastLoginIPAddress")
        public String LastLoginIPAddress;
        @SerializedName("IsDeleted")
        public String IsDeleted;
        @SerializedName("Address")
        public String Address;
        @SerializedName("CityId")
        public String CityId;
        @SerializedName("CityName")
        public String CityName;
        @SerializedName("Districtid")
        public String Districtid;
        @SerializedName("DistrictName")
        public String DistrictName;
        @SerializedName("StateId")
        public String StateId;
        @SerializedName("StateName")
        public String StateName;
        @SerializedName("CountryId")
        public String CountryId;
        @SerializedName("DefaultLanguage")
        public String DefaultLanguage;
        @SerializedName("UserTypeId")
        public String UserTypeId;
        @SerializedName("DeviceType")
        public String DeviceType;
        @SerializedName("UserType")
        public String UserType;
        @SerializedName("DeviceToken")
        public String DeviceToken;
        @SerializedName("DeviceIdentifier")
        public String DeviceIdentifier;
        @SerializedName("TOTAL")
        public String TOTAL;
        @SerializedName("RoleCode")
        public String RoleCode;
        @SerializedName("RoleId")
        public String RoleId;

    }
}

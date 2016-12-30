package com.malviya.blankframework.models;

import com.google.gson.annotations.SerializedName;
import com.malviya.blankframework.interfaces.IModel;

/**
 * Created by Admin on 30-12-2016.
 */

public class LoginDataHolder extends IModel {
    public final static String KEY = "LoginDataHolder";
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


    public String getRoleCode() {
        return RoleCode;
    }

    public void setRoleCode(String roleCode) {
        RoleCode = roleCode;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getUniversityId() {
        return UniversityId;
    }

    public void setUniversityId(int universityId) {
        UniversityId = universityId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getLastLogin() {
        return LastLogin;
    }

    public void setLastLogin(String lastLogin) {
        LastLogin = lastLogin;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public boolean isLocked() {
        return IsLocked;
    }

    public void setLocked(boolean locked) {
        IsLocked = locked;
    }

    public String getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        CreatedOn = createdOn;
    }

    public int getPassAttemptCount() {
        return PassAttemptCount;
    }

    public void setPassAttemptCount(int passAttemptCount) {
        PassAttemptCount = passAttemptCount;
    }

    public String getAuthenticationToken() {
        return AuthenticationToken;
    }

    public void setAuthenticationToken(String authenticationToken) {
        AuthenticationToken = authenticationToken;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(String userStatus) {
        UserStatus = userStatus;
    }

    public String getLastLoginIPAddress() {
        return LastLoginIPAddress;
    }

    public void setLastLoginIPAddress(String lastLoginIPAddress) {
        LastLoginIPAddress = lastLoginIPAddress;
    }

    public String getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        IsDeleted = isDeleted;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getDistrictid() {
        return Districtid;
    }

    public void setDistrictid(String districtid) {
        Districtid = districtid;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getStateId() {
        return StateId;
    }

    public void setStateId(String stateId) {
        StateId = stateId;
    }

    public String getStateName() {
        return StateName;
    }

    public void setStateName(String stateName) {
        StateName = stateName;
    }

    public String getCountryId() {
        return CountryId;
    }

    public void setCountryId(String countryId) {
        CountryId = countryId;
    }

    public String getDefaultLanguage() {
        return DefaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        DefaultLanguage = defaultLanguage;
    }

    public String getUserTypeId() {
        return UserTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        UserTypeId = userTypeId;
    }

    public String getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(String deviceType) {
        DeviceType = deviceType;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getDeviceToken() {
        return DeviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        DeviceToken = deviceToken;
    }

    public String getDeviceIdentifier() {
        return DeviceIdentifier;
    }

    public void setDeviceIdentifier(String deviceIdentifier) {
        DeviceIdentifier = deviceIdentifier;
    }

    public String getTOTAL() {
        return TOTAL;
    }

    public void setTOTAL(String TOTAL) {
        this.TOTAL = TOTAL;
    }

    public String getRoleId() {
        return RoleId;
    }

    public void setRoleId(String roleId) {
        RoleId = roleId;
    }


}

package com.malviya.blankframework.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 28-01-2017.
 */

public class TableNoticeBoardDataModel {

    /*{
        "ParentId":239,
        "StudentId":328,
        "MenuCode":"FEE",
        "ReferenceId":411,
        "PublishedOn":"20170304093851",
        "ExpiryDate":"20170314075352"
        },
    */

    @SerializedName("ParentId")
    private  String ParentId;
    @SerializedName("StudentId")
    private  String StudentId ;
    @SerializedName("MenuCode")
    private  String MenuCode;
    @SerializedName("ReferenceId")
    private  String RederenceId ;
    @SerializedName("PublishedOn")
    private  String PublishedOn ;
    @SerializedName("ExpiryDate")
    private  String ExpiryDate ;
    @SerializedName("ReferenceDate")
    private  String ReferenceDate ;

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getMenuCode() {
        return MenuCode;
    }

    public void setMenuCode(String menuCode) {
        MenuCode = menuCode;
    }

    public String getRederenceId() {
        return RederenceId;
    }

    public void setReferenceId(String rederenceId) {
        RederenceId = rederenceId;
    }

    public String getPublishedOn() {
        return PublishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        PublishedOn = publishedOn;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }

    public String getReferenceDate() {
        return ReferenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        ReferenceDate = referenceDate;
    }
}

package com.malviya.blankframework.models;

/**
 * Created by Admin on 28-01-2017.
 */

public class TableNoticeBoardDataModel {
    private  String ParentId;
    private  String StudentId ;
    private  String MenuCode;
    private  String RederenceId ;

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

    public void setRederenceId(String rederenceId) {
        RederenceId = rederenceId;
    }
}

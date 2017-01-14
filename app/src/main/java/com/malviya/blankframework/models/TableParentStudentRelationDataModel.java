package com.malviya.blankframework.models;

/**
 * Created by Admin on 14-01-2017.
 */

public class TableParentStudentRelationDataModel {
    private String is_default = "";
    private String parent_id = "";
    private String studentid = "";

    public String getIs_default() {
        return is_default;
    }

    public void setIsDefault(String is_default) {
        this.is_default = is_default;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParentId(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }
}

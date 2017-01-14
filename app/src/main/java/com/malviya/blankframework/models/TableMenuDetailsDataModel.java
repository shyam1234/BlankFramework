package com.malviya.blankframework.models;

/**
 * Created by Admin on 14-01-2017.
 */

public class TableMenuDetailsDataModel {
    private String alert_count = "";
    private String date_updated = "";
    private String menu_code = "";
    private String parent_id = "";
    private String student_id = "";

    public String getAlert_count() {
        return alert_count;
    }

    public void setAlert_count(String alert_count) {
        this.alert_count = alert_count;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }

    public String getMenu_code() {
        return menu_code;
    }

    public void setMenuCode(String menu_code) {
        this.menu_code = menu_code;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParentId(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getStudentId() {
        return student_id;
    }

    public void setStudentId(String student_id) {
        this.student_id = student_id;
    }
}

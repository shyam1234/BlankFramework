package com.malviya.blankframework.models;

/**
 * Created by Admin on 11-12-2016.
 */

public class DashboardCellDataHolder {
    private int image;
    private String text;
    private String alert_count;
    private String date_updated;
    private String menu_code;
    private String parent_id;
    private String student_id;
    private int color;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNotification() {
        return alert_count;
    }

    public void setNotification(String notification) {
        this.alert_count = notification;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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

    public void setMenu_code(String menu_code) {
        this.menu_code = menu_code;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParentId(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudentId(String student_id) {
        this.student_id = student_id;
    }
}

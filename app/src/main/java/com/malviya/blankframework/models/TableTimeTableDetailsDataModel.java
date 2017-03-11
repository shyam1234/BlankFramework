package com.malviya.blankframework.models;

import com.google.gson.annotations.SerializedName;
import com.malviya.blankframework.interfaces.IModel;

/**
 * Created by Admin on 05-02-2017.
 */

public class TableTimeTableDetailsDataModel extends IModel {
    @SerializedName("MenuCode")
    private String MenuCode;
    @SerializedName("StudentId")
    private int StudentId;
    @SerializedName("ReferenceDate")
    private String ReferenceDate;
    @SerializedName("SubjectName")
    private String SubjectName;
    @SerializedName("TTime")
    private String TTime;
    @SerializedName("Faculty")
    private String Faculty;
    @SerializedName("RoomName")
    private String RoomName;

    public String getMenuCode() {
        return MenuCode;
    }

    public void setMenuCode(String menuCode) {
        MenuCode = menuCode;
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public String getReferenceDate() {
        return ReferenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        ReferenceDate = referenceDate;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getTTime() {
        return TTime;
    }

    public void setTTime(String TTime) {
        this.TTime = TTime;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String roomName) {
        RoomName = roomName;
    }
}

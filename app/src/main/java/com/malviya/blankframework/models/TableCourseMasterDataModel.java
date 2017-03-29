package com.malviya.blankframework.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 21-01-2017.
 */

public class TableCourseMasterDataModel {

    @SerializedName("StudentId")
    private int StudentId;
    @SerializedName("Course")
    private int Course;
    @SerializedName("Semester")
    private String Semester;
    @SerializedName("LastRetrieved")
    private String LastRetrieved;


    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public int getCourse() {
        return Course;
    }

    public void setCourse(int course) {
        Course = course;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getLastRetrieved() {
        return LastRetrieved;
    }

    public void setLastRetrieved(String lastRetrieved) {
        LastRetrieved = lastRetrieved;
    }
}

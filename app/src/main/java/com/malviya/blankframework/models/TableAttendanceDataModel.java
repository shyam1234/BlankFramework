package com.malviya.blankframework.models;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.malviya.blankframework.interfaces.IModel;

/**
 * Created by Admin on 21-01-2017.
 */

public class TableAttendanceDataModel extends IModel implements Comparable<TableAttendanceDataModel> {

    @SerializedName("StudentId")
    private int StudentId;
    @SerializedName("Course")
    private int Course;
    @SerializedName("Semester")
    private String Semester;
    @SerializedName("SubjectName")
    private String SubjectName;
    @SerializedName("Total")
    private String Total;
    @SerializedName("Present")
    private String Present;
    @SerializedName("Absent")
    private String Absent;
    @SerializedName("Percentage")
    private String Percentage;
    @SerializedName("Color")
    private String Color;


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

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getPresent() {
        return Present;
    }

    public void setPresent(String present) {
        Present = present;
    }

    public String getAbsent() {
        return Absent;
    }

    public void setAbsent(String absent) {
        Absent = absent;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }


    @Override
    public int compareTo(@NonNull TableAttendanceDataModel o) {
        return 0;
    }
}

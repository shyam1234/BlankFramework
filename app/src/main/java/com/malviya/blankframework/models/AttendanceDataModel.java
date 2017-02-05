package com.malviya.blankframework.models;

/**
 * Created by Admin on 05-02-2017.
 */

public class AttendanceDataModel {
    private int totalDays;
    private int absentDays;
    private int subjectValue;

    public int getSubjectValue() {
        return subjectValue;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public int getAbsentDays() {
        return absentDays;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public void setAbsentDays(int absentDays) {
        this.absentDays = absentDays;
    }

    public void setSubjectValue(int subjectValue) {
        this.subjectValue = subjectValue;
    }
}

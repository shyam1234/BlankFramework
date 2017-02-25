package com.malviya.blankframework.models;

/**
 * Created by Admin on 05-02-2017.
 */

public class TableFeeMasterDataModel {
    private String MenuCode;
    private int ParentId;
    private int StudentId;
    private int ReferenceId;
    private String StudentNumber;
    private String StudentName;
    private String CourseName;
    private String SemsterName;
    private String TotalDue;
    private String DueDate;
    private String PublishedOn;
    private String PublishedBy;
    private String ExpiryDate;


    public String getMenuCode() {
        return MenuCode;
    }

    public void setMenuCode(String menuCode) {
        MenuCode = menuCode;
    }

    public int getParentId() {
        return ParentId;
    }

    public void setParentId(int parentId) {
        ParentId = parentId;
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public int getReferenceId() {
        return ReferenceId;
    }

    public void setReferenceId(int referenceId) {
        ReferenceId = referenceId;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        CourseName = courseName;
    }

    public String getSemsterName() {
        return SemsterName;
    }

    public void setSemsterName(String semsterName) {
        SemsterName = semsterName;
    }

    public String getTotalDue() {
        return TotalDue;
    }

    public void setTotalDue(String totalDue) {
        TotalDue = totalDue;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getPublishedOn() {
        return PublishedOn;
    }

    public void setPublishedOn(String publishedOn) {
        PublishedOn = publishedOn;
    }

    public String getPublishedBy() {
        return PublishedBy;
    }

    public void setPublishedBy(String publishedBy) {
        PublishedBy = publishedBy;
    }

    public String getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        ExpiryDate = expiryDate;
    }
}

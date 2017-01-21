package com.malviya.blankframework.models;

/**
 * Created by Admin on 14-01-2017.
 */

public class TableStudentDetailsDataModel {
    private String CourseCode = "";
    private String gender = "";
    private String imageurl = "";
    private int student_id;
    private String student_name = "";
    private int university_id;
    private String StudentNumber;
    private String DateOfBirth;

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        this.CourseCode = courseCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudentId(int student_id) {
        this.student_id = student_id;
    }

    public String getFullName() {
        return student_name;
    }

    public void setFullName(String student_name) {
        this.student_name = student_name;
    }

    public int getUniversity_id() {
        return university_id;
    }

    public void setUniversity_id(int university_id) {
        this.university_id = university_id;
    }

    public String getStudentNumber() {
        return StudentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        StudentNumber = studentNumber;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }
}
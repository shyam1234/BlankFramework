package com.malviya.blankframework.models;

import com.google.gson.annotations.SerializedName;
import com.malviya.blankframework.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 31-03-2017.
 */

public class   GetMobileAttendanceDetailDataModel extends IModel{
    @SerializedName("MessageResult")
    private String MessageResult;
    @SerializedName("MessageBody")
    private MessageBodyDataModel MessageBody = new MessageBodyDataModel();


    public String getMessageResult() {
        return MessageResult;
    }

    public MessageBodyDataModel getMessageBody() {
        return MessageBody;
    }


    public class MessageBodyDataModel {
        @SerializedName("StudentDetailList")
        private ArrayList<StudentDetailListDataModel> StudentDetailList = new ArrayList<StudentDetailListDataModel>();
        @SerializedName("StudentAttendanceDetailList")
        private ArrayList<TableAttendanceDataModel> StudentAttendanceDetailList = new ArrayList<TableAttendanceDataModel>();

        public ArrayList<StudentDetailListDataModel> getStudentDetailList() {
            return StudentDetailList;
        }

        public void setStudentDetailList(ArrayList<StudentDetailListDataModel> studentDetailList) {
            StudentDetailList = studentDetailList;
        }


        public ArrayList<TableAttendanceDataModel> getStudentAttendanceDetailList() {
            return StudentAttendanceDetailList;
        }

        public void setStudentAttendanceDetailList(ArrayList<TableAttendanceDataModel> studentAttendanceDetailList) {
            StudentAttendanceDetailList = studentAttendanceDetailList;
        }
    }

    private class StudentDetailListDataModel {
        /* {
        "StudentId": 335,
        "Course": "CSE-A",
        "Semester": "Semester 1"
      }*/
        private int StudentId;
        private String Course;
        private String Semester;

        public int getStudentId() {
            return StudentId;
        }

        public void setStudentId(int studentId) {
            StudentId = studentId;
        }

        public String getCourse() {
            return Course;
        }

        public void setCourse(String course) {
            Course = course;
        }

        public String getSemester() {
            return Semester;
        }

        public void setSemester(String semester) {
            Semester = semester;
        }
    }


}

package com.malviya.blankframework.models;

import com.google.gson.annotations.SerializedName;
import com.malviya.blankframework.interfaces.IModel;

import java.util.ArrayList;

/**
 * Created by Admin on 30-12-2016.
 */

public class GetMobileMenuDataHolder extends IModel {
    /*{
          "MessageResult": "OK",
          "MessageBody": {
            "NoticeBoardMenuList": [],
            "NewsMasterMenuList": [
              {
                "ParentId": 118,
                "StudentId": 7,
                "MenuCode": "NEW",
                "ReferenceId": 5,
                "DocumentMasterId": 53,
                "DocumentId": 0,
                "ReferenceTitle": "test1212",
                "ShortBody": null,
                "ThumbnailPath": "https://edurpstorage.blob.core.windows.net/edurpcontainer/DEV/0/53/0T?sv=2015-12-11&sr=c&sig=6r9npqZoHfm0hDqROXuKB9fR7zBR0LyF8hsW9tY8zTg%3D&se=2017-02-19T13%3A17%3A09Z&sp=rwl",
                "PublishedOn": "20170101000000",
                "PublishedBy": "Admin",
                "ExpiryDate": "20170301000000",
                "TotalComments": 0,
                "TotalLikes": 0
              }
            ],
            "StudentOverallSummary": []
          }
    }*/
    //-------------------------------------------------------------------------------------------

    @SerializedName("MessageResult")
    private String MessageResult;

    public GetMobileMenuDataHolder.MessageBody getMessageBody() {
        return MessageBody;
    }

    @SerializedName("MessageBody")
    private MessageBody MessageBody;

    public GetMobileMenuDataHolder() {
        MessageBody = new MessageBody();
    }

    public String getMessageResult() {
        return MessageResult;
    }

    public void setMessageResult(String messageResult) {
        MessageResult = messageResult;
    }

    private static class MessageBody {
        /*  "NoticeBoardMenuList": [],
            "NewsMasterMenuList": [
              {
                "ParentId": 118,
                "StudentId": 7,
                "MenuCode": "NEW",
                "ReferenceId": 5,
                "DocumentMasterId": 53,
                "DocumentId": 0,
                "ReferenceTitle": "test1212",
                "ShortBody": null,
                "ThumbnailPath": "https://edurpstorage.blob.core.windows.net/edurpcontainer/DEV/0/53/0T?sv=2015-12-11&sr=c&sig=6r9npqZoHfm0hDqROXuKB9fR7zBR0LyF8hsW9tY8zTg%3D&se=2017-02-19T13%3A17%3A09Z&sp=rwl",
                "PublishedOn": "20170101000000",
                "PublishedBy": "Admin",
                "ExpiryDate": "20170301000000",
                "TotalComments": 0,
                "TotalLikes": 0
              }
            ],
            "StudentOverallSummary": []*/
        //-------------------------------------------------------------------------------------------

        @SerializedName("NoticeBoardMenuList")
        private ArrayList<TableNoticeBoardDataModel> NoticeBoardMenuList;

        @SerializedName("NewsMasterMenuList")
        private ArrayList<TableNewsMasterDataModel> NewsMasterMenuList;

        @SerializedName("StudentOverallSummary")
        private ArrayList<StudentOverallSummaryDataModel> StudentOverallSummary;


        public ArrayList<TableNoticeBoardDataModel> getNoticeBoardMenuList() {
            return NoticeBoardMenuList;
        }


        public ArrayList<TableNewsMasterDataModel> getNewsMasterMenuList() {
            return NewsMasterMenuList;
        }

        public ArrayList<StudentOverallSummaryDataModel> getStudentOverallSummary() {
            return StudentOverallSummary;
        }
    }

}

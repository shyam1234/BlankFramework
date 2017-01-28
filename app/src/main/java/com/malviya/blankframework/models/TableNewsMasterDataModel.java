package com.malviya.blankframework.models;

/**
 * Created by Admin on 28-01-2017.
 */

public class TableNewsMasterDataModel {

    private  String ParentId;
    private  String StudentId;
    private  String NewsId= "";
    private  String NewsTitle;
    private  String ShortBody;
    private  String NewsBody;
    private  String ThumbNailPath;
    private  String PublishedOn;
    private  String PublishedBy;
    private  String TotalComments;
    private  String TotalLikes;

    public String getParentId() {
        return ParentId;
    }

    public void setParentId(String parentId) {
        ParentId = parentId;
    }

    public String getStudentId() {
        return StudentId;
    }

    public void setStudentId(String studentId) {
        StudentId = studentId;
    }

    public String getNewsId() {
        return NewsId;
    }

    public void setNewsId(String newsId) {
        NewsId = newsId;
    }

    public String getNewsTitle() {
        return NewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        NewsTitle = newsTitle;
    }

    public String getShortBody() {
        return ShortBody;
    }

    public void setShortBody(String shortBody) {
        ShortBody = shortBody;
    }

    public String getNewsBody() {
        return NewsBody;
    }

    public void setNewsBody(String newsBody) {
        NewsBody = newsBody;
    }

    public String getThumbNailPath() {
        return ThumbNailPath;
    }

    public void setThumbNailPath(String thumbNailPath) {
        ThumbNailPath = thumbNailPath;
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

    public String getTotalComments() {
        return TotalComments;
    }

    public void setTotalComments(String totalComments) {
        TotalComments = totalComments;
    }

    public String getTotalLikes() {
        return TotalLikes;
    }

    public void setTotalLikes(String totalLikes) {
        TotalLikes = totalLikes;
    }
}

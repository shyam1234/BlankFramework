package com.malviya.blankframework.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 02-03-2017.
 */

public class NewsDetailsCommentLikeDataModel {

        /*{
    "LikedOn":"29-Jan-2017",
    "LikedBy":"Parent 1",
    "UserType":"P"
    }*/

        /*{
    "Comment":"sdfasdfasdf",
    "CommentedOn":"04-Feb-2017",
    "CommentedBy":"Admin2",
    "UserType":"P",
    "CommentId":15
    },*/



    private String imageURL;
    @SerializedName("LikedBy")
    private String LikedBy;
    @SerializedName("CommentedBy")
    private String CommentedBy;
    @SerializedName("LikedOn")
    private String LikedOn;
    @SerializedName("CommentedOn")
    private String CommentedOn;
    @SerializedName("Comment")
    private String Comment;
    @SerializedName("UserType")
    private String UserType;
    @SerializedName("CommentId")
    private int CommentId;
    private String tag;      //for compare btn like and comment


    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLikedBy() {
        return LikedBy;
    }

    public void setLikedBy(String likedBy) {
        this.LikedBy = likedBy;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        this.Comment = comment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public int getCommentId() {
        return CommentId;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }

    public String getCommentedOn() {
        return CommentedOn;
    }

    public void setCommentedOn(String commentedOn) {
        CommentedOn = commentedOn;
    }

    public String getLikedOn() {
        return LikedOn;
    }

    public void setLikedOn(String likedOn) {
        LikedOn = likedOn;
    }

    public String getCommentedBy() {
        return CommentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        CommentedBy = commentedBy;
    }
}

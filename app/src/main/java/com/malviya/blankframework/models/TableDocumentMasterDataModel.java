package com.malviya.blankframework.models;

/**
 * Created by Admin on 28-01-2017.
 */

public class TableDocumentMasterDataModel {
    private  String referenceid;
    private  String menucode;
    private  String documentname;
    private  String documentpath;
    private  String documentextn;
    private  String isattachment;
    private  String mediatype;
    private  String sortorder;
    private  String documentId;

    public String getReferenceid() {
        return referenceid;
    }

    public void setReferenceid(String referenceid) {
        this.referenceid = referenceid;
    }

    public String getMenucode() {
        return menucode;
    }

    public void setMenucode(String menucode) {
        this.menucode = menucode;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public String getDocumentpath() {
        return documentpath;
    }

    public void setDocumentpath(String documentpath) {
        this.documentpath = documentpath;
    }

    public String getDocumentextn() {
        return documentextn;
    }

    public void setDocumentextn(String documentextn) {
        this.documentextn = documentextn;
    }

    public String getIsattachment() {
        return isattachment;
    }

    public void setIsattachment(String isattachment) {
        this.isattachment = isattachment;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    public String getSortorder() {
        return sortorder;
    }

    public void setSortorder(String sortorder) {
        this.sortorder = sortorder;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
}

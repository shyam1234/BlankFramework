package com.malviya.blankframework.models;

import com.google.gson.annotations.SerializedName;
import com.malviya.blankframework.interfaces.IModel;

import java.sql.Date;
import java.util.ArrayList;


/**
 * Created by Admin on 31-12-2016.
 */
public class LanguageArrayDataModel extends IModel {

    /*{
      "Data": {},
      "LanguageArray": [
        {
          "UniversityId": 1,
          "ConversionId": 1,
          "ConversionCode": "AC",
          "EnglishVersion": "Admin Cancelled",
          "BahasaVersion": "Admin Dibatalkan",
          "DateModified": "2016-11-27T00:00:00"
        }
       }*/

    public ArrayList<LanguageDataModel> LanguageArray;
    public LanguageArrayDataModel() {
        LanguageArray = new ArrayList<LanguageDataModel>();
    }

    public static class LanguageDataModel {
          /*{
          "UniversityId": 1,
          "ConversionId": 1,
          "ConversionCode": "AC",
          "EnglishVersion": "Admin Cancelled",
          "BahasaVersion": "Admin Dibatalkan",
          "DateModified": "2016-11-27T00:00:00"
        }*/

        @SerializedName("UniversityId")
        public int UniversityId;
        @SerializedName("ConversionId")
        public int ConversionId;
        @SerializedName("ConversionCode")
        public String ConversionCode;
        @SerializedName("EnglishVersion")
        public String EnglishVersion;
        @SerializedName("BahasaVersion")
        public String BahasaVersion;
        @SerializedName("DateModified")
        public String DateModified;
    }
}

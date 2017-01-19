package com.malviya.blankframework.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.models.TableStudentDetailsDataModel;
import com.malviya.blankframework.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 19-01-2017.
 */

public class CommonInfo {
    private SQLiteDatabase mDB;
    private Context mContext;

    public void openDB(Context pContext) {
        mContext = pContext;
        DatabaseHelper helper = DatabaseHelper.getInstance(pContext);
        mDB = helper.getWritableDatabase();
    }

    public void closeDB() {
        if (mDB != null) {
            mDB = null;
        }
    }

    public ArrayList<TableStudentDetailsDataModel> getAllChildWRTParent(String parentId) {
        ArrayList<TableStudentDetailsDataModel> list = new ArrayList<TableStudentDetailsDataModel>();
        try {
            if (mDB != null) {
                String query = "select * from " + TableStudentDetails.TABLE_NAME + " join " + TableParentStudentRelation.TABLE_NAME
                        + " on " + TableStudentDetails.TABLE_NAME + "." + TableStudentDetails.COL_STUDENT_ID
                        + "=" + TableParentStudentRelation.TABLE_NAME + "." + TableParentStudentRelation.COL_STUDENTID
                        + " where " + TableParentStudentRelation.TABLE_NAME + "." + TableParentStudentRelation.COL_PARENTID + "='" + parentId + "'";
                AppLog.log("query getCourse: ",query);

                Cursor cursor = mDB.rawQuery(query, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableStudentDetailsDataModel model = new TableStudentDetailsDataModel();
                        model.setGender(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_GENDER)));
                        model.setStudentId(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_STUDENT_ID)));
                        model.setCourse(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_COURSE)));
                        model.setImageurl(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_IMAGEURL)));
                        model.setStudent_name(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_STUDENT_NAME)));
                        model.setUniversity_id(cursor.getString(cursor.getColumnIndex(TableStudentDetails.COL_UNIVERSITY_ID)));
                        list.add(model);
                        AppLog.log("getAllChildWRTParent getStudent_name: ", model.getStudent_name());
                        AppLog.log("getAllChildWRTParent getCourse: ", model.getCourse());

                    } while (cursor.moveToNext());

                } else {
                    Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        } catch (Exception e) {
            AppLog.errLog("getAllChildWRTParent", e.getMessage());
        } finally {
            return list;
        }
    }
}

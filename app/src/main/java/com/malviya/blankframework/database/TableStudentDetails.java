package com.malviya.blankframework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.utils.AppLog;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableStudentDetails {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableStudentDetails";
    private static final String TABLE_NAME = "table_student_details";
    private static final String COL_COURSE = "course";
    private static final String COL_GENDER = "gender";
    private static final String COL_IMAGEURL = "imageurl";
    private static final String COL_STUDENT_ID = "student_id";
    private static final String COL_STUDENT_NAME = "student_name";
    private static final String COL_UNIVERSITY_ID = "university_id";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_COURSE + " varchar(255), "
            + COL_GENDER + " varchar(255), "
            + COL_IMAGEURL + " varchar(255), "
            + COL_STUDENT_ID + " varchar(255), "
            + COL_STUDENT_NAME + " varchar(255), "
            + COL_UNIVERSITY_ID + " varchar(255) "
            + " ) ";


    public void openDB(Context pContext) {
        DatabaseHelper helper = DatabaseHelper.getInstance(pContext);
        mDB = helper.getWritableDatabase();
    }

    public void closeDB() {
        if (mDB != null) {
            mDB = null;
        }
    }

    //--------------------------------------------------------------------------------------------------------------------

    public void dropTable() {
        try {
            if (mDB != null) {
                mDB.execSQL(DROP_TABLE);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }

    public void reset() {
        try {
            if (mDB != null) {
                mDB.execSQL(TRUNCATE_TABLE);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }


}

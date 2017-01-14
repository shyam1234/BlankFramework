package com.malviya.blankframework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.utils.AppLog;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableMenuDetails {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableMenuDetails";
    private static final String TABLE_NAME = "table_menudetails";
    private static final String COL_ALERT_COUNT = "alert_count";
    private static final String COL_DATE_UPDATED = "date_updated";
    private static final String COL_MENU_CODE = "menu_code";
    private static final String COL_PARENT_ID = "parent_id";
    private static final String COL_STUDENT_ID = "student_id";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_ALERT_COUNT + " varchar(255), "
            + COL_DATE_UPDATED + " varchar(255), "
            + COL_MENU_CODE + " varchar(255), "
            + COL_PARENT_ID + " varchar(255), "
            + COL_STUDENT_ID + " varchar(255)"
            + " ) ";
    //For Foreign key
    //  + " FOREIGN KEY ("+TASK_CAT+") REFERENCES "+CAT_TABLE+"("+CAT_ID+"));";

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

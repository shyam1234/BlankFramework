package com.malviya.blankframework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.models.TableNoticeBoardDataModel;
import com.malviya.blankframework.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableNoticeBoard {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableNoticeBoard";
    private static final String TABLE_NAME = "table_noticeboard";
    private static final String COL_PARENTID = "ParentId";
    private static final String COL_STUDENTID = "StudentId";
    private static final String COL_MENUCODE = "MenuCode";
    private static final String COL_REFERENCEID = "RederenceId";
    private static final String COL_PUBLISHEDON = "PublishedOn";
    private static final String COL_EXPIRYDATE = "ExpiryDate";


    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_PARENTID + " int , "
            + COL_STUDENTID + " int , "
            + COL_MENUCODE + " char(3), "
            + COL_REFERENCEID + " int , "
            + COL_PUBLISHEDON + " varchar(255) , "
            + COL_EXPIRYDATE + " varchar(255)  "
            + " )";

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

    //---------------------------------------------------------------------------------------

    public void insert(ArrayList<TableNoticeBoardDataModel> list) {
        try {
            if (mDB != null) {
                for (TableNoticeBoardDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_PARENTID, holder.getParentId());
                    value.put(COL_STUDENTID, holder.getStudentId());
                    value.put(COL_MENUCODE, holder.getMenuCode());
                    value.put(COL_REFERENCEID, holder.getRederenceId());
                    value.put(COL_PUBLISHEDON, holder.getPublishedOn());
                    value.put(COL_EXPIRYDATE, holder.getExpiryDate());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: ", holder.getRederenceId() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableNoticeBoardDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                    + COL_MENUCODE + " = '" + model.getMenuCode() + "' , "
                    + COL_PARENTID + " = '" + model.getParentId() + "' , "
                    + COL_STUDENTID + " = '" + model.getStudentId() + "' , "
                    + COL_PUBLISHEDON + " = '" + model.getPublishedOn() + "' , "
                    + COL_REFERENCEID + " = '" + model.getRederenceId() + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    AppLog.log("isExists ", "" + true);
                    return true;
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("isIDExists", e.getMessage());
        }
        return false;
    }

    public boolean deleteRecord(TableNoticeBoardDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_PUBLISHEDON + "=? and "+ COL_MENUCODE + "=? and "+COL_PARENTID + "=? and "+COL_STUDENTID + "=? and "+COL_REFERENCEID + "=?", new String[]{"" + holder.getPublishedOn(),"" + holder.getMenuCode(),"" + holder.getParentId(),"" + holder.getStudentId(),"" + holder.getRederenceId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableNoticeBoard" + e.getMessage());
        }
        return false;
    }


}

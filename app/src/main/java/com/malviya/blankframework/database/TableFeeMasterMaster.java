package com.malviya.blankframework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.models.TableFeeMasterDataModel;
import com.malviya.blankframework.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableFeeMasterMaster {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableFeeMasterMaster";
    private static final String TABLE_NAME = "table_feemaster";
    private static final String COL_MENUCODE = "menucode";
    private static final String COL_PARENTID = "parentid";
    private static final String COL_STUDENTID = "studentId";
    private static final String COL_REFERENCEID = "referenceid";
    private static final String COL_STUDENTNUMBER = "studentnumber";
    private static final String COL_STUDENTNAME = "studentname";
    private static final String COL_COURSENAME = "coursename";
    private static final String COL_SEMESTERNAME = "semestername";
    private static final String COL_TOTALDUE = "totaldue";
    private static final String COL_DUEDATE = "duedate";
    private static final String COL_PUBLISHEDON = "publishedon";
    private static final String COL_PUBLISHEDBY = "publishedby";
    private static final String COL_EXPIRYDATE = "expirydate";
    private static final String COL_FEETITLE = "feetitle";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_MENUCODE + " char(10), "
            + COL_PARENTID + " int, "
            + COL_STUDENTID + " int, "
            + COL_REFERENCEID + " int, "
            + COL_STUDENTNAME + " varchar(255), "
            + COL_STUDENTNUMBER + " varchar(255), "
            + COL_DUEDATE + " varchar(255), "
            + COL_COURSENAME + " varchar(255), "
            + COL_SEMESTERNAME + " varchar(255), "
            + COL_TOTALDUE + " varchar(255), "
            + COL_PUBLISHEDON + " varchar(255), "
            + COL_PUBLISHEDBY + " varchar(255), "
            + COL_FEETITLE + " varchar(255), "
            + COL_EXPIRYDATE + " varchar(255) "
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

    public void insert(ArrayList<TableFeeMasterDataModel> list) {
        try {
            if (mDB != null) {
                for (TableFeeMasterDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_MENUCODE, holder.getMenuCode());
                    value.put(COL_STUDENTID, holder.getStudentId());
                    value.put(COL_PARENTID, holder.getParentId());
                    value.put(COL_REFERENCEID, holder.getReferenceId());
                    value.put(COL_STUDENTNUMBER, holder.getStudentNumber());
                    value.put(COL_STUDENTNAME, holder.getStudentName());
                    value.put(COL_DUEDATE, holder.getDueDate());
                    value.put(COL_COURSENAME, holder.getCourseName());
                    value.put(COL_SEMESTERNAME, holder.getSemsterName());
                    value.put(COL_TOTALDUE, holder.getTotalDue());
                    value.put(COL_PUBLISHEDON, holder.getPublishedOn());
                    value.put(COL_PUBLISHEDBY, holder.getPublishedBy());
                    value.put(COL_FEETITLE, holder.getFeeTitle());
                    value.put(COL_EXPIRYDATE, holder.getExpiryDate());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: getParentId ", "" + holder.getParentId());
                    AppLog.log(TABLE_NAME + " inserted: getStudentId ", holder.getStudentId() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableFeeMasterDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PARENTID + " = '" + model.getParentId() + "' and " + COL_STUDENTID + " = '" + model.getStudentId() + "'";
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

    public boolean deleteRecord(TableFeeMasterDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_PARENTID + "=? && " + COL_STUDENTID + "=? ", new String[]{"" + holder.getParentId(), "" + holder.getStudentId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableFeeMasterDataModel" + e.getMessage());
        }
        return false;
    }


    public TableFeeMasterDataModel getInfo(String menuCode, String rederenceId) {
        TableFeeMasterDataModel holder = new TableFeeMasterDataModel();
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_MENUCODE + " = '" + menuCode + "' and " + COL_REFERENCEID + " = '" + rederenceId + "'";
            Cursor cursor = mDB.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    holder.setCourseName(cursor.getString(cursor.getColumnIndex(COL_COURSENAME)));
                    holder.setMenuCode(cursor.getString(cursor.getColumnIndex(COL_MENUCODE)));
                    holder.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                    holder.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                    holder.setReferenceId(cursor.getInt(cursor.getColumnIndex(COL_REFERENCEID)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNUMBER)));
                    holder.setStudentName(cursor.getString(cursor.getColumnIndex(COL_STUDENTNAME)));
                    holder.setSemsterName(cursor.getString(cursor.getColumnIndex(COL_SEMESTERNAME)));
                    holder.setTotalDue(cursor.getString(cursor.getColumnIndex(COL_TOTALDUE)));
                    holder.setDueDate(cursor.getString(cursor.getColumnIndex(COL_DUEDATE)));
                    holder.setPublishedOn(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDON)));
                    holder.setPublishedBy(cursor.getString(cursor.getColumnIndex(COL_PUBLISHEDBY)));
                    holder.setExpiryDate(cursor.getString(cursor.getColumnIndex(COL_EXPIRYDATE)));
                    holder.setFeeTitle(cursor.getString(cursor.getColumnIndex(COL_FEETITLE)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            AppLog.errLog("getInfo", e.getMessage());
        }
        return holder;
    }
}
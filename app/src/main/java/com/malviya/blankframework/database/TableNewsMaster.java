package com.malviya.blankframework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.models.TableNewsMasterDataModel;
import com.malviya.blankframework.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableNewsMaster {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableNewsMaster";
    private static final String TABLE_NAME = "table_newsmaster";
    private static final String COL_PARENTID= "ParentId";
    private static final String COL_STUDENTID = "StudentId";
    private static final String COL_NEWSID= "NewsId";
    private static final String COL_NEWSTITLE = "NewsTitle";
    private static final String COL_SHORTBODY = "ShortBody";
    private static final String COL_NEWSBODY = "NewsBody";
    private static final String COL_THUMBNAILPATH = "ThumbNailPath";
    private static final String COL_PUBLISHEDON = "PublishedOn";
    private static final String COL_PUBLISHEDBY = "PublishedBy";
    private static final String COL_TOTALCOMMENTS = "TotalComments";
    private static final String COL_TOTALLIKES = "TotalLikes";

    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_PARENTID + " int , "
            + COL_STUDENTID + " int , "
            + COL_NEWSID + " int, "
            + COL_NEWSTITLE + " varchar(100), "
            + COL_SHORTBODY + "  varchar(100) , "
            + COL_NEWSBODY + "  varchar(100) , "
            + COL_THUMBNAILPATH + " varchar(100), "
            + COL_PUBLISHEDON + " datetime , "
            + COL_PUBLISHEDBY + " int , "
            + COL_TOTALCOMMENTS + " int , "
            + COL_TOTALLIKES + " int "
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

    public void insert(ArrayList<TableNewsMasterDataModel> list) {
        try {
            if (mDB != null) {
                for (TableNewsMasterDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_PARENTID, holder.getParentId());
                    value.put(COL_STUDENTID, holder.getStudentId());
                    value.put(COL_NEWSID, holder.getNewsId());
                    value.put(COL_NEWSTITLE, holder.getNewsTitle());
                    value.put(COL_SHORTBODY, holder.getShortBody());
                    value.put(COL_NEWSBODY, holder.getNewsBody());
                    value.put(COL_THUMBNAILPATH, holder.getThumbNailPath());
                    value.put(COL_PUBLISHEDON, holder.getPublishedOn());
                    value.put(COL_PUBLISHEDBY, holder.getPublishedBy());
                    value.put(COL_TOTALCOMMENTS, holder.getTotalComments());
                    value.put(COL_TOTALLIKES, holder.getTotalLikes());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: ", holder.getNewsId() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableNewsMasterDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_NEWSID + " = '" + model.getNewsId() + "'";
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

    public boolean deleteRecord(TableNewsMasterDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_NEWSID + "=?", new String[]{"" + holder.getNewsId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableNewsMasterDataModel" + e.getMessage());
        }
        return false;
    }


}

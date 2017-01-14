package com.malviya.blankframework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.R;
import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.models.DashboardCellDataHolder;
import com.malviya.blankframework.models.LanguageArrayDataModel;
import com.malviya.blankframework.models.TableMenuDetailsDataModel;
import com.malviya.blankframework.utils.AppLog;

import java.util.ArrayList;

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
            + COL_STUDENT_ID + " varchar(255), "
            //For Foreign key
            + " FOREIGN KEY (" + COL_MENU_CODE + ") REFERENCES " + TableMenuMaster.TABLE_NAME + "(" + TableMenuMaster.COL_MENUCODE + "));";

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

    public void insert(ArrayList<TableMenuDetailsDataModel> list) {
        try {
            if (mDB != null) {
                for (TableMenuDetailsDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_ALERT_COUNT, holder.getAlert_count());
                    value.put(COL_DATE_UPDATED, holder.getDate_updated());
                    value.put(COL_MENU_CODE, holder.getMenu_code());
                    value.put(COL_PARENT_ID, holder.getParent_id());
                    value.put(COL_STUDENT_ID, holder.getStudentId());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: ", "getStudentId " + holder.getStudentId() + " holder.getParent_id() " + holder.getParent_id() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableMenuDetailsDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_PARENT_ID + " = '" + model.getParent_id()
                    + "' AND " + COL_STUDENT_ID + " = '" + model.getStudentId()+"'";
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

    public boolean deleteRecord(TableMenuDetailsDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_PARENT_ID + "=? and " + COL_STUDENT_ID + "=? ", new String[]{holder.getParent_id(), holder.getStudentId()});
                AppLog.log("deleteRecord ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableMenuDetailsDataModel" + e.getMessage());
        }
        return false;
    }


    public ArrayList<DashboardCellDataHolder> getNotificationCellData(String parentId, String studentId) {
        ArrayList<DashboardCellDataHolder> list = new ArrayList<DashboardCellDataHolder>();
        try {
            AppLog.log("getNotificationCellData++++++","");
            if (mDB != null) {
//                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + COL_PARENT_ID + "='" + parentId
//                        + "' AND " + COL_STUDENT_ID + "='" + studentId+"'";
                String selectQuery = "Select * from "+TABLE_NAME+" join "+TableMenuMaster.TABLE_NAME+" on "+TABLE_NAME+"."+COL_MENU_CODE+"="+TableMenuMaster.TABLE_NAME+"."+TableMenuMaster.COL_MENUCODE;
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        DashboardCellDataHolder model = new DashboardCellDataHolder();
                        model.setColor(R.color.colorLightYellow);
                        model.setImage(R.drawable.noticeboard);
                        model.setNotification(cursor.getString(cursor.getColumnIndex(COL_ALERT_COUNT)));
                        model.setParentId(cursor.getString(cursor.getColumnIndex(COL_PARENT_ID)));
                        model.setMenu_code(cursor.getString(cursor.getColumnIndex(COL_MENU_CODE)));
                        model.setStudentId(cursor.getString(cursor.getColumnIndex(COL_STUDENT_ID)));
                        model.setText(cursor.getString(cursor.getColumnIndex(TableMenuMaster.COL_MENU_DESCRIPTION)));
                        AppLog.log("getNotificationCellData parentId",parentId);
                        AppLog.log("getNotificationCellData studentId ",studentId);
                        AppLog.log("getNotificationCellData",model.getText());
                        AppLog.log("getNotificationCellData",model.getMenu_code());
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog("getNotificationCellData", e.getMessage());
        } finally {
            return list;
        }

    }

}

package com.malviya.blankframework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.fragments.HomeFragment;
import com.malviya.blankframework.models.DashboardCellDataHolder;
import com.malviya.blankframework.models.TableParentStudentMenuDetailsDataModel;
import com.malviya.blankframework.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableParentStudentMenuDetails {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableParentStudentMenuDetails";
    private static final String TABLE_NAME = "table_menudetails";
    private static final String COL_COUNT = "ColumnCount";
    private static final String COL_SUBCRIPTIONCODE = "SubscriptionCode";
    private static final String COL_PARENTID = "ParentId";
    private static final String COL_STUDENTID = "StudentId";
    private static final String COL_ISACTIVE = "IsActive";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_TABLE = "Create table " + TABLE_NAME + "( "
            + COL_ISACTIVE + " integer , "
            + COL_COUNT + " integer , "
            + COL_SUBCRIPTIONCODE + " varchar(255), "
            + COL_PARENTID + " integer , "
            + COL_STUDENTID + " integer ,  "
            //For Foreign key
            + " FOREIGN KEY (" + COL_SUBCRIPTIONCODE + ") REFERENCES " + TableLanguage.TABLE_NAME + "(" + TableLanguage.CONVERSION_CODE + "));";


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

    public void insert(ArrayList<TableParentStudentMenuDetailsDataModel> list) {
        try {
            if (mDB != null) {
                for (TableParentStudentMenuDetailsDataModel holder : list) {
                    if (isExists(holder)) {
                        deleteRecord(holder);
                    }
                    //----------------------------------------
                    ContentValues value = new ContentValues();
                    value.put(COL_COUNT, holder.getAlert_count());
                    value.put(COL_ISACTIVE, holder.getIsActive());
                    value.put(COL_SUBCRIPTIONCODE, holder.getMenu_code());
                    value.put(COL_PARENTID, holder.getParent_id());
                    value.put(COL_STUDENTID, holder.getStudentId());
                    long row = mDB.insert(TABLE_NAME, null, value);
                    AppLog.log(TABLE_NAME + " inserted: ", "getStudentId " + holder.getStudentId() + " holder.getParent_id() " + holder.getParent_id() + " row: " + row);
                }
            }
        } catch (Exception e) {
            AppLog.errLog("insert", e.getMessage());
        }
    }


    public boolean isExists(TableParentStudentMenuDetailsDataModel model) {
        try {
            String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_SUBCRIPTIONCODE + " = '" + model.getMenu_code()+ "'"
                    +" and "+ COL_PARENTID + " = '" + model.getParent_id()+ "'"
                    +" and "+ COL_STUDENTID + " = '" + model.getStudentId()+ "'";
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

    public boolean deleteRecord(TableParentStudentMenuDetailsDataModel holder) {
        try {
            if (mDB != null) {
                long row = mDB.delete(TABLE_NAME, COL_SUBCRIPTIONCODE + "=? and "+ COL_PARENTID + "=? " +
                        "and "+ COL_STUDENTID + "=?", new String[]{holder.getMenu_code(),""+holder.getParent_id(),""+holder.getStudentId()});
                AppLog.log("deleteRecord from TableParentStudentMenuDetailsDataModel ", "" + row);
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "deleteRecord from TableParentStudentMenuDetailsDataModel" + e.getMessage());
        }
        return false;
    }

    private int position;
    public ArrayList<DashboardCellDataHolder> getHomeFragmentData(int parentId, int studentId) {
        ArrayList<DashboardCellDataHolder> list = new ArrayList<DashboardCellDataHolder>();
        try {
            if (mDB != null) {
                String selectQuery = "Select * from " + TABLE_NAME
                        + " join " + TableLanguage.TABLE_NAME + " , " + TableStudentDetails.TABLE_NAME + " , " + TableUniversityMaster.TABLE_NAME
                        + " on " + TABLE_NAME + "." + COL_SUBCRIPTIONCODE + "=" + TableLanguage.TABLE_NAME + "." + TableLanguage.CONVERSION_CODE
                        + " and " + TABLE_NAME + "." + COL_STUDENTID + "=" + TableStudentDetails.TABLE_NAME + "." + TableStudentDetails.COL_STUDENT_ID
                        + " and " + TableStudentDetails.TABLE_NAME + "." + TableStudentDetails.COL_UNIVERSITY_ID + "=" + TableUniversityMaster.TABLE_NAME + "." + TableUniversityMaster.COL_UNIVERSITY_ID
                        + " where " + TABLE_NAME + "." + COL_PARENTID + "='" + parentId
                        + "' or " + TABLE_NAME + "." + COL_STUDENTID + "='" + studentId + "'";
                AppLog.log("getHomeFragmentData ++++selectQuery++++++++++++++++",selectQuery);
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                position = 0 ;
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        DashboardCellDataHolder model = new DashboardCellDataHolder();
                        model.setColor(HomeFragment.mMenuColor[position]);
                        model.setImage(HomeFragment.mMenuImage[position]);
                        model.setUniversity_id(cursor.getInt(cursor.getColumnIndex(TableUniversityMaster.COL_UNIVERSITY_ID)));
                        model.setUniversity_name(cursor.getString(cursor.getColumnIndex(TableUniversityMaster.COL_UNIVERSITY_NAME)));
                        model.setNotification(cursor.getInt(cursor.getColumnIndex(COL_COUNT)));
                        model.setIsActive(cursor.getInt(cursor.getColumnIndex(COL_ISACTIVE)));
                        model.setParentId(cursor.getInt(cursor.getColumnIndex(COL_PARENTID)));
                        model.setMenu_code(cursor.getString(cursor.getColumnIndex(COL_SUBCRIPTIONCODE)));
                        model.setStudentId(cursor.getInt(cursor.getColumnIndex(COL_STUDENTID)));
                        model.setText(cursor.getString(cursor.getColumnIndex(TableLanguage.ENGLISH_VERSION)));
                        AppLog.log("getHomeFragmentData parentId", ""+parentId);
                        AppLog.log("getHomeFragmentData studentId ", ""+studentId);
                        AppLog.log("getHomeFragmentData getMenu_code ", model.getMenu_code());
                        AppLog.log("getHomeFragmentData getUniversity_id", ""+model.getUniversity_id());
                        AppLog.log("getHomeFragmentData getUniversity_name ", model.getUniversity_name());
                        AppLog.log("getHomeFragmentData ++++++++++++++++++++","");
                        list.add(model);
                        position++;
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog("getHomeFragmentData", e.getMessage());
        } finally {
            return list;
        }

    }

}

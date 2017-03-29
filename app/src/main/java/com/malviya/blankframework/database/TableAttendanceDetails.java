package com.malviya.blankframework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.models.TableAttendanceDataModel;
import com.malviya.blankframework.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableAttendanceDetails {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableAttendanceDetails";
    public static final String TABLE_NAME = "table_attendacedetails";
    public static final String STUDENTID = "studentid";
    public static final String COURSE = "course";
    public static final String SEMESTER = "semester";
    public static final String SUBJECTNAME = "subjectname";
    public static final String TOTAL = "total";
    public static final String PRESENT = "present";
    public static final String ABSENT = "absent";
    public static final String PERCENTAGE = "percentage";
    public static final String COLOR = "color";

    //-------------------------------------------------------------------------
    public static final String DROP_TABLE_DIARY = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE_DIARY = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_LANGUAGE_TABLE = "Create table " + TABLE_NAME + "( "
            + STUDENTID + " int, "
            + COURSE + " varchar(100), "
            + SEMESTER + " varchar(100), "
            + SUBJECTNAME + " varchar(100), "
            + TOTAL + " varchar(50), "
            + PRESENT + " varchar(50), "
            + ABSENT + " varchar(50), "
            + PERCENTAGE + " varchar(50), "
            + COLOR + " varchar(50) "
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

    public boolean insert(ArrayList<TableAttendanceDataModel> list) {
        try {
            if (mDB != null) {
                for (TableAttendanceDataModel holder : list) {
                    deleteDataIfExist(holder.getStudentId(), holder.getSemester());
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(STUDENTID, holder.getStudentId());
                    contentValues.put(COURSE, holder.getCourse());
                    contentValues.put(SEMESTER, holder.getSemester());
                    contentValues.put(SUBJECTNAME, holder.getSubjectName());
                    contentValues.put(TOTAL, holder.getTotal());
                    contentValues.put(PRESENT, holder.getPresent());
                    contentValues.put(ABSENT, holder.getAbsent());
                    contentValues.put(PERCENTAGE, holder.getPercentage());
                    contentValues.put(COLOR, holder.getColor());
                    mDB.insert(TABLE_NAME, null, contentValues);
                }
                return true;
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return false;
        }
        return false;
    }

    private void deleteDataIfExist(int pStudentId, String pSemester) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + STUDENTID + "=" + pStudentId + " AND " + SEMESTER + "=" + pSemester;
            AppLog.log(TAG, "deleteDataIfExist +++selectQuery():" + selectQuery.toString());
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataIfExist " + e.getMessage());
        }
    }

    public ArrayList<TableAttendanceDataModel> read() {
        try {
            ArrayList<TableAttendanceDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME;
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableAttendanceDataModel model = new TableAttendanceDataModel();
                        model.setStudentId(cursor.getInt(cursor.getColumnIndex(STUDENTID)));
                        model.setSemester((cursor.getString(cursor.getColumnIndex(SEMESTER))));
                        model.setCourse((cursor.getInt(cursor.getColumnIndex(COURSE))));
                        model.setSubjectName((cursor.getString(cursor.getColumnIndex(SUBJECTNAME))));
                        model.setTotal((cursor.getString(cursor.getColumnIndex(TOTAL))));
                        model.setPresent((cursor.getString(cursor.getColumnIndex(PRESENT))));
                        model.setPercentage((cursor.getString(cursor.getColumnIndex(PERCENTAGE))));
                        model.setAbsent((cursor.getString(cursor.getColumnIndex(ABSENT))));
                        model.setColor(cursor.getString(cursor.getColumnIndex(COLOR)));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public ArrayList<TableAttendanceDataModel> read(int pStudentId) {
        try {
            ArrayList<TableAttendanceDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + STUDENTID + "='" + pStudentId + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        TableAttendanceDataModel model = new TableAttendanceDataModel();
                        model.setStudentId(cursor.getInt(cursor.getColumnIndex(STUDENTID)));
                        model.setSemester((cursor.getString(cursor.getColumnIndex(SEMESTER))));
                        model.setCourse((cursor.getInt(cursor.getColumnIndex(COURSE))));
                        model.setSubjectName((cursor.getString(cursor.getColumnIndex(SUBJECTNAME))));
                        model.setTotal((cursor.getString(cursor.getColumnIndex(TOTAL))));
                        model.setPresent((cursor.getString(cursor.getColumnIndex(PRESENT))));
                        model.setPercentage((cursor.getString(cursor.getColumnIndex(PERCENTAGE))));
                        model.setAbsent((cursor.getString(cursor.getColumnIndex(ABSENT))));
                        model.setColor(cursor.getString(cursor.getColumnIndex(COLOR)));
                        list.add(model);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return list;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public TableAttendanceDataModel getValueBySem(String pSemseter) {
        try {
            TableAttendanceDataModel model = new TableAttendanceDataModel();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + SEMESTER + "='" + pSemseter + "'";
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        model.setStudentId(cursor.getInt(cursor.getColumnIndex(STUDENTID)));
                        model.setSemester((cursor.getString(cursor.getColumnIndex(SEMESTER))));
                        model.setCourse((cursor.getInt(cursor.getColumnIndex(COURSE))));
                        model.setSubjectName((cursor.getString(cursor.getColumnIndex(SUBJECTNAME))));
                        model.setTotal((cursor.getString(cursor.getColumnIndex(TOTAL))));
                        model.setPresent((cursor.getString(cursor.getColumnIndex(PRESENT))));
                        model.setPercentage((cursor.getString(cursor.getColumnIndex(PERCENTAGE))));
                        model.setAbsent((cursor.getString(cursor.getColumnIndex(ABSENT))));
                        model.setColor(cursor.getString(cursor.getColumnIndex(COLOR)));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return model;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public void dropTable() {
        try {
            if (mDB != null) {
                mDB.execSQL(DROP_TABLE_DIARY);
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
                mDB.execSQL(TRUNCATE_TABLE_DIARY);
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from reset " + e.getMessage());
        }
    }


}

package com.malviya.blankframework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.models.DiaryModel;
import com.malviya.blankframework.utils.AppLog;

import java.util.ArrayList;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableTest {
    public static final String TAG = "TableTest";
    private static final String TABLE_DIARY = "table_diary";
    private static final String INDEX = "index";
    private static final String TOPIC = "topic";
    private static final String DESCRIPTION = "description";
    private static final String NOTE = "note";
    public static final String DROP_TABLE_DIARY = "Drop table if exists " + TABLE_DIARY;
    public static final String TRUNCATE_TABLE_DIARY = "TRUNCATE TABLE " + TABLE_DIARY;
    private SQLiteDatabase mDB;

    public static final String CREATE_TABLE_DIARY = "Create table " + TABLE_DIARY + "( "
            + INDEX + " int "
            + TOPIC + " varchar(255)"
            + DESCRIPTION + " varchar(255)"
            + NOTE + " varchar(255)"
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

    public boolean insert(ArrayList<DiaryModel> list) {
        try {
            if (mDB != null) {
                for (DiaryModel holder : list) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(INDEX, holder.getIndex());
                    contentValues.put(TOPIC, holder.getTopic());
                    contentValues.put(DESCRIPTION, holder.getDescription());
                    contentValues.put(NOTE, holder.getNote());
                    return mDB.insert(TABLE_DIARY, null, contentValues) > 0;
                }
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return false;
        }
        return false;
    }

    public ArrayList<DiaryModel> read() {
        try {
            ArrayList<DiaryModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_DIARY;
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        DiaryModel diaryModel = new DiaryModel();
                        diaryModel.setIndex(cursor.getString(cursor.getColumnIndex(INDEX)));
                        diaryModel.setDescription(cursor.getString(cursor.getColumnIndex(DESCRIPTION)));
                        diaryModel.setTopic(cursor.getString(cursor.getColumnIndex(TOPIC)));
                        diaryModel.setNote(cursor.getString(cursor.getColumnIndex(NOTE)));
                        list.add(diaryModel);
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

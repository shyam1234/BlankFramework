package com.malviya.blankframework.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.widget.Toast;

import com.malviya.blankframework.application.MyApplication;
import com.malviya.blankframework.models.LanguageArrayDataModel;
import com.malviya.blankframework.utils.AppLog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Admin on 26-11-2016.
 */
public class TableLanguage {
    private SQLiteDatabase mDB;
    //--------------------------------------------------------------------------
    public static final String TAG = "TableLanguage";
    private static final String TABLE_NAME = "table_language";
    private static final String UNIVERSITY_ID = "UniversityId";
    private static final String CONVERSION_ID = "ConversionId";
    private static final String CONVERSION_CODE = "ConversionCode";
    private static final String ENGLISH_VERSION = "EnglishVersion";
    private static final String BAHASA_VERSION = "BahasaVersion";
    private static final String DATE_MODIFIED = "DateModified";
    //-------------------------------------------------------------------------
    public static final String DROP_TABLE_DIARY = "Drop table if exists " + TABLE_NAME;
    public static final String TRUNCATE_TABLE_DIARY = "TRUNCATE TABLE " + TABLE_NAME;


    public static final String CREATE_LANGUAGE_TABLE = "Create table " + TABLE_NAME + "( "
            + UNIVERSITY_ID + " int, "
            + CONVERSION_ID + " int, "
            + CONVERSION_CODE + " varchar(255), "
            + ENGLISH_VERSION + " varchar(255), "
            + BAHASA_VERSION + " varchar(255), "
            + DATE_MODIFIED + " varchar(255) "
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

    public boolean insert(ArrayList<LanguageArrayDataModel.LanguageDataModel> list) {
        try {
            if (mDB != null) {
                for (LanguageArrayDataModel.LanguageDataModel holder : list) {
                    deleteDataIfExist(holder.UniversityId, holder.ConversionId);
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(UNIVERSITY_ID, holder.UniversityId);
                    contentValues.put(CONVERSION_ID, holder.ConversionId);
                    contentValues.put(CONVERSION_CODE, holder.ConversionCode);
                    contentValues.put(ENGLISH_VERSION, holder.EnglishVersion);
                    contentValues.put(BAHASA_VERSION, holder.BahasaVersion);
                    contentValues.put(DATE_MODIFIED, holder.DateModified);
                    return mDB.insert(TABLE_NAME, null, contentValues) > 0;
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

    private void deleteDataIfExist(int pUniversityId, int pConversionId) {
        try {
            String selectQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + UNIVERSITY_ID + "=" + pUniversityId + " AND " + CONVERSION_ID + "=" + pConversionId;
            mDB.execSQL(selectQuery);
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from deleteDataIfExist " + e.getMessage());
        }
    }

    public ArrayList<LanguageArrayDataModel.LanguageDataModel> read() {
        try {
            ArrayList<LanguageArrayDataModel.LanguageDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME;
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        LanguageArrayDataModel.LanguageDataModel model = new LanguageArrayDataModel.LanguageDataModel();
                        model.UniversityId = (cursor.getInt(cursor.getColumnIndex(UNIVERSITY_ID)));
                        model.ConversionId = (cursor.getInt(cursor.getColumnIndex(CONVERSION_CODE)));
                        model.ConversionCode = (cursor.getString(cursor.getColumnIndex(CONVERSION_ID)));
                        model.EnglishVersion = (cursor.getString(cursor.getColumnIndex(ENGLISH_VERSION)));
                        model.BahasaVersion = (cursor.getString(cursor.getColumnIndex(BAHASA_VERSION)));
                        model.DateModified = (cursor.getString(cursor.getColumnIndex(DATE_MODIFIED)));
                        AppLog.errLog(" :", model.EnglishVersion);
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

    public ArrayList<LanguageArrayDataModel.LanguageDataModel> read(int pUniID) {
        try {
            ArrayList<LanguageArrayDataModel.LanguageDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + UNIVERSITY_ID + "=" + pUniID;
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        LanguageArrayDataModel.LanguageDataModel model = new LanguageArrayDataModel.LanguageDataModel();
                        model.UniversityId = (cursor.getInt(cursor.getColumnIndex(UNIVERSITY_ID)));
                        model.ConversionId = (cursor.getInt(cursor.getColumnIndex(CONVERSION_CODE)));
                        model.ConversionCode = (cursor.getString(cursor.getColumnIndex(CONVERSION_ID)));
                        model.EnglishVersion = (cursor.getString(cursor.getColumnIndex(ENGLISH_VERSION)));
                        model.BahasaVersion = (cursor.getString(cursor.getColumnIndex(BAHASA_VERSION)));
                        model.DateModified = (cursor.getString(cursor.getColumnIndex(DATE_MODIFIED)));
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

    public LanguageArrayDataModel.LanguageDataModel getValue(String pKey) {
        try {
            // ArrayList<LanguageArrayDataModel.LanguageDataModel> list = new ArrayList<>();
            if (mDB != null) {
                String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + CONVERSION_CODE + "=" + pKey;
                Cursor cursor = mDB.rawQuery(selectQuery, null);
                if (cursor.moveToFirst()) {
                    do {
                        // get the data into array, or class variable
                        LanguageArrayDataModel.LanguageDataModel model = new LanguageArrayDataModel.LanguageDataModel();
                        model.UniversityId = (cursor.getInt(cursor.getColumnIndex(UNIVERSITY_ID)));
                        model.ConversionId = (cursor.getInt(cursor.getColumnIndex(CONVERSION_CODE)));
                        model.ConversionCode = (cursor.getString(cursor.getColumnIndex(CONVERSION_ID)));
                        model.EnglishVersion = (cursor.getString(cursor.getColumnIndex(ENGLISH_VERSION)));
                        model.BahasaVersion = (cursor.getString(cursor.getColumnIndex(BAHASA_VERSION)));
                        model.DateModified = (cursor.getString(cursor.getColumnIndex(DATE_MODIFIED)));
                        // list.add(model);
                        return model;
                    } while (cursor.moveToNext());
                }
                cursor.close();
            } else {
                Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
            }
            return null;
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
            return null;
        }
    }


    public void getAllValues(final String[] pKeyArray, final Handler handler) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HashMap<String, LanguageArrayDataModel.LanguageDataModel> mHashMap = new HashMap<String, LanguageArrayDataModel.LanguageDataModel>();
                    if (mDB != null) {
                        for (String key : pKeyArray) {
                            String selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + CONVERSION_CODE + "=" + key;
                            Cursor cursor = mDB.rawQuery(selectQuery, null);
                            if (cursor.moveToFirst()) {
                                do {
                                    // get the data into array, or class variable
                                    LanguageArrayDataModel.LanguageDataModel model = new LanguageArrayDataModel.LanguageDataModel();
                                    model.UniversityId = (cursor.getInt(cursor.getColumnIndex(UNIVERSITY_ID)));
                                    model.ConversionId = (cursor.getInt(cursor.getColumnIndex(CONVERSION_CODE)));
                                    model.ConversionCode = (cursor.getString(cursor.getColumnIndex(CONVERSION_ID)));
                                    model.EnglishVersion = (cursor.getString(cursor.getColumnIndex(ENGLISH_VERSION)));
                                    model.BahasaVersion = (cursor.getString(cursor.getColumnIndex(BAHASA_VERSION)));
                                    model.DateModified = (cursor.getString(cursor.getColumnIndex(DATE_MODIFIED)));
                                    mHashMap.put(key, model);
                                } while (cursor.moveToNext());
                            }
                            cursor.close();
                        }
                        handler.sendMessage(handler.obtainMessage(0, mHashMap));
                    } else {
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(), "Need to open DB", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            AppLog.errLog(TAG, "Exception from insert() " + e.getMessage());
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

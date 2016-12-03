package com.malviya.blankframework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 26-11-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_name";
    private static final int DB_VERSION = 1;
    private static DatabaseHelper mInstance;

    public static DatabaseHelper getInstance(Context context){
        if(mInstance==null){
            mInstance = new DatabaseHelper(context);
        }
        return  mInstance;
    }


    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL(TableDiary.CREATE_TABLE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        //modify table if version changes
        if (old_version < new_version) {
            db.execSQL(TableDiary.DROP_TABLE_DIARY);
            onCreate(db);
        }
    }
}

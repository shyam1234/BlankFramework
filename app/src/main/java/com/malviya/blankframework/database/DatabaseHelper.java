package com.malviya.blankframework.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 26-11-2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "db_edurp";
    private static final int DB_VERSION = 1;
    private static DatabaseHelper mInstance;

    public static DatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(context);
        }
        return mInstance;
    }


    private DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create table
        db.execSQL(TableLanguage.CREATE_LANGUAGE_TABLE);
        db.execSQL(TableParentStudentMenuDetails.CREATE_TABLE);
        //db.execSQL(TableMenuMaster.CREATE_TABLE);
        db.execSQL(TableParentMaster.CREATE_TABLE);
        db.execSQL(TableParentStudentAssociation.CREATE_TABLE);
        db.execSQL(TableStudentDetails.CREATE_TABLE);
        db.execSQL(TableUniversityMaster.CREATE_TABLE);

        db.execSQL(TableDocumentMaster.CREATE_TABLE);
        db.execSQL(TableNewsMaster.CREATE_TABLE);
        db.execSQL(TableNoticeBoard.CREATE_TABLE);
        db.execSQL(TableUserMaster.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        //modify table if version changes
        if (old_version < new_version) {
            db.execSQL(TableLanguage.DROP_TABLE_DIARY);
            db.execSQL(TableParentStudentMenuDetails.DROP_TABLE);
            db.execSQL(TableParentMaster.DROP_TABLE);
            db.execSQL(TableParentStudentAssociation.DROP_TABLE);
            db.execSQL(TableStudentDetails.DROP_TABLE);
            db.execSQL(TableUniversityMaster.DROP_TABLE);
            db.execSQL(TableDocumentMaster.DROP_TABLE);
            db.execSQL(TableNewsMaster.DROP_TABLE);
            db.execSQL(TableNoticeBoard.DROP_TABLE);
            db.execSQL(TableUserMaster.DROP_TABLE);

            onCreate(db);
        }
    }
}

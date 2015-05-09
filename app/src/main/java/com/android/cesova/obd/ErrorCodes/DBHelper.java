package com.android.cesova.obd.ErrorCodes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    //version number to upgrade database version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "OBDErrorCode.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_ERRORCODES = "CREATE TABLE " + OBDErrorCode.TABLE + "("
                + OBDErrorCode.KEY_PID + " TEXT PRIMARY KEY ,"
                + OBDErrorCode.KEY_TYPE + " TEXT, "
                + OBDErrorCode.KEY_DESCRIPTION + " TEXT, "
                + OBDErrorCode.KEY_SYMPTOMS + " TEXT, "
                + OBDErrorCode.KEY_CAUSES + " TEXT, "
                + OBDErrorCode.KEY_SOLUTIONS + " TEXT, "
                + OBDErrorCode.KEY_RELATED + " TEXT)";
        db.execSQL(CREATE_TABLE_ERRORCODES);
        Log.d("Database Path", db.getPath());
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone
        db.execSQL("DROP TABLE IF EXISTS " + OBDErrorCode.TABLE);
        // Create tables again
        onCreate(db);
    }

}

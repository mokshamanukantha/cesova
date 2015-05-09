package com.android.cesova.obd.ErrorCodes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mokshaDev on 3/21/2015.
 */
public class OBDErrorCodeManager {

    private DBHelper dbHelper;

    public OBDErrorCodeManager(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insert(OBDErrorCode errorCode) {
        // TODO Auto-generated method stub

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OBDErrorCode.KEY_PID, OBDErrorCode.pid);
        values.put(OBDErrorCode.KEY_TYPE, OBDErrorCode.type);
        values.put(OBDErrorCode.KEY_DESCRIPTION, OBDErrorCode.description);

        values.put(OBDErrorCode.KEY_SYMPTOMS, OBDErrorCode.symptom);
        values.put(OBDErrorCode.KEY_CAUSES, OBDErrorCode.cause);
        values.put(OBDErrorCode.KEY_SOLUTIONS, OBDErrorCode.solution);
        values.put(OBDErrorCode.KEY_RELATED, OBDErrorCode.related);

        // Inserting Row
        long OBDErrorCode_Id = db.insert(OBDErrorCode.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) OBDErrorCode_Id;
    }

    public void delete(int OBDErrorCode_Id) {
        //int OBDErrorCode_Id = getFirstOBDErrorCode();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(OBDErrorCode.TABLE, OBDErrorCode.KEY_PID + "=" + OBDErrorCode_Id, null);
        db.close(); // Closing database connection
    }

    public void update(OBDErrorCode OBDErrorCode) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(OBDErrorCode.KEY_PID, OBDErrorCode.pid);
        values.put(OBDErrorCode.KEY_TYPE, OBDErrorCode.type);
        values.put(OBDErrorCode.KEY_DESCRIPTION, OBDErrorCode.description);

        values.put(OBDErrorCode.KEY_SYMPTOMS, OBDErrorCode.symptom);
        values.put(OBDErrorCode.KEY_CAUSES, OBDErrorCode.cause);
        values.put(OBDErrorCode.KEY_SOLUTIONS, OBDErrorCode.solution);
        values.put(OBDErrorCode.KEY_RELATED, OBDErrorCode.related);

        db.update(com.android.cesova.obd.ErrorCodes.OBDErrorCode.TABLE, values, com.android.cesova.obd.ErrorCodes.OBDErrorCode.KEY_PID + "=" + OBDErrorCode, null);
        db.close(); // Closing database connection
    }

    public ArrayList getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                OBDErrorCode.KEY_PID + "," +
                OBDErrorCode.KEY_TYPE + "," +
                OBDErrorCode.KEY_DESCRIPTION + "," +
                " FROM " + OBDErrorCode.TABLE;

        ArrayList OBDErrorCodeList = new ArrayList();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                OBDErrorCodeList.add(cursor.getString(cursor.getColumnIndex(OBDErrorCode.KEY_PID)) + "_"
                        + cursor.getString(cursor.getColumnIndex(OBDErrorCode.KEY_DESCRIPTION)));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return OBDErrorCodeList;

    }

    public ArrayList<HashMap<String, String>> getOBDErrorCodeList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                OBDErrorCode.KEY_PID + "," +
                OBDErrorCode.KEY_TYPE + "," +
                OBDErrorCode.KEY_DESCRIPTION + "," +
                " FROM " + OBDErrorCode.TABLE;

        //OBDErrorCode OBDErrorCode = new OBDErrorCode();
        ArrayList<HashMap<String, String>> OBDErrorCodeList = new ArrayList<HashMap<String, String>>();


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> OBDErrorCode = new HashMap<String, String>();
                OBDErrorCode.put("pid", cursor.getString(cursor.getColumnIndex(com.android.cesova.obd.ErrorCodes.OBDErrorCode.KEY_PID)));
                OBDErrorCode.put("description", cursor.getString(cursor.getColumnIndex(com.android.cesova.obd.ErrorCodes.OBDErrorCode.KEY_DESCRIPTION)));
                OBDErrorCodeList.add(OBDErrorCode);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return OBDErrorCodeList;

    }

    public OBDErrorCode getOBDErrorCodeById(String Id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                OBDErrorCode.KEY_PID + "," +
                OBDErrorCode.KEY_TYPE + "," +
                OBDErrorCode.KEY_DESCRIPTION + "," +
                OBDErrorCode.KEY_SYMPTOMS + "," +
                OBDErrorCode.KEY_CAUSES + "," +
                OBDErrorCode.KEY_SOLUTIONS + "," +
                OBDErrorCode.KEY_RELATED +
                " FROM " + OBDErrorCode.TABLE
                + " WHERE " +
                OBDErrorCode.KEY_PID + "=" + Id;

        String query = "SELECT pid,type,description FROM OBDErrorCode where pid = '" + Id + "'";

        int iCount = 0;
        OBDErrorCode OBDErrorCode = new OBDErrorCode();

        Cursor cursor = db.rawQuery(query, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                OBDErrorCode.pid = cursor.getString(cursor.getColumnIndex(OBDErrorCode.KEY_PID));
                OBDErrorCode.type = cursor.getString(cursor.getColumnIndex(OBDErrorCode.KEY_TYPE));
                OBDErrorCode.description = cursor.getString(cursor.getColumnIndex(OBDErrorCode.KEY_DESCRIPTION));

                OBDErrorCode.symptom = cursor.getString(cursor.getColumnIndex(OBDErrorCode.KEY_SYMPTOMS));
                OBDErrorCode.cause = cursor.getString(cursor.getColumnIndex(OBDErrorCode.KEY_CAUSES));
                OBDErrorCode.solution = cursor.getString(cursor.getColumnIndex(OBDErrorCode.KEY_SOLUTIONS));
                OBDErrorCode.related = cursor.getString(cursor.getColumnIndex(OBDErrorCode.KEY_RELATED));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return OBDErrorCode;
    }

    private int getFirstOBDErrorCode() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                OBDErrorCode.KEY_PID +
                " FROM " + OBDErrorCode.TABLE
                + " LIMIT 1;";


        int OBDErrorCode_Id = 0;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                OBDErrorCode_Id = cursor.getInt(cursor.getColumnIndex(OBDErrorCode.KEY_PID));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return OBDErrorCode_Id;
    }

    private int getLastOBDErrorCode() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                OBDErrorCode.KEY_PID +
                " FROM " + OBDErrorCode.TABLE
                + " ORDER BY " +
                OBDErrorCode.KEY_PID + " DESC ";
        //" DESC LIMIT 1;" ;


        int OBDErrorCode_Id = 0;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                OBDErrorCode_Id = cursor.getInt(cursor.getColumnIndex(com.android.cesova.obd.ErrorCodes.OBDErrorCode.KEY_PID));
                break;
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return OBDErrorCode_Id;
    }
}

package com.tns.waterbill;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by root on 9/12/16.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{

    public MyDatabaseHelper(Context context){
        super( context, "water_bill", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("*****", "creating table");
        db.execSQL("create table auth( username varchar(15), sessionid varchar(40) )"); //if not exist
        Log.i("*****", "Table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getSessionId(){
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("*****", "querying db");
        try {
            Cursor cursor = db.rawQuery("select sessionid from auth", null);
            String sessionId = cursor.getString(1);
            Log.i("*****", sessionId);
            return sessionId;
        }
        catch(Exception e){
            Log.i("*****", "no-cursor");
            return "no-cursor";
        }

    }

    public void setSessionId( String sessionId ){
        Log.i("******", "Inserting..");
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("username", "rest-webservice");
            values.put("sessionid", sessionId);
            db.insert("auth", null, values);
            Log.i("******", "Inserted");
        }
        catch( Exception e ){
            Log.i("******", e.getMessage());
        }
    }
}

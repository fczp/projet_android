package com.example.pabrand.projet_android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MaBaseSQLite extends SQLiteOpenHelper {

    private final static String CREATE_TABLE = "CREATE TABLE rendezvous(" +
            "id integer primary key," +
            "date varchar(10)," +
            "heure varchar(5))," +
            "contacts text;";

    final static String DROP_TABLE = "drop table rendezvous;";

    public MaBaseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db){

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(DROP_TABLE);
        db.execSQL(CREATE_TABLE);
    }

}
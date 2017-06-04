package com.hackday.anonymousmeet.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Liu jiaohan on 2017-06-04.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String CREATE_MYSAYING="create table Saying ("
            + "id integer primary key autoincrement, "
            + "content text, "
            + "likeamount text)";

    public static final String CREATE_TRACES="create table Traces ("
            + "id integer primary key autoincrement, "
            + "content text, "
            + "likeamount text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MYSAYING);
        db.execSQL(CREATE_TRACES);
        Log.i("TAG", "onCreate: MyDatabase");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

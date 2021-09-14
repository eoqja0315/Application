package com.dbhong.application.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DataBaseHelper";

    private int mVersion = 1;
    private String mDatabaseName;
    private Context mContext;

    public DataBaseHelper(@Nullable Context context, @Nullable String databaseName, int version) {
        super(context, databaseName, null, version);

        mContext = context;
        mDatabaseName = databaseName;
        mVersion = version;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlHelper.getSqlCreateNoteDataEntries());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SqlHelper.getSqlDeleteNoteDataEntries());
        onCreate(db);
        mVersion = newVersion;
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void deleteDatabase(SQLiteDatabase db)
    {
        db.execSQL(SqlHelper.getSqlDeleteNoteDataEntries());
    }

}

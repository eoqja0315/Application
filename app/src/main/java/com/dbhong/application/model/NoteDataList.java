package com.dbhong.application.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.ContactsContract;
import android.util.Log;

import com.dbhong.application.database.DataBaseContract;
import com.dbhong.application.database.DataBaseHelper;
import com.dbhong.application.presenter.Contract;

import java.util.ArrayList;
import java.util.List;

public class NoteDataList extends ArrayList<NoteData> {

    private Contract.NoteDataListPresenter presenter;

    public NoteDataList(Contract.NoteDataListPresenter presenter)
    {
        this.presenter = presenter;
    }

    public int getCheckedItemNum()
    {
        int cnt = 0;

        for(int i = 0; i < size(); i++) {
            if (get(i).getIsChecked()) cnt++;
        }

        return cnt;
    }

    public int getNonTitledItemNum()
    {
        int cnt = 0;

        for(int i = 0; i < size(); i++){
            if(get(i).getNoteTitle().contains("제목 없음"))
            {
                cnt++;
            }
        }

        return cnt;
    }
    public void saveNoteDataListInDataBase(Context context)
    {
        DataBaseHelper dbHelper = new DataBaseHelper(context, DataBaseContract.NoteDataEntry.TABLE_NAME, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        dbHelper.onUpgrade(db, 1, 1);

        for(int i = 0; i < size(); i++)
        {
            ContentValues values = new ContentValues();
            values.put(DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_CREATE_DATE, get(i).getCreateDate());
            values.put(DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_TITLE, get(i).getNoteTitle());
            values.put(DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_CONTENT, get(i).getContent());
            values.put(DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_LAST_EDIT_DATE, get(i).getLastEditDate());
            db.insert(DataBaseContract.NoteDataEntry.TABLE_NAME, null, values);
        }
    }

    public int loadNoteDataListInDataBase(Context context)
    {
        try {
            DataBaseHelper dbHelper = new DataBaseHelper(context, DataBaseContract.NoteDataEntry.TABLE_NAME, 1);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] projection = {"*"};

            Cursor cursor = db.query (
                    DataBaseContract.NoteDataEntry.TABLE_NAME,
                    projection,
                    null, // The columns for the WHERE clause
                    null, // The values for the WHERE clause
                    null,
                    null,
                    null
            );

            while(cursor.moveToNext())
            {
                long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(DataBaseContract.NoteDataEntry._ID));

                String createDate = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_CREATE_DATE));
                String noteTitle = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_CONTENT));
                String lastRevisedDate = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_LAST_EDIT_DATE));

                add(new NoteData(createDate, noteTitle, content, lastRevisedDate));
            }
            cursor.close();
        } catch (SQLiteException e)
        {
            e.printStackTrace();
        }

        return size();
    }

}

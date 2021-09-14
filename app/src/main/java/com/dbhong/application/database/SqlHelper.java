package com.dbhong.application.database;

public class SqlHelper {
    private static final String SQL_CREATE_NOTE_DATA_ENTRIES =
            "CREATE TABLE " + DataBaseContract.NoteDataEntry.TABLE_NAME + " (" +
                    DataBaseContract.NoteDataEntry._ID + " INTEGER PRIMARY KEY," +
                    DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_CREATE_DATE + " TEXT," +
                    DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_TITLE + " TEXT," +
                    DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_CONTENT + " TEXT," +
                    DataBaseContract.NoteDataEntry.COLUMN_NAME_NOTE_LAST_EDIT_DATE + " TEXT)" ;

    private static final String SQL_DELETE_NOTE_DATA_ENTRIES =
            "DROP TABLE IF EXISTS " + DataBaseContract.NoteDataEntry.TABLE_NAME;

    public static String getSqlCreateNoteDataEntries() {
        return SQL_CREATE_NOTE_DATA_ENTRIES;
    }
    public static String getSqlDeleteNoteDataEntries() {
        return SQL_DELETE_NOTE_DATA_ENTRIES;
    }
}

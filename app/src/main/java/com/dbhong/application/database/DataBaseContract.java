package com.dbhong.application.database;

import android.provider.BaseColumns;

public final class DataBaseContract {

    private DataBaseContract() {}

    public static class NoteDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "NoteData";
        public static final String COLUMN_NAME_NOTE_CREATE_DATE = "createDate";
        public static final String COLUMN_NAME_NOTE_TITLE = "title";
        public static final String COLUMN_NAME_NOTE_CONTENT = "content";
        public static final String COLUMN_NAME_NOTE_LAST_EDIT_DATE = "lastReviseDate";
    }

}

package com.dbhong.application.presenter;

import android.content.Context;
import android.provider.ContactsContract;

import com.dbhong.application.model.NoteData;
import com.dbhong.application.model.NoteDataList;

import java.util.ArrayList;

public interface Contract {

    interface MainActivtyView {

    }

    interface NoteDataListPresenter {
        public void addNoteData(NoteData noteData);

        public void saveNoteDataListInDataBase(Context context);
        public int loadNoteDataListInDataBase(Context context);

        public void removeNoteData(int position);

        public NoteData getNoteData(int position);
        public NoteDataList getNoteDataList();
    }
}

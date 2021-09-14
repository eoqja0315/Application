package com.dbhong.application.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import androidx.core.content.ContextCompat;

import com.dbhong.application.database.DataBaseContract;
import com.dbhong.application.database.DataBaseHelper;
import com.dbhong.application.model.NoteData;
import com.dbhong.application.model.NoteDataList;

import java.lang.annotation.Native;
import java.util.ArrayList;

public class NoteDataListPresenter implements Contract.NoteDataListPresenter {

    private Contract.MainActivtyView view;
    private NoteDataList mNoteDataList = null;

    public NoteDataListPresenter(Contract.MainActivtyView view)
    {
        mNoteDataList = new NoteDataList(this);
        this.view = view;
    }

    public NoteDataListPresenter(Contract.MainActivtyView view, NoteDataList noteDataList) {
        mNoteDataList = noteDataList;
        this.view = view;
    }

    @Override
    public void removeNoteData(int position)
    {
        if(mNoteDataList != null)
            mNoteDataList.remove(position);
    }

    @Override
    public NoteData getNoteData(int position) {
        return mNoteDataList.get(position);
    }

    @Override
    public void addNoteData(NoteData noteData)
    {
        try {
            if(noteData.getContent().equals(""))
                return;

            if(noteData.getNoteTitle().equals(""))
            {
                noteData.setNoteTitle("제목 없음 " + (mNoteDataList.size() + 1));
            }

            mNoteDataList.add(noteData);
        } catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public NoteDataList getNoteDataList()
    {
        return mNoteDataList;
    }

    @Override
    public void saveNoteDataListInDataBase(Context context)
    {
        mNoteDataList.saveNoteDataListInDataBase(context);
    }

    @Override
    public int loadNoteDataListInDataBase(Context context)
    {
        return mNoteDataList.loadNoteDataListInDataBase(context);
    }
}

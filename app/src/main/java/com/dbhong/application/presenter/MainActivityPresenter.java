package com.dbhong.application.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dbhong.application.R;
import com.dbhong.application.database.DataBaseContract;
import com.dbhong.application.database.DataBaseHelper;
import com.dbhong.application.model.NoteData;
import com.dbhong.application.model.NoteDataList;
import com.dbhong.application.view.Adapter.NoteListAdapter;

import java.lang.annotation.Native;
import java.util.ArrayList;

public class MainActivityPresenter implements Contract.MainActivityPresenter {

    private Contract.MainActivtyView view;
    protected static NoteDataList mNoteDataList = null;

    public MainActivityPresenter(Contract.MainActivtyView view)
    {
        mNoteDataList = new NoteDataList(this);
        this.view = view;
    }

    @Override
    public void removeNoteData(int position)
    {
        if(mNoteDataList != null)
            mNoteDataList.remove(position);
    }

    @Override
    public void addNoteData(NoteData noteData)
    {
        try {
            if(noteData.getContent().equals(""))
                return;

            if(noteData.getNoteTitle().equals(""))
            {
                noteData.setNoteTitle( "제목 없음 " + (mNoteDataList.size() + 1));
            }

            mNoteDataList.add(noteData);
        } catch(NullPointerException e)
        {
            e.printStackTrace();
        }
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

    @Override
    public void setCreateDate(String createDate, int position)
    {
        mNoteDataList.get(position).setCreateDate(createDate);
    }

    @Override
    public String getCreateDate(int position)
    {
        return mNoteDataList.get(position).getCreateDate();
    }

    @Override
    public void setNoteTitle(String noteTitle, int position)
    {
        mNoteDataList.get(position).setNoteTitle(noteTitle);
    }

    @Override
    public String getNoteTitle(int position)
    {
        return mNoteDataList.get(position).getNoteTitle();
    }

    @Override
    public void setContent(String content, int position)
    {
        mNoteDataList.get(position).setContent(content);
    }

    @Override
    public String getContent(int position)
    {
        return mNoteDataList.get(position).getContent();
    }

    @Override
    public void setLastEditDate(String lastEditDate , int position)
    {
        mNoteDataList.get(position).setLastEdtiDate(lastEditDate);
    }

    @Override
    public String getLastEditDate(int position)
    {
        return mNoteDataList.get(position).getLastEditDate();
    }

    @Override
    public void setIsChecked(boolean isChecked, int position)
    {
        mNoteDataList.get(position).setIsChecked(isChecked);
    }

    @Override
    public boolean getIsChecked(int position)
    {
        return mNoteDataList.get(position).getIsChecked();
    }

    @Override
    public int getChekedItemNum()
    {
        return mNoteDataList.getCheckedItemNum();
    }

    @Override
    public int getNonTitledItemNum()
    {
        return mNoteDataList.getNonTitledItemNum();
    }

    @Override
    public int getItemCount()
    {
        return mNoteDataList.size();
    }

    @Override
    public NoteDataList getNoteDataList() {
        return mNoteDataList;
    }

    @Override
    public void setViewNoteListLongClickEvent(int position) {
        setIsChecked(!getIsChecked(position), position);
        view.setViewNoteListLongClickEvent(position);
    }

    @Override
    public void setViewNoteListClickEvent(int position) {

        int checkedItemNumbeforeCheck = getChekedItemNum();
        setIsChecked(!getIsChecked(position), position);

        view.setViewNoteListClickEvent(position, checkedItemNumbeforeCheck);

    }

    public boolean toolbarActionRemove()
    {

        return view.toolbarActionRemove();
    }

    public boolean toolBarActionCancle()
    {
        for(int i = 0; i < mNoteDataList.size(); i++)
        {
            mNoteDataList.get(i).setIsChecked(false);
        }

        return view.toolBarActionCancle();
    }

    public boolean toolBarActionCopy()
    {
        return view.toolBarActionCopy();
    }

    public boolean toolBarActionSelectAll()
    {
        for(int i = 0; i < mNoteDataList.size(); i++)
        {
            mNoteDataList.get(i).setIsChecked(true);
        }

        return view.toolBarActionSelectAll();
    }
}

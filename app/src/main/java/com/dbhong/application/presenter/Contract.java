package com.dbhong.application.presenter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;

import com.dbhong.application.model.NoteData;
import com.dbhong.application.model.NoteDataList;
import com.dbhong.application.view.Adapter.NoteListAdapter;

import java.util.ArrayList;

public interface Contract {

    interface MainActivtyView {
        public void startAnim(NoteListAdapter.ViewHolder viewHolder, int resId, Animation animation);
        public void setViewNoteListLongClickEvent(int position);
        public void setViewNoteListClickEvent(int position, int checkItemNumBeforeCheck);

        public boolean toolbarActionRemove();
        public boolean toolBarActionCancle();
        public boolean toolBarActionCopy();
        public boolean toolBarActionSelectAll();
    }

    interface MainActivityPresenter {
        public void addNoteData(NoteData noteData);

        public void saveNoteDataListInDataBase(Context context);
        public int loadNoteDataListInDataBase(Context context);

        public void removeNoteData(int position);

        public void setCreateDate(String createDate, int position);
        public String getCreateDate(int position);

        public void setNoteTitle(String noteTitle, int position);
        public String getNoteTitle(int position);

        public void setContent(String content, int position);
        public String getContent(int position);

        public void setLastEditDate(String lastEditDate, int position);
        public String getLastEditDate(int position);

        public void setIsChecked(boolean isChecked, int position);
        public boolean getIsChecked(int position);

        public int getChekedItemNum();
        public int getNonTitledItemNum();

        public int getItemCount();

        public NoteDataList getNoteDataList();

        public void setViewNoteListLongClickEvent(int position);
        public void setViewNoteListClickEvent(int position);
        public boolean toolbarActionRemove();
        public boolean toolBarActionCancle();
        public boolean toolBarActionCopy();
        public boolean toolBarActionSelectAll();

    }

    interface EditNoteActivityView {

    }

    interface EditNoteActivityPresenter {

    }
}

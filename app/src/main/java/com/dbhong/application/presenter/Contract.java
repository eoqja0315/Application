package com.dbhong.application.presenter;

import android.content.Context;
import android.provider.ContactsContract;

import com.dbhong.application.model.NoteData;
import com.dbhong.application.model.NoteDataList;
import com.dbhong.application.view.Adapter.NoteListAdapter;

import java.util.ArrayList;

public interface Contract {

    interface MainActivtyView {
        public void setVisibleNoteListRecyclerCheckBox(NoteListAdapter.ViewHolder viewHolder, int visible);
        public void setVisibleNoteListRecyclerAllCheckBox(int visible);

        public void setCheckedNoteListRecyclerCheckBox(NoteListAdapter.ViewHolder viewHolder, boolean isChecked);
        public void setCheckedNoteListRecyclerAllCheckBox(boolean isChecked);

    }

    interface NoteDataListPresenter {
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
    }
}

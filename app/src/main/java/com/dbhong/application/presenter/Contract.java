package com.dbhong.application.presenter;

import com.dbhong.application.model.NoteData;

public interface Contract {

    interface View {
        public void showNoteName(int position);
    }

    interface Presenter {
        public void addNoteName(String noteName);
        public void addData(String data);

        public void setNoteName(String noteName);
        public void setData(String data);
        public void setDate(String date);

        public NoteData getNoteData();
    }
}

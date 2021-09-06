package com.dbhong.application.presenter;

public interface Contract {

    interface View {
        public void showNoteData();
    }

    interface Presenter {
        public void addNoteName(String noteName);
        public void addData(String data);
    }
}

package com.dbhong.application.model;

import com.dbhong.application.presenter.Contract;

public class NoteData {

    Contract.Presenter presenter;

    static private int count;

    private String mDate;
    private String mNoteName;
    private String mData;
    private int mNoteNum;

    public NoteData(Contract.Presenter presenter) {
        this(presenter, "", "", "");
    }

    public NoteData(Contract.Presenter presenter, String date, String noteName, String data) {
        this.presenter = presenter;
        this.mDate = date;
        this.mNoteName = noteName;
        this.mData = data;
        this.mNoteNum = count++;
    }

    public String getDate() {
        return mDate;
    }

    public String getNoteName() {
        return mNoteName;
    }

    public String getData() {
        return mData;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public void setNoteName(String noteName) {
        this.mNoteName = noteName;
    }

    public void setData(String data) {
        this.mData = data;
    }

}

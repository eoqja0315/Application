package com.dbhong.application.model;

import com.dbhong.application.presenter.Contract;

import java.util.ArrayList;

public class NoteData {

    private String mCreateDate;
    private String mLastEditDate;
    private String mNoteTitle;
    private String mContent;

    public NoteData() {
        this( "", "", "");
    }

    public NoteData(String createDate) {
        this(createDate, "", "", "");
    }

    public NoteData(String createDate, String noteTitle) {
        this(createDate, noteTitle, "", "");
    }

    public NoteData(String createDate, String noteTitle, String content) {
        this(createDate, noteTitle, content, "");
    }

    public NoteData(String createDate, String noteTitle, String content, String lastEditDate) {
        this.mCreateDate = createDate;
        this.mNoteTitle = noteTitle;
        this.mContent = content;
        this.mLastEditDate = lastEditDate;
    }

    public String getCreateDate() {
        return mCreateDate;
    }

    public String getLastEditDate() {
        return mLastEditDate;
    }

    public String getNoteTitle() {
        return mNoteTitle;
    }

    public String getContent() {
        return mContent;
    }

    public void setCreateDate(String createDate) {
        this.mCreateDate = createDate;
    }

    public void setLastEdtiDate(String lastEditDate) {
        this.mLastEditDate = lastEditDate;
    }

    public void setNoteTitle(String noteTitle) {
        this.mNoteTitle = noteTitle;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

}

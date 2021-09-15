package com.dbhong.application.model;

import com.dbhong.application.presenter.Contract;

import java.util.ArrayList;

public class NoteData {

    private String mCreateDate;
    private String mNoteTitle;
    private String mContent;
    private String mLastEditDate;
    private boolean isChecked;

    public NoteData() {
        this( "");
    }

    public NoteData(String createDate) {
        this(createDate, "");
    }

    public NoteData(String createDate, String noteTitle) {
        this(createDate, noteTitle, "");
    }

    public NoteData(String createDate, String noteTitle, String content) {
        this(createDate, noteTitle, content, "");
    }

    public NoteData(String createDate, String noteTitle, String content, String lastEditDate) {
        this(createDate, noteTitle, content, lastEditDate, false);
    }

    private NoteData(String createDate, String noteTitle, String content, String lastEditDate, boolean isChecked) {
        this.mCreateDate = createDate;
        this.mNoteTitle = noteTitle;
        this.mContent = content;
        this.mLastEditDate = lastEditDate;
        this.isChecked = isChecked;
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

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}

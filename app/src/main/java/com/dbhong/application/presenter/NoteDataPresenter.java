package com.dbhong.application.presenter;

import android.provider.ContactsContract;

import androidx.core.content.ContextCompat;

import com.dbhong.application.model.NoteData;

import java.lang.annotation.Native;

public class NoteDataPresenter implements Contract.Presenter {

    Contract.View view;
    NoteData noteData = null;

    public NoteDataPresenter(Contract.View view) {
        noteData = new NoteData(this);
        this.view = view;
    }

    public NoteDataPresenter(Contract.View view, String date, String noteName, String data) {
        noteData = new NoteData(this, date, noteName, data);
        this.view = view;
    }

    @Override
    public void addNoteName(String noteName) {
        noteData.setNoteName(noteData.getNoteName() + noteName);
    }

    @Override
    public void addData(String data) {
        noteData.setData(noteData.getData() + data);
    }

    @Override
    public void setNoteName(String noteName) {
        noteData.setNoteName(noteName);
    }

    @Override
    public void setData(String data) {
        noteData.setData(data);
    }

    @Override
    public void setDate(String date) {
        noteData.setDate(date);
    }

    @Override
    public NoteData getNoteData(){
        return noteData;
    }
}

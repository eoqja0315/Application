package com.dbhong.application.presenter;

import androidx.core.content.ContextCompat;

import com.dbhong.application.model.NoteData;

public class NoteDataPresenter implements Contract.Presenter {

    Contract.View view;
    NoteData noteData = null;

    public void NoteDataPresenter(Contract.View view) {
        noteData = new NoteData(this);
        this.view = view;
    }

    public void NoteDataPresenter(Contract.View view, String date, String noteName, String data) {
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

}

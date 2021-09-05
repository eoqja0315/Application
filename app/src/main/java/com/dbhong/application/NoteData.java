package com.dbhong.application;

public class NoteData {

    static private int count;

    private String date;
    private String noteName;
    private String noteData;
    private int noteNum;

    public NoteData() {
        this.date = "";
        this.noteName = "";
        this.noteData = "";
        this.noteNum = count++;
    }

    public NoteData(String date, String noteName, String noteData) {
        this.date = date;
        this.noteName = noteName;
        this.noteData = noteData;
        this.noteNum = count++;
    }

    public String getDate() {
        return date;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteData() {
        return noteData;
    }

    public int getNoteNum() {
        return noteNum;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }

    public void setNoteData(String noteData) {
        this.noteData = noteData;
    }

    public void setNoteNum(int noteNum) {
        this.noteNum = noteNum;
    }
}

package com.dbhong.application.view.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.dbhong.application.R;

public class NoteDeleteDialog extends Dialog {

    public static final String TAG = "NoteDeleteDialog";

    NoteDeleteDialogListener noteDeleteDialogListener;

    Button buttonConfirm;
    Button buttonCancle;
    public NoteDeleteDialog(@NonNull Context context) {
        super(context);

        setTitle(R.string.delete_note_dialog_title);
        setContentView(R.layout.dialog_delete_note);

        buttonConfirm = findViewById(R.id.buttonDeleteNoteConfirm);
        buttonCancle = findViewById(R.id.buttonDeleteNoteCancle);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDeleteDialogListener.onPositiveClicked();
                dismiss();
            }
        });

        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteDeleteDialogListener.onNegativeClicked();
                dismiss();
            }
        });
    }

    public void setDialogListener(NoteDeleteDialogListener noteDeleteDialogListener){
        this.noteDeleteDialogListener = noteDeleteDialogListener;
    }
}

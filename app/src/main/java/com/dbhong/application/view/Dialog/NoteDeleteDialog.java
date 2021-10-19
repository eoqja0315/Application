package com.dbhong.application.view.Dialog;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.dbhong.application.R;
import com.dbhong.application.presenter.Contract;
import com.dbhong.application.presenter.MainActivityPresenter;
import com.dbhong.application.presenter.NoteDeleteDialogPresenter;
import com.dbhong.application.view.Activity.MainActivity;

public class NoteDeleteDialog extends Dialog implements Contract.NoteDeleteDialogView {

    public static final String TAG = "NoteDeleteDialog";

    NoteDeleteDialogPresenter mPresenter;
    NoteDeleteDialogListener noteDeleteDialogListener;

    Button buttonConfirm;
    Button buttonCancle;
    public NoteDeleteDialog(@NonNull Context context) {
        super(context);

        Log.d(TAG, "NoteDeleteDialog constructor()");

        mPresenter = new NoteDeleteDialogPresenter(this);

        setTitle(R.string.delete_note_dialog_title);
        setContentView(R.layout.dialog_delete_note);

        buttonConfirm = findViewById(R.id.buttonDeleteNoteConfirm);
        buttonCancle = findViewById(R.id.buttonDeleteNoteCancle);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "Dialog onClick");
                mPresenter.onPositive();
                dismiss();
            }
        });

        buttonCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onNegative();
                dismiss();
            }
        });

        Activity owner = (context instanceof Activity) ? (Activity)context : null;
        if (owner != null) {
            setOwnerActivity( (MainActivity) context);
        }
    }

    @Override
    public void onPositive() {
        Log.e(TAG, "Dialog onPositive()");
        ((MainActivity) getOwnerActivity()).getPresenter().noteDeleteDialogPositiveClicked();
    }

    @Override
    public void onNegative() {
        ((MainActivity) getOwnerActivity()).getPresenter().noteDeleteDialogNegativeClicked();
    }
}

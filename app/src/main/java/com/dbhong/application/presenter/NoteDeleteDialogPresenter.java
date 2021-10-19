package com.dbhong.application.presenter;

import android.util.Log;
import com.dbhong.application.model.NoteDataList;

public class NoteDeleteDialogPresenter implements Contract.NoteDeleteDialogPresenter {

    private final String TAG = "NoteDeleteDialog";

    private Contract.NoteDeleteDialogView view;

    public NoteDeleteDialogPresenter(Contract.NoteDeleteDialogView view)
    {
        this.view = view;
    }

    @Override
    public void onPositive() {

        for(int i = 0; i < NoteDataList.getInstance().size(); i++)
        {
            if(NoteDataList.getInstance().get(i).getIsChecked())
            {
                NoteDataList.getInstance().get(i).setIsChecked(false);
                NoteDataList.getInstance().remove(i);
                i--;
            }
        }

        Log.e(TAG, "NoteDeleteDialogPresenter onPositive()");
        view.onPositive();
    }

    @Override
    public void onNegative() {
        //Cancle 처리
        Log.d(TAG, "onNegative()");
        view.onNegative();
    }
}

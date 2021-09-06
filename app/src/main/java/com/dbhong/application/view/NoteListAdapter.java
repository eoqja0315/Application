package com.dbhong.application.view;

import android.content.Context;
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import com.dbhong.application.model.NoteData;

import java.util.ArrayList;

public class NoteListAdapter extends BaseAdapter {

    private ArrayList<NoteData> mNoteDataArrayList;
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean mObjectsFromResources;
    private int mFieldId = 0;
    private final int mResource;
    private int mDropDownResource;

    public NoteListAdapter( Context context, @LayoutRes int resource, ArrayList<NoteData> objects) {
        this(context, resource, 0, objects);
    }

    public NoteListAdapter( Context context, @LayoutRes int resource,
                        @IdRes int textViewResourceId, ArrayList<NoteData> objects) {
        this(context, resource, textViewResourceId, objects, false);
    }

    private NoteListAdapter(Context context, @LayoutRes int resource,
                         @IdRes int textViewResourceId, ArrayList<NoteData> noteDataArrayList, boolean objsFromResources) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = mDropDownResource = resource;
        mNoteDataArrayList = noteDataArrayList;
        mObjectsFromResources = objsFromResources;
        mFieldId = textViewResourceId;
    }

    @Override
    public int getCount() {
        if(mNoteDataArrayList != null)
            return mNoteDataArrayList.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        if(mNoteDataArrayList != null)
            return mNoteDataArrayList.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

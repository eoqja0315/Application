package com.dbhong.application.view.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dbhong.application.R;

public class EditNoteFragment extends Fragment {

    public static final String TAG = "EditNoteFragment";

    private EditText editTextNoteTitle;
    private EditText editTextNoteContent;

    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "content";
    private static final String ARG_PARAM3 = "position";

    private String mNoteTitle;
    private String mNoteContent;
    private int mPosition;

    public EditNoteFragment() {
    }

    public static EditNoteFragment newInstance(String param1, String param2) {
        EditNoteFragment fragment = new EditNoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNoteTitle = getArguments().getString(ARG_PARAM1);
            mNoteContent = getArguments().getString(ARG_PARAM2);
            mPosition = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_edit_note, container, false);

        editTextNoteContent = root.findViewById(R.id.editTextNoteContents);
        editTextNoteTitle = root.findViewById(R.id.editTextNoteTitle);

        editTextNoteTitle.setText(mNoteTitle);
        editTextNoteContent.setText(mNoteContent);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public String getNoteTitle() {
        return editTextNoteTitle.getText().toString();
    }
    public String getNoteContent() {
        return editTextNoteContent.getText().toString();
    }

    public int getPosition() {
        return mPosition;
    }
}
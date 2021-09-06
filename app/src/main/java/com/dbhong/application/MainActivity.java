package com.dbhong.application;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dbhong.application.model.NoteData;
import com.dbhong.application.presenter.Contract;
import com.dbhong.application.view.NoteListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<NoteData> noteList;
    ArrayList<Contract.Presenter> presenterList;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoteListAdapter adapter = new NoteListAdapter(this, R.layout.activity_main , noteList);
        listView = findViewById(R.id.noteListView);
        listView.setAdapter(adapter);
    }
}
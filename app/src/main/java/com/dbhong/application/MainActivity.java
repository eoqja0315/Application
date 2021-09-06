package com.dbhong.application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dbhong.application.model.NoteData;
import com.dbhong.application.presenter.Contract;
import com.dbhong.application.presenter.NoteDataPresenter;
import com.dbhong.application.view.NoteListAdapter;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Contract.View{

    ArrayList<NoteData> noteDataList;
    ArrayList<Contract.Presenter> presenterList;
    NoteListAdapter adapter;

    ListView listView;

    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new NoteListAdapter(this, R.layout.activity_main , noteDataList);
        listView = findViewById(R.id.noteListView);
        listView.setAdapter(adapter);

        actionBar = getSupportActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create:

                presenterList.add(new NoteDataPresenter(this));

                presenterList.get(0).setNoteName("Test Note");
                presenterList.get(0).setData("This is test");
                presenterList.get(0).setDate("2021-09-06");

                noteDataList.add(presenterList.get(0).getNoteData());

                adapter.addAll(noteDataList);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showNoteName(int position) {

    }
}
package com.dbhong.application.view.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.dbhong.application.R;
import com.dbhong.application.Utility.Utility;
import com.dbhong.application.database.DataBaseContract;
import com.dbhong.application.database.DataBaseHelper;
import com.dbhong.application.model.NoteData;
import com.dbhong.application.presenter.Contract;
import com.dbhong.application.presenter.NoteDataListPresenter;
import com.dbhong.application.view.Adapter.ClickAdapterCallback;
import com.dbhong.application.view.Adapter.NoteListAdapter;
import com.dbhong.application.view.Dialog.NoteDeleteDialog;
import com.dbhong.application.view.Dialog.NoteDeleteDialogListener;
import com.google.android.material.internal.VisibilityAwareImageButton;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements Contract.MainActivtyView{

    private static final int CREATE_NOTE = -1;
    public static final String TAG = "NoteListMainActivity";

    NoteDataListPresenter mPresenter;
    NoteListAdapter mAdapter;

    RecyclerView noteListRecyclerView;
    Toolbar toolbar;

    ImageButton imageButtonCreateNote;

    Menu menu;
    MenuInflater menuInflater;

    Animation moveRightAnim;
    Animation moveLeftAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate()");

        mPresenter = new NoteDataListPresenter(this);

        noteListRecyclerView = findViewById(R.id.noteListRecycler);
        noteListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new NoteListAdapter(this, R.layout.activity_main , mPresenter);
        mAdapter.setClickAdapterCallback(clickAdapterCallback);

        noteListRecyclerView.setAdapter(mAdapter);

        toolbar = findViewById(R.id.mainToolbar);
        toolbar.setTitle(R.string.main_toolbar_title);
        setSupportActionBar(toolbar);

        imageButtonCreateNote = findViewById(R.id.imageButtonCreateNote);
        imageButtonCreateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditNoteActivity.class);
                intent.putExtra("title", "");
                intent.putExtra("content", "");
                intent.putExtra("position", -1);

                startActivityResult.launch(intent);
            }
        });

        initialize();
    }

    public void initialize()
    {
        if(mPresenter.loadNoteDataListInDataBase(this) > 0)
            mAdapter.notifyDataSetChanged();

        menu = toolbar.getMenu();
        menuInflater = getMenuInflater();

        moveRightAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.note_recycler_move_right);
        moveLeftAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.note_recycler_move_left);
    }

    private ClickAdapterCallback clickAdapterCallback = new ClickAdapterCallback() {
        @Override
        public void callback(int position) {

            if(mPresenter.getChekedItemNum() == 0)
            {
                Intent intent = new Intent(getApplicationContext(), EditNoteActivity.class);
                intent.putExtra("title", mPresenter.getNoteTitle(position));
                intent.putExtra("content", mPresenter.getContent(position));
                intent.putExtra("position", position);

                startActivityResult.launch(intent);
            }
            else
            {
                mPresenter.setIsChecked(!mPresenter.getIsChecked(position), position);

                if(mPresenter.getChekedItemNum() > 0)
                {
                    setCheckedNoteListRecyclerCheckBox((NoteListAdapter.ViewHolder) noteListRecyclerView.findViewHolderForAdapterPosition(position), mPresenter.getIsChecked(position));
                }
                else
                {
                    menu.removeItem(R.id.action_remove);
                    menu.removeItem(R.id.action_cancle);
                    menu.removeItem(R.id.action_copy);
                    menu.removeItem(R.id.action_select_all);

                    setVisibleNoteListRecyclerAllCheckBox(View.GONE);
                    imageButtonCreateNote.setVisibility(View.VISIBLE);
                    setCheckedNoteListRecyclerCheckBox((NoteListAdapter.ViewHolder) noteListRecyclerView.findViewHolderForAdapterPosition(position), mPresenter.getIsChecked(position));
                }
            }
        }

        @Override
        public void longClickCallBack(int position) {
            mPresenter.setIsChecked(!mPresenter.getIsChecked(position), position);
            setCheckedNoteListRecyclerCheckBox((NoteListAdapter.ViewHolder) noteListRecyclerView.findViewHolderForAdapterPosition(position), mPresenter.getIsChecked(position));

            if(mPresenter.getChekedItemNum() > 0) {
                if(menu.findItem(R.id.action_remove) == null)
                {
                    menuInflater.inflate(R.menu.main_actionbar_action, menu);
                }
                setVisibleNoteListRecyclerAllCheckBox(View.VISIBLE);
                imageButtonCreateNote.setVisibility(View.GONE);
            }
            else {
                menu.removeItem(R.id.action_remove);
                menu.removeItem(R.id.action_cancle);
                menu.removeItem(R.id.action_copy);
                menu.removeItem(R.id.action_select_all);
                setVisibleNoteListRecyclerAllCheckBox(View.GONE);
                imageButtonCreateNote.setVisibility(View.VISIBLE);
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_remove:

                NoteDeleteDialog noteDeleteDialog = new NoteDeleteDialog(this);
                noteDeleteDialog.setDialogListener(new NoteDeleteDialogListener() {
                    @Override
                    public void onPositiveClicked() {

                        for(int i = 0; i < mAdapter.getItemCount(); i++)
                        {
                            if(mPresenter.getIsChecked(i))
                            {
                                setCheckedNoteListRecyclerCheckBox((NoteListAdapter.ViewHolder) noteListRecyclerView.findViewHolderForAdapterPosition(i), false);
                                mPresenter.setIsChecked(false, i);
                                mPresenter.removeNoteData(i--);
                                mAdapter.notifyDataSetChanged();
                            }
                        }

                        menu.removeItem(R.id.action_remove);
                        menu.removeItem(R.id.action_cancle);
                        menu.removeItem(R.id.action_copy);
                        menu.removeItem(R.id.action_select_all);
                        imageButtonCreateNote.setVisibility(View.VISIBLE);
                        setVisibleNoteListRecyclerAllCheckBox(View.GONE);
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });

                noteDeleteDialog.show();

                return true;
            case R.id.action_copy:
                return true;
            case R.id.action_cancle:
                menu.removeItem(R.id.action_remove);
                menu.removeItem(R.id.action_cancle);
                menu.removeItem(R.id.action_copy);
                menu.removeItem(R.id.action_select_all);
                imageButtonCreateNote.setVisibility(View.VISIBLE);
                setVisibleNoteListRecyclerAllCheckBox(View.GONE);
                setCheckedNoteListRecyclerAllCheckBox(false);
                return true;
            case R.id.action_select_all:
                setCheckedNoteListRecyclerAllCheckBox(true);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {

                        int position = 0;

                        Intent intent = result.getData();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Bundle bundle = intent.getExtras();
                        position = bundle.getInt("position");

                        if(position == CREATE_NOTE)
                            mPresenter.addNoteData(new NoteData(Utility.getCurrenTime(), bundle.getString("title"), bundle.getString("content")));
                        else
                        {
                            mPresenter.setNoteTitle(bundle.getString("title"), position);
                            mPresenter.setContent(bundle.getString("content"), position);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }
            }
    );


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.saveNoteDataListInDataBase(this);
    }

    @Override
    public void setVisibleNoteListRecyclerCheckBox(NoteListAdapter.ViewHolder viewHolder, int visible) {

        if(viewHolder != null)
        {
            CheckBox checkBox = viewHolder.itemView.findViewById(R.id.checkBoxSelect);
            checkBox.setVisibility(visible);
        }
    }

    @Override
    public void setCheckedNoteListRecyclerCheckBox(NoteListAdapter.ViewHolder viewHolder, boolean isChecked) {

        if(viewHolder != null)
        {
            CheckBox checkBox = viewHolder.itemView.findViewById(R.id.checkBoxSelect);
            checkBox.setChecked(isChecked);
            mPresenter.setIsChecked(isChecked, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public void setVisibleNoteListRecyclerAllCheckBox(int visible)
    {
        for(int i = 0; i < mPresenter.getItemCount(); i++)
        {
            setVisibleNoteListRecyclerCheckBox((NoteListAdapter.ViewHolder) noteListRecyclerView.findViewHolderForAdapterPosition(i), visible);
        }
    }

    @Override
    public void setCheckedNoteListRecyclerAllCheckBox(boolean isChecked) {
        for(int i = 0; i < mPresenter.getItemCount(); i++)
        {
            setCheckedNoteListRecyclerCheckBox((NoteListAdapter.ViewHolder) noteListRecyclerView.findViewHolderForAdapterPosition(i), isChecked);
            mPresenter.setIsChecked(isChecked, i);
        }
    }


}
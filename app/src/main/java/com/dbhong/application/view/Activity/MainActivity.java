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
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.dbhong.application.R;
import com.dbhong.application.Utility.Utility;
import com.dbhong.application.model.NoteData;
import com.dbhong.application.model.NoteDataList;
import com.dbhong.application.presenter.Contract;
import com.dbhong.application.presenter.MainActivityPresenter;
import com.dbhong.application.view.Adapter.NoteListAdapter;
import com.dbhong.application.view.Dialog.NoteDeleteDialog;
import com.dbhong.application.view.Dialog.NoteDeleteDialogListener;

import java.util.Map;


public class MainActivity extends AppCompatActivity implements Contract.MainActivtyView{

    private static final int CREATE_NOTE = -1;
    public static final String TAG = "NoteListMainActivity";

    private MainActivityPresenter mPresenter;
    NoteListAdapter mAdapter;

    RecyclerView noteListRecyclerView;
    Toolbar toolbar;

    ImageButton imageButtonCreateNote;

    Menu menu;
    MenuInflater menuInflater;

    Animation moveRightAnim;
    Animation moveLeftAnim;
    Animation checkboxClickAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate()");

        mPresenter = new MainActivityPresenter(this);

        noteListRecyclerView = findViewById(R.id.noteListRecycler);
        noteListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new NoteListAdapter(this, R.layout.activity_main , mPresenter);

        Log.e(TAG, "mAdapter size : " + mAdapter.getItemCount());

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
        Log.e(TAG, "item count before : " + mPresenter.getItemCount());
        if(mPresenter.loadNoteDataListInDataBase(this) > 0)
            mAdapter.notifyDataSetChanged();

        Log.e(TAG, "item count after : " + mPresenter.getItemCount());

        menu = toolbar.getMenu();
        menuInflater = getMenuInflater();

        moveRightAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.note_recycler_move_right);
        moveLeftAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.note_recycler_move_left);
        checkboxClickAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.note_checkbox_anim);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_remove:
                return toolbarActionRemove();
            case R.id.action_copy:
                return mPresenter.toolBarActionCopy();
            case R.id.action_cancle:
                return mPresenter.toolBarActionCancle();
            case R.id.action_select_all:
                return mPresenter.toolBarActionSelectAll();
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
    public void onBackPressed() {
        if(toolbar.getMenu().findItem(R.id.action_cancle) != null)
        {
            onOptionsItemSelected(toolbar.getMenu().findItem(R.id.action_cancle));
        }
        else
            finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.saveNoteDataListInDataBase(this);
    }

    @Override
    public void startAnim(NoteListAdapter.ViewHolder viewHolder, int resId, Animation animation) {

    }

    @Override
    public void setViewNoteListLongClickEvent(int position) {
        if(mPresenter.getChekedItemNum() > 0) {
            if(menu.findItem(R.id.action_remove) == null)
            {
                menuInflater.inflate(R.menu.main_actionbar_action, menu);
            }

            setVisibilityAllNoteListRecyclerItem(R.id.checkBoxSelect, View.VISIBLE);
            startAnim((NoteListAdapter.ViewHolder) noteListRecyclerView.findViewHolderForAdapterPosition(position), R.id.checkBoxSelect, checkboxClickAnim);
            ((CheckBox) noteListRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.checkBoxSelect)).setChecked(mPresenter.getIsChecked(position));

            imageButtonCreateNote.setVisibility(View.GONE);
        }
        else {
            removeAllMenuItem();
            setVisibilityAllNoteListRecyclerItem(R.id.checkBoxSelect, View.GONE);
            imageButtonCreateNote.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setViewNoteListClickEvent(int position, int checkedItemNumBeforeCheck) {

        if(mPresenter.getChekedItemNum() == 0)
        {
            removeAllMenuItem();
            setVisibilityAllNoteListRecyclerItem(R.id.checkBoxSelect, View.GONE);
            imageButtonCreateNote.setVisibility(View.VISIBLE);
            ((CheckBox)noteListRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.checkBoxSelect)).setChecked(mPresenter.getIsChecked(position));
        }
        else if(mPresenter.getChekedItemNum() == 1)
        {
            switch (checkedItemNumBeforeCheck)
            {
                case 0: // 0 -> 1
                    Intent intent = new Intent(getApplicationContext(), EditNoteActivity.class);
                    intent.putExtra("title", mPresenter.getNoteTitle(position));
                    intent.putExtra("content", mPresenter.getContent(position));
                    intent.putExtra("position", position);

                    startActivityResult.launch(intent);
                    break;
                case 2: // 2 -> 1
                    ((CheckBox)noteListRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.checkBoxSelect)).setChecked(mPresenter.getIsChecked(position));
                    break;
            }
        }
        else
        {
            ((CheckBox)noteListRecyclerView.findViewHolderForAdapterPosition(position).itemView.findViewById(R.id.checkBoxSelect)).setChecked(mPresenter.getIsChecked(position));
            //((NoteListAdapter.ViewHolder) noteListRecyclerView.findViewHolderForAdapterPosition(position)).itemView.findViewById(R.id.checkBoxSelect).startAnimation(checkboxClickAnim);
        }
    }

    public void setVisibilityAllNoteListRecyclerItem(int resId, int visibility)
    {
        for(int i = 0; i < mAdapter.getItemCount(); i++)
        {
            noteListRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(resId).setVisibility(visibility);
        }
    }

    public void setCheckAllNoteListRecyclerCheckBox(int resId, boolean type)
    {
        for(int i = 0; i < mPresenter.getItemCount(); i++)
        {
            ((CheckBox)noteListRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(resId)).setChecked(type);
        }
    }

    public void removeAllMenuItem()
    {
        menu.removeItem(R.id.action_remove);
        menu.removeItem(R.id.action_cancle);
        menu.removeItem(R.id.action_copy);
        menu.removeItem(R.id.action_select_all);
    }

    @Override
    public void noteDeleteDialogPositiveClicked() {
        mAdapter.notifyDataSetChanged();

        noteListRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < mAdapter.getItemCount(); i++)
                {
                    ((CheckBox) noteListRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.checkBoxSelect)).setChecked(mPresenter.getIsChecked(i));
                    setVisibilityAllNoteListRecyclerItem(R.id.checkBoxSelect, View.GONE);
                }
            }
        }, 50);

//        for(int i = 0; i < mAdapter.getItemCount(); i++)
//        {
//            Log.d(TAG, "mAdapter i : " + i );
//            noteListRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.checkBoxSelect);
//            ((CheckBox) noteListRecyclerView.findViewHolderForAdapterPosition(i).itemView.findViewById(R.id.checkBoxSelect)).setChecked(mPresenter.getIsChecked(i));
//        }

        removeAllMenuItem();
        imageButtonCreateNote.setVisibility(View.VISIBLE);
        //setVisibilityAllNoteListRecyclerItem(R.id.checkBoxSelect, View.GONE);
    }

    @Override
    public void noteDeleteDialogNegativeClicked() {
        Toast.makeText(getApplicationContext(), "Push cancle", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean toolbarActionRemove() {
        NoteDeleteDialog noteDeleteDialog = new NoteDeleteDialog(this);
        noteDeleteDialog.show();
        return true;
    }

    @Override
    public boolean toolBarActionCancle() {
        removeAllMenuItem();
        imageButtonCreateNote.setVisibility(View.VISIBLE);
        setVisibilityAllNoteListRecyclerItem(R.id.checkBoxSelect, View.GONE);
        setCheckAllNoteListRecyclerCheckBox(R.id.checkBoxSelect, false);


        return true;
    }

    @Override
    public boolean toolBarActionCopy() {
        return true;
    }

    @Override
    public boolean toolBarActionSelectAll(boolean isChecked) {

        setCheckAllNoteListRecyclerCheckBox(R.id.checkBoxSelect, isChecked);
        return true;
    }

    public MainActivityPresenter getPresenter() {
        return mPresenter;
    }
}
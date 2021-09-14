package com.dbhong.application.view.Activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dbhong.application.view.Fragment.EditNoteFragment;
import com.dbhong.application.R;

public class EditNoteActivity extends AppCompatActivity {

    public static final String TAG = "EditNoteActivity";

    EditNoteFragment editNoteFragment;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        fragmentManager = getSupportFragmentManager();
        editNoteFragment = new EditNoteFragment();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.editNoteFrameLayout, editNoteFragment).commitAllowingStateLoss();

        toolbar = findViewById(R.id.editNoteToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.edit_note_toolbar_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
    }

    public void initialize()
    {
        String noteTitle = "";
        String noteContent = "";
        int position = -1;

        Intent intent = getIntent();

        noteTitle = intent.getExtras().getString("title");
        noteContent = intent.getExtras().getString("content");
        position = intent.getExtras().getInt("position");

        Bundle bundle = new Bundle();
        bundle.putString("title", noteTitle);
        bundle.putString("content", noteContent);
        bundle.putInt("position", position);

        editNoteFragment.setArguments(bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_note_actionbar_action, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = new Intent(this, MainActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("title", editNoteFragment.getNoteTitle());
                bundle.putString("content", editNoteFragment.getNoteContent());
                bundle.putInt("position", editNoteFragment.getPosition());

                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);

                finish();

                return true;
            case R.id.action_sharing:

                intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");

                String text = "공유하기";
                intent.putExtra(Intent.EXTRA_TEXT, "노트 내용 : " + editNoteFragment.getNoteContent());

                Intent chooser = Intent.createChooser(intent, "친구에게 공유하기");
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {

                    }
                }
            }
    );
}
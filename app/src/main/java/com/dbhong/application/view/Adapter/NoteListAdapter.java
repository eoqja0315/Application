package com.dbhong.application.view.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbhong.application.Utility.Utility;
import com.dbhong.application.model.NoteData;
import com.dbhong.application.presenter.NoteDataListPresenter;
import com.dbhong.application.view.Activity.EditNoteActivity;
import com.dbhong.application.view.Activity.MainActivity;
import com.dbhong.application.view.Dialog.NoteDeleteDialog;
import com.dbhong.application.view.Dialog.NoteDeleteDialogListener;
import com.dbhong.application.R;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    public static final String TAG = "NoteListAdapter";

    private NoteDataListPresenter mPresenter;

    private Context mContext;
    private LayoutInflater mInflater;
    private boolean mObjectsFromResources;
    private int mFieldId = 0;
    private final int mResource;
    private int mDropDownResource;

    private ClickAdapterCallback clickAdapterCallback;

    public NoteListAdapter( Context context, @LayoutRes int resource, NoteDataListPresenter objects) {
        this(context, resource, 0, objects);
    }

    public NoteListAdapter( Context context, @LayoutRes int resource,
                        @IdRes int textViewResourceId, NoteDataListPresenter objects) {
        this(context, resource, textViewResourceId, objects, false);
    }

    private NoteListAdapter(Context context, @LayoutRes int resource,
                         @IdRes int textViewResourceId, NoteDataListPresenter presenter, boolean objsFromResources) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mResource = mDropDownResource = resource;
        mPresenter = presenter;
        mObjectsFromResources = objsFromResources;
        mFieldId = textViewResourceId;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNoteTitle;
        TextView textViewNoteCreateDate;
        TextView textViewNoteContent;
        LinearLayout layoutRecyclerBackground;
        CheckBox checkboxSelect;
        ViewHolder(View itemView) {
            super(itemView);

            textViewNoteTitle = itemView.findViewById(R.id.textViewNoteTitle);
            textViewNoteCreateDate = itemView.findViewById(R.id.textViewNoteCreateDate);
            textViewNoteContent = itemView.findViewById(R.id.textViewNoteContent);
            layoutRecyclerBackground = itemView.findViewById(R.id.layoutRecyclerBackground);
            checkboxSelect = itemView.findViewById(R.id.checkBoxSelect);

        }
    }

    @Override
    public NoteListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHodler begin");

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.note_recycler, parent, false);
        NoteListAdapter.ViewHolder viewHolder = new NoteListAdapter.ViewHolder(view);

        Log.d(TAG, "onCreateViewHodler end");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteListAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindView begin");

        holder.textViewNoteTitle.setText(mPresenter.getNoteData(position).getNoteTitle());
        holder.textViewNoteCreateDate.setText(mPresenter.getNoteData(position).getCreateDate());
        holder.textViewNoteContent.setText(mPresenter.getNoteData(position).getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), EditNoteActivity.class);
                intent.putExtra("title", mPresenter.getNoteData(holder.getAdapterPosition()).getNoteTitle());
                intent.putExtra("content", mPresenter.getNoteData(holder.getAdapterPosition()).getContent());
                intent.putExtra("position", holder.getAdapterPosition());

                ((MainActivity) mContext).startActivityResult.launch(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Drawable drawable = holder.itemView.getResources().getDrawable(R.color.colorPrimary);
                holder.layoutRecyclerBackground.setBackground(drawable);

                holder.checkboxSelect.setVisibility(View.VISIBLE);
                holder.checkboxSelect.setChecked(true);

                return true;
            }
        });

        Log.d(TAG, "onBindView end");
    }

    @Override
    public int getItemCount() {
        return mPresenter.getNoteDataList().size();
    }

    public void setClickAdapterCallback(ClickAdapterCallback clickAdapterCallback)
    {
        this.clickAdapterCallback = clickAdapterCallback;
    }
}

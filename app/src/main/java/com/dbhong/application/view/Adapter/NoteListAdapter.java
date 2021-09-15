package com.dbhong.application.view.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.dbhong.application.presenter.NoteDataListPresenter;
import com.dbhong.application.view.Activity.EditNoteActivity;
import com.dbhong.application.view.Activity.MainActivity;

import com.dbhong.application.R;

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
        LinearLayout layoutRecyclerItemDisplay;
        CheckBox checkboxSelect;
        Animation moveRightAnim;
        ViewHolder(View itemView) {
            super(itemView);

            textViewNoteTitle = itemView.findViewById(R.id.textViewNoteTitle);
            textViewNoteCreateDate = itemView.findViewById(R.id.textViewNoteCreateDate);
            textViewNoteContent = itemView.findViewById(R.id.textViewNoteContent);
            layoutRecyclerBackground = itemView.findViewById(R.id.layoutRecyclerBackground);
            checkboxSelect = itemView.findViewById(R.id.checkBoxSelect);
            layoutRecyclerItemDisplay = itemView.findViewById(R.id.layoutRecyclerItemDisplay);
            moveRightAnim = AnimationUtils.loadAnimation(mContext, R.anim.note_recycler_move_right);
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

        holder.textViewNoteTitle.setText(mPresenter.getNoteTitle(position));
        holder.textViewNoteCreateDate.setText(mPresenter.getCreateDate(position));
        holder.textViewNoteContent.setText(mPresenter.getContent(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), EditNoteActivity.class);
                intent.putExtra("title", mPresenter.getNoteData(position).getNoteTitle());
                intent.putExtra("content", mPresenter.getNoteData(position).getContent());
                intent.putExtra("position", position);

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
                holder.layoutRecyclerItemDisplay.setAnimation(holder.moveRightAnim);

                Toolbar mainToolbar = ((MainActivity) mContext).findViewById(R.id.mainToolbar);
                MenuInflater menuInflater = ((MainActivity) mContext).getMenuInflater();
                menuInflater.inflate(R.menu.main_actionbar_action, mainToolbar.getMenu());

                clickAdapterCallback.longClickCallBack(position);
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

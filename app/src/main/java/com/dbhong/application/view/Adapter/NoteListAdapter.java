package com.dbhong.application.view.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

import androidx.recyclerview.widget.RecyclerView;

import com.dbhong.application.model.NoteData;
import com.dbhong.application.model.NoteDataList;
import com.dbhong.application.presenter.MainActivityPresenter;

import com.dbhong.application.R;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    public static final String TAG = "NoteListAdapter";

    private MainActivityPresenter mPresenter;

    private Context mContext;
    private LayoutInflater mInflater;
    private boolean mObjectsFromResources;
    private int mFieldId = 0;
    private final int mResource;
    private int mDropDownResource;

    public NoteListAdapter( Context context, @LayoutRes int resource, MainActivityPresenter objects) {
        this(context, resource, 0, objects);
    }

    public NoteListAdapter( Context context, @LayoutRes int resource,
                        @IdRes int textViewResourceId, MainActivityPresenter objects) {
        this(context, resource, textViewResourceId, objects, false);
    }

    private NoteListAdapter(Context context, @LayoutRes int resource,
                            @IdRes int textViewResourceId, MainActivityPresenter presenter, boolean objsFromResources) {
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

        ViewHolder(View itemView) {
            super(itemView);

            textViewNoteTitle = itemView.findViewById(R.id.textViewNoteTitle);
            textViewNoteCreateDate = itemView.findViewById(R.id.textViewNoteCreateDate);
            textViewNoteContent = itemView.findViewById(R.id.textViewNoteContent);
            layoutRecyclerBackground = itemView.findViewById(R.id.layoutRecyclerBackground);
            checkboxSelect = itemView.findViewById(R.id.checkBoxSelect);
            layoutRecyclerItemDisplay = itemView.findViewById(R.id.layoutRecyclerItemDisplay);

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
                mPresenter.setViewNoteListClickEvent(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mPresenter.setViewNoteListLongClickEvent(position);
                return true;
            }
        });

        Log.d(TAG, "onBindView end");
    }

    @Override
    public int getItemCount() {
        if(NoteDataList.getInstance() == null)
            return 0;
        else {
            return NoteDataList.getInstance().size();
        }
    }

}

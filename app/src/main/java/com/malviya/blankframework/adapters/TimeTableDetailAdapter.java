package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.models.AttendanceDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class TimeTableDetailAdapter extends RecyclerView.Adapter<TimeTableDetailAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<AttendanceDataModel> mList;
    private View.OnClickListener mClickListener;

    public TimeTableDetailAdapter(Context context, ArrayList<AttendanceDataModel> pList, View.OnClickListener pClickListener) {
        mContext = context;
        mList = pList;
        mClickListener = pClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //View view = inflater.inflate(R.layout.attendance_row, null);
        //if use null then recyclerview not take match_parent as width
        View viewHolder = inflater.inflate(R.layout.resultlist_row, parent, false);
        //viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageviewSelection.setTag(position);
        holder.imageviewSelection.setOnClickListener(mClickListener);
    }


    @Override
    public int getItemCount() {
        return 10;
        // return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSem;
        public ImageView imageviewSelection;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewSem = (TextView) itemView.findViewById(R.id.textview_resultlist_row_sem_value);
            imageviewSelection = (ImageView) itemView.findViewById(R.id.imageview_resultlist_row_selection);
        }
    }
}

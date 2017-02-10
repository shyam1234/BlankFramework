package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.models.AttendanceDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<AttendanceDataModel> mList;

    public ResultAdapter(Context context, ArrayList<AttendanceDataModel> pList) {
        mContext = context;
        mList = pList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //View view = inflater.inflate(R.layout.attendance_row, null);
        //if use null then recyclerview not take match_parent as width
        View viewHolder = inflater.inflate(R.layout.result_row, parent,false);
        //viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //holder.textViewSubject.setText("" + mList.get(position).getSubjectValue() + "%");
       // holder.textViewGrade.setText("" + mList.get(position).getTotalDays());
       // holder.textViewResult.setText("" + mList.get(position).getAbsentDays());
    }


    @Override
    public int getItemCount()
    {
        return 10;
       // return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewSubject;
        public TextView textViewGrade;
        public TextView textViewResult;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewSubject = (TextView) itemView.findViewById(R.id.textview_result_row_subject_value);
            textViewGrade = (TextView) itemView.findViewById(R.id.textview_result_row_grade_value);
            textViewResult = (TextView) itemView.findViewById(R.id.textview_result_row_result_value);
        }
    }
}

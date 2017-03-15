package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.models.TableResultMasterDataModel;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TableResultMasterDataModel> mList;
    private View.OnClickListener mListner;

    public ResultAdapter(Context context, ArrayList<TableResultMasterDataModel> pList, View.OnClickListener pListner) {
        mContext = context;
        mList = pList;
        mListner =pListner;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //View view = inflater.inflate(R.layout.attendance_row, null);
        //if use null then recyclerview not take match_parent as width
        View viewHolder = inflater.inflate(R.layout.result_row, parent, false);
        //viewHolder.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextViewSubjectSemster.setText(mList.get(position).getSemesterName());
        holder.mTextViewTotalScore.setText("0");
        holder.mTextViewAchievementIndex.setText(mList.get(position).getAchievementIndex());
        holder.textViewSubject.setText(mList.get(position).getCourseName());
        holder.textViewGrade.setText("not coming");
        holder.textViewResult.setText(mList.get(position).getResult());
        holder.btnDownload.setOnClickListener(mListner);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTextViewSubjectSemster;
        private final TextView mTextViewTotalScore;
        private final TextView mTextViewAchievementIndex;
        public final TextView textViewSubject;
        public final TextView textViewGrade;
        public final TextView textViewResult;
        private final Button btnDownload;

        public MyViewHolder(View itemView) {
            super(itemView);
            textViewSubject = (TextView) itemView.findViewById(R.id.textview_result_row_subject_value);
            textViewGrade = (TextView) itemView.findViewById(R.id.textview_result_row_grade_value);
            textViewResult = (TextView) itemView.findViewById(R.id.textview_result_row_result_value);
            //------------------------------------
            mTextViewSubjectSemster = (TextView) itemView.findViewById(R.id.textview_results_sem_value);
            mTextViewTotalScore = (TextView) itemView.findViewById(R.id.textview_results_total_score_value);
            mTextViewAchievementIndex = (TextView) itemView.findViewById(R.id.textview_results_achivement_index_value);
            //-------------------------------------
            btnDownload = (Button) itemView.findViewById(R.id.imagebtn_results_download);

        }
    }
}

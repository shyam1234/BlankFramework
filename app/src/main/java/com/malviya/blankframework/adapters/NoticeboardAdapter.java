package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.constant.Constant;
import com.malviya.blankframework.database.TableNewsMaster;
import com.malviya.blankframework.database.TableStudentOverallFeeSummary;
import com.malviya.blankframework.database.TableStudentOverallResultSummary;
import com.malviya.blankframework.models.TableFeeMasterDataModel;
import com.malviya.blankframework.models.TableNewsMasterDataModel;
import com.malviya.blankframework.models.TableNoticeBoardDataModel;
import com.malviya.blankframework.models.TableResultMasterDataModel;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.GetPicassoImage;
import com.malviya.blankframework.utils.Utils;

import java.util.ArrayList;

import static com.malviya.blankframework.R.id.imageview_bottom_comment;
import static com.malviya.blankframework.R.id.imageview_bottom_tag;

/**
 * Created by Admin on 05-02-2017.
 */

public class NoticeboardAdapter extends RecyclerView.Adapter<NoticeboardAdapter.MyViewHolder> {
    private static final String TAG = "NoticeboardAdapter";
    private Context mContext;
    private ArrayList<TableNoticeBoardDataModel> mList;
    private View.OnClickListener mListener;

    public NoticeboardAdapter(Context context, ArrayList<TableNoticeBoardDataModel> pList, View.OnClickListener pListener) {
        mContext = context;
        mList = pList;
        mListener = pListener;
        AppLog.log(TAG, " +++ NoticeboardAdapter +pList.size()+++ " + pList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.noticeboard_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AppLog.log(TAG, " +++ onBindViewHolder getMenuCode ++++ "+mList.get(position).getMenuCode());
        AppLog.log(TAG, " +++ onBindViewHolder getRederenceId ++++ "+mList.get(position).getRederenceId());
        holder.imageViewRhumbnil.setVisibility(View.GONE);
        holder.textViewLike.setVisibility(View.GONE);
        holder.textViewComment.setVisibility(View.GONE);
        holder.imageViewTag.setVisibility(View.GONE);
        holder.imageViewComment.setVisibility(View.GONE);
        holder.imageViewLike.setVisibility(View.GONE);
        holder.line_bottom_holder.setVisibility(View.GONE);
        holder.textViewPublishBy.setVisibility(View.GONE);
        holder.textViewRefTitle.setVisibility(View.GONE);
        holder.textViewPublishedTime.setVisibility(View.GONE);
        holder.lin_news_row_holder.setOnClickListener(mListener);
        holder.lin_news_row_holder.setTag(position);
        switch (mList.get(position).getMenuCode()) {
            case Constant.TAG_FEE:
                holder.textViewPublishBy.setVisibility(View.VISIBLE);
                holder.textViewRefTitle.setVisibility(View.VISIBLE);
                holder.textViewPublishedTime.setVisibility(View.VISIBLE);
                TableStudentOverallFeeSummary feeTable = new TableStudentOverallFeeSummary();
                feeTable.openDB(mContext);
                TableFeeMasterDataModel feeHolder = feeTable.getData(mList.get(position).getMenuCode(), mList.get(position).getRederenceId());
                //GetPicassoImage.getImage(mContext, feeHolder.getThumbNailPath(), holder.imageViewRhumbnil);
                holder.textViewPublishBy.setText(feeHolder.getPublishedBy());
                holder.textViewRefTitle.setText(feeHolder.getFeeTitle());
                holder.textViewPublishedTime.setText(feeHolder.getPublishedOn());
                String body = "Total Due: " + feeHolder.getTotalDue() + " submit till date: " + feeHolder.getDueDate()
                        + " for Sememster " + feeHolder.getSemsterName();
                holder.webviewShortBody.loadData(body, "text/html; charset=utf-8", "utf-8");
                holder.textViewTag.setText(feeHolder.getMenuCode());

                feeTable.closeDB();
                break;
            case Constant.TAG_ATTENDANCE:
                break;
            case Constant.TAG_HOMEWORK:
                break;
            case Constant.TAG_DIARY:
                break;
            case Constant.TAG_MESSAGE:
                break;
            case Constant.TAG_EVENTS:
                break;
            case Constant.TAG_GALLERY:
                break;
            case Constant.TAG_FEEDBACK:
                break;
            case Constant.TAG_NEWS:
                holder.textViewPublishBy.setVisibility(View.VISIBLE);
                holder.textViewRefTitle.setVisibility(View.VISIBLE);
                holder.textViewPublishedTime.setVisibility(View.VISIBLE);
                TableNewsMaster newsTable = new TableNewsMaster();
                newsTable.openDB(mContext);
                TableNewsMasterDataModel newsHolder = newsTable.getData(mList.get(position).getMenuCode(), mList.get(position).getRederenceId());
                GetPicassoImage.getImage(mContext, newsHolder.getThumbNailPath(), holder.imageViewRhumbnil);
                holder.imageViewRhumbnil.setVisibility(View.VISIBLE);
                holder.textViewLike.setVisibility(View.VISIBLE);
                holder.textViewComment.setVisibility(View.VISIBLE);
                holder.imageViewTag.setVisibility(View.VISIBLE);
                holder.imageViewComment.setVisibility(View.VISIBLE);
                holder.imageViewLike.setVisibility(View.VISIBLE);
                holder.line_bottom_holder.setVisibility(View.VISIBLE);

                holder.textViewPublishBy.setText(newsHolder.getPublishedBy());
                holder.textViewRefTitle.setText(newsHolder.getReferenceTitle());
                //holder.textViewPublishedTime.setText(newsHolder.getPublishedOn());
                holder.textViewPublishedTime.setText(Utils.getTimeInDDMMYYYY(newsHolder.getPublishedOn()));
                holder.webviewShortBody.loadData(newsHolder.getShortBody(), "text/html; charset=utf-8", "utf-8");
                holder.textViewTag.setText(newsHolder.getMenuCode());
                holder.textViewLike.setText(newsHolder.getTotalLikes());
                holder.textViewComment.setText(newsHolder.getTotalComments());
                newsTable.closeDB();
                break;
            case Constant.TAG_TIMETABLE:
                holder.textViewPublishBy.setVisibility(View.GONE);
                holder.textViewRefTitle.setVisibility(View.VISIBLE);
                holder.textViewPublishedTime.setVisibility(View.VISIBLE);
                holder.textViewPublishedTime.setText(Utils.getTimeInDDMMYYYY(mList.get(position).getPublishedOn()));
                holder.textViewRefTitle.setText("Time table has been changes for reference data "+mList.get(position).getReferenceDate());
                break;
            case Constant.TAG_RESULT:
                holder.textViewPublishBy.setVisibility(View.VISIBLE);
                holder.textViewRefTitle.setVisibility(View.VISIBLE);
                holder.textViewPublishedTime.setVisibility(View.VISIBLE);
                TableStudentOverallResultSummary resultTable = new TableStudentOverallResultSummary();
                resultTable.openDB(mContext);
                TableResultMasterDataModel resultHolder = resultTable.getData(mList.get(position).getMenuCode(), mList.get(position).getRederenceId());
                //GetPicassoImage.getImage(mContext, resultHolder.getThumbNailPath(), holder.imageViewRhumbnil);
                holder.textViewPublishBy.setText(resultHolder.getPublishedBy());
                holder.textViewRefTitle.setText(resultHolder.getCourseName());
                //holder.textViewPublishedTime.setText(resultHolder.getPublishedOn());
                holder.textViewPublishedTime.setText(Utils.getTimeInDDMMYYYY(resultHolder.getPublishedOn()));
                holder.webviewShortBody.loadData(resultHolder.getResult(), "text/html; charset=utf-8", "utf-8");
                holder.textViewTag.setText(resultHolder.getMenuCode());
                // holder.textViewLike.setText(resultHolder.getTotalLikes());
                //holder.textViewComment.setText(resultHolder.getTotalComments());
                resultTable.closeDB();
                break;
        }
        //--------------------------------------------------------------
        holder.imageViewRhumbnil.setOnClickListener(mListener);
        holder.imageViewRhumbnil.setTag(position);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRefTitle;
        public TextView textViewPublishBy;
        public TextView textViewPublishedTime;
        public ImageView imageViewRhumbnil;
        public WebView webviewShortBody;
        public ImageView imageViewTag;
        public ImageView imageViewLike;
        public ImageView imageViewComment;
        public TextView textViewTag;
        public TextView textViewLike;
        public TextView textViewComment;
        public LinearLayout line_bottom_holder;
        public LinearLayout lin_news_row_holder;


        public MyViewHolder(View itemView) {
            super(itemView);
            lin_news_row_holder = (LinearLayout) itemView.findViewById(R.id.lin_news_row_holder);
            imageViewRhumbnil = (ImageView) itemView.findViewById(R.id.imageview_news_row_thumbnil);
            textViewRefTitle = (TextView) itemView.findViewById(R.id.textview_news_row_reference_title);
            textViewPublishBy = (TextView) itemView.findViewById(R.id.textview_news_row_published_by);
            textViewPublishedTime = (TextView) itemView.findViewById(R.id.textview_news_row_published_time);
            webviewShortBody = (WebView) itemView.findViewById(R.id.webview_news_row_shortbody);
            line_bottom_holder = (LinearLayout) itemView.findViewById(R.id.lin_bottom_tag);
            imageViewTag = (ImageView) itemView.findViewById(imageview_bottom_tag);
            imageViewLike = (ImageView) itemView.findViewById(R.id.imageview_bottom_like);
            imageViewComment = (ImageView) itemView.findViewById(imageview_bottom_comment);
            textViewTag = (TextView) itemView.findViewById(R.id.textview_inc_bottom_tag);
            textViewLike = (TextView) itemView.findViewById(R.id.textview_inc_bottom_like);
            textViewComment = (TextView) itemView.findViewById(R.id.textview_inc_bottom_comment);

        }
    }
}

package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.constant.Constant;
import com.malviya.blankframework.database.TableFeeMasterMaster;
import com.malviya.blankframework.database.TableNewsMaster;
import com.malviya.blankframework.database.TableResultMaster;
import com.malviya.blankframework.models.TableFeeMasterDataModel;
import com.malviya.blankframework.models.TableNewsMasterDataModel;
import com.malviya.blankframework.models.TableNoticeBoardDataModel;
import com.malviya.blankframework.models.TableResultMasterDataModel;
import com.malviya.blankframework.utils.AppLog;
import com.malviya.blankframework.utils.GetPicassoImage;

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
        AppLog.log(TAG, " +++ onBindViewHolder ++++ ");
        switch (mList.get(position).getMenuCode()) {
            case Constant.TAG_FEE:
                TableFeeMasterMaster feeTable = new TableFeeMasterMaster();
                TableFeeMasterDataModel feeHolder = feeTable.getInfo(mList.get(position).getMenuCode(), mList.get(position).getRederenceId());
                //GetPicassoImage.getImage(mContext, feeHolder.getThumbNailPath(), holder.imageViewRhumbnil);
                holder.imageViewRhumbnil.setVisibility(View.GONE);
                holder.textViewPublishBy.setText(feeHolder.getPublishedBy());
                holder.textViewRefTitle.setText(feeHolder.getFeeTitle());
                holder.textViewPublishedTime.setText(feeHolder.getPublishedOn());
                String body = "Total Due: " + feeHolder.getTotalDue() + " submit till date: " + feeHolder.getDueDate()
                        + " for Sememster " + feeHolder.getSemsterName();
                holder.webviewShortBody.loadData(body, "text/html; charset=utf-8", "utf-8");
                holder.textViewTag.setText(feeHolder.getMenuCode());
                holder.textViewLike.setVisibility(View.GONE);
                holder.textViewComment.setVisibility(View.GONE);
                holder.imageViewTag.setVisibility(View.GONE);
                holder.imageViewComment.setVisibility(View.GONE);
                holder.imageViewLike.setVisibility(View.GONE);
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
                TableNewsMaster newsTable = new TableNewsMaster();
                TableNewsMasterDataModel newsHolder = newsTable.getInfo(mList.get(position).getMenuCode(), mList.get(position).getRederenceId());
                GetPicassoImage.getImage(mContext, newsHolder.getThumbNailPath(), holder.imageViewRhumbnil);
                holder.imageViewRhumbnil.setVisibility(View.VISIBLE);
                holder.textViewPublishBy.setText(newsHolder.getPublishedBy());
                holder.textViewRefTitle.setText(newsHolder.getReferenceTitle());
                holder.textViewPublishedTime.setText(newsHolder.getPublishedOn());
                holder.webviewShortBody.loadData(newsHolder.getShortBody(), "text/html; charset=utf-8", "utf-8");
                holder.textViewTag.setText(newsHolder.getMenuCode());
                holder.textViewLike.setText(newsHolder.getTotalLikes());
                holder.textViewComment.setText(newsHolder.getTotalComments());
                newsTable.closeDB();
                break;
            case Constant.TAG_TIMETABLE:
                break;
            case Constant.TAG_RESULT:
                TableResultMaster resultTable = new TableResultMaster();
                TableResultMasterDataModel resultHolder = resultTable.getInfo(mList.get(position).getMenuCode(), mList.get(position).getRederenceId());
                //GetPicassoImage.getImage(mContext, resultHolder.getThumbNailPath(), holder.imageViewRhumbnil);
                holder.imageViewRhumbnil.setVisibility(View.GONE);
                holder.textViewPublishBy.setText(resultHolder.getPublishedBy());
                holder.textViewRefTitle.setText(resultHolder.getCourseName());
                holder.textViewPublishedTime.setText(resultHolder.getPublishedOn());
                holder.webviewShortBody.loadData(resultHolder.getResult(), "text/html; charset=utf-8", "utf-8");
                holder.textViewTag.setText(resultHolder.getMenuCode());
                // holder.textViewLike.setText(resultHolder.getTotalLikes());
                //holder.textViewComment.setText(resultHolder.getTotalComments());
                holder.textViewLike.setVisibility(View.GONE);
                holder.textViewComment.setVisibility(View.GONE);
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


        public MyViewHolder(View itemView) {
            super(itemView);
            imageViewRhumbnil = (ImageView) itemView.findViewById(R.id.imageview_news_row_thumbnil);
            textViewRefTitle = (TextView) itemView.findViewById(R.id.textview_news_row_reference_title);
            textViewPublishBy = (TextView) itemView.findViewById(R.id.textview_news_row_published_by);
            textViewPublishedTime = (TextView) itemView.findViewById(R.id.textview_news_row_published_time);
            webviewShortBody = (WebView) itemView.findViewById(R.id.webview_news_row_shortbody);

            imageViewTag = (ImageView) itemView.findViewById(imageview_bottom_tag);
            imageViewLike = (ImageView) itemView.findViewById(R.id.imageview_bottom_like);
            imageViewComment = (ImageView) itemView.findViewById(imageview_bottom_comment);
            textViewTag = (TextView) itemView.findViewById(R.id.textview_inc_bottom_tag);
            textViewLike = (TextView) itemView.findViewById(R.id.textview_inc_bottom_like);
            textViewComment = (TextView) itemView.findViewById(R.id.textview_inc_bottom_comment);

        }
    }
}

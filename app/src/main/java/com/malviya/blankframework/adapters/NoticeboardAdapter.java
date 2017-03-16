package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.constant.WSContant;
import com.malviya.blankframework.database.TableNewsMaster;
import com.malviya.blankframework.database.TableStudentOverallFeeSummary;
import com.malviya.blankframework.database.TableStudentOverallResultSummary;
import com.malviya.blankframework.models.TableFeeMasterDataModel;
import com.malviya.blankframework.models.TableNewsMasterDataModel;
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
    private ArrayList<Object> mList;
    private View.OnClickListener mListener;

    public NoticeboardAdapter(Context context, ArrayList<Object> pList, View.OnClickListener pListener) {
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
        if (mList.get(position) instanceof TableNewsMasterDataModel) {
            holder.noticeboard_row_news_holder.setOnClickListener(mListener);
            holder.noticeboard_row_news_holder.setTag(position);

            holder.noticeboard_row_news_holder.setVisibility(View.VISIBLE);
            holder.textViewPublishBy.setVisibility(View.VISIBLE);
            holder.textViewRefTitle.setVisibility(View.VISIBLE);
            holder.textViewPublishedTime.setVisibility(View.VISIBLE);
            TableNewsMaster newsTable = new TableNewsMaster();
            newsTable.openDB(mContext);
            TableNewsMasterDataModel newsHolder = newsTable.getData(((TableNewsMasterDataModel) mList.get(position)).getMenuCode(), "" + ((TableNewsMasterDataModel) mList.get(position)).getReferenceId());
            GetPicassoImage.getImage(mContext, newsHolder.getThumbNailPath(), holder.imageViewRhumbnil);
            holder.imageViewRhumbnil.setVisibility(View.VISIBLE);
            holder.textViewLike.setVisibility(View.VISIBLE);
            holder.textViewComment.setVisibility(View.VISIBLE);
            holder.imageViewTag.setVisibility(View.VISIBLE);
            holder.imageViewComment.setVisibility(View.VISIBLE);
            holder.imageViewLike.setVisibility(View.VISIBLE);
            holder.textViewPublishBy.setText(newsHolder.getPublishedBy());
            holder.textViewRefTitle.setText(newsHolder.getReferenceTitle());
            //holder.textViewPublishedTime.setText(newsHolder.getPublishedOn());
            holder.textViewPublishedTime.setText(Utils.getTimeInDDMMYYYY(newsHolder.getPublishedOn()));
            holder.webviewShortBody.loadData(newsHolder.getShortBody(), "text/html; charset=utf-8", "utf-8");
            holder.textViewTag.setText(newsHolder.getMenuCode());
            holder.textViewLike.setText(newsHolder.getTotalLikes());
            holder.textViewComment.setText(newsHolder.getTotalComments());
            newsTable.closeDB();
        } else if (mList.get(position) instanceof TableResultMasterDataModel) {


            holder.textViewPublishBy.setVisibility(View.VISIBLE);
            holder.textViewRefTitle.setVisibility(View.VISIBLE);
            holder.textViewPublishedTime.setVisibility(View.VISIBLE);

            TableStudentOverallResultSummary resultTable = new TableStudentOverallResultSummary();
            resultTable.openDB(mContext);
            TableResultMasterDataModel resultHolder = resultTable.getData(((TableResultMasterDataModel) mList.get(position)).getMenuCode(), "" + ((TableResultMasterDataModel) mList.get(position)).getReferenceId());
            //GetPicassoImage.getImage(mContext, resultHolder.getThumbNailPath(), holder.imageViewRhumbnil);
            holder.textViewPublishBy.setText(resultHolder.getPublishedBy());
            holder.textViewRefTitle.setText(resultHolder.getCourseName());
            //holder.textViewPublishedTime.setText(resultHolder.getPublishedOn());
            holder.textViewPublishedTime.setText(Utils.getTimeInDDMMYYYY(resultHolder.getPublishedOn()));
            holder.webviewShortBody.loadData(resultHolder.getResult(), "text/html; charset=utf-8", "utf-8");
            holder.textViewTag.setText(resultHolder.getMenuCode());
            resultTable.closeDB();
        } else if (mList.get(position) instanceof TableFeeMasterDataModel) {
            AppLog.log(TAG, "TableFeeMasterDataModel " + position);
            TableStudentOverallFeeSummary feeTable = new TableStudentOverallFeeSummary();
            feeTable.openDB(mContext);
            TableFeeMasterDataModel feeHolder = feeTable.getData(((TableFeeMasterDataModel) mList.get(position)).getMenuCode(), "" + ((TableFeeMasterDataModel) mList.get(position)).getReferenceId());
            //TableFeeMasterDataModel feeHolder = feeTable.getData(mList.get(position).getMenuCode(), mList.get(position).getRederenceId());
            feeTable.closeDB();
            if (feeHolder.getStatus().equalsIgnoreCase(WSContant.TAG_VALUE_NOT_PAID)) {
                holder.noticeboard_row_fee_holder.setVisibility(View.VISIBLE);
                holder.noticeboard_row_activity_fee_row_holder.setVisibility(View.GONE);
                holder.mTextViewRPValue.setText(feeHolder.getTotalDue());
                holder.mTextViewDueDate.setText(Utils.getTimeInYYYYMMDD(feeHolder.getDueDate()));
                holder.mButtonViewDetails.setOnClickListener(mListener);
                holder.mButtonViewDetails.setTag(position);
                holder.mButtonPayNow.setOnClickListener(mListener);
                holder.mButtonPayNow.setTag(position);
                holder.noticeboard_row_fee_holder.setOnClickListener(mListener);
                holder.noticeboard_row_fee_holder.setTag(position);
            } else {
                holder.noticeboard_row_activity_fee_row_holder.setVisibility(View.VISIBLE);
                holder.noticeboard_row_fee_holder.setVisibility(View.GONE);
                holder.textViewDate.setText(Utils.getTimeInYYYYMMDD(feeHolder.getDueDate()));
                holder.textViewPaymentValue.setText(feeHolder.getTotalDue());
                holder.btnViewDetails.setOnClickListener(mListener);
                holder.btnViewDetails.setTag(position);
                holder.noticeboard_row_activity_fee_row_holder.setOnClickListener(mListener);
                holder.noticeboard_row_activity_fee_row_holder.setTag(position);
            }
        } else if (mList.get(position) instanceof TableResultMasterDataModel) {
            TableStudentOverallResultSummary result = new TableStudentOverallResultSummary();
            result.openDB(mContext);
            TableResultMasterDataModel resultHolder = result.getData(((TableResultMasterDataModel) mList.get(position)).getMenuCode(), "" + ((TableResultMasterDataModel) mList.get(position)).getReferenceId());
            result.closeDB();

            holder.noticeboard_row_result_row_holder.setVisibility(View.VISIBLE);
            holder.textview_result_subject.setText(resultHolder.getStudentName());
            holder.textview_result_grade.setText(resultHolder.getAchievementIndex());
            holder.textview_result_value.setText(resultHolder.getResult());
            holder.noticeboard_row_result_row_holder.setTag(position);
        }

       /* switch (mList.get(position).getMenuCode()) {

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
            case Constant.TAG_TIMETABLE:
                holder.textViewPublishBy.setVisibility(View.GONE);
                holder.textViewRefTitle.setVisibility(View.VISIBLE);
                holder.textViewPublishedTime.setVisibility(View.VISIBLE);
                holder.textViewPublishedTime.setText(Utils.getTimeInDDMMYYYY(mList.get(position).getPublishedOn()));
                holder.textViewRefTitle.setText("Time table has been changes for reference data " + mList.get(position).getReferenceDate());
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
        }*/
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public final LinearLayout noticeboard_row_news_holder;
        public final LinearLayout noticeboard_row_fee_holder;
        public final LinearLayout noticeboard_row_activity_fee_row_holder;
        private final TextView mTextViewRPValue;
        private final TextView mTextViewDueDate;
        private final Button mButtonViewDetails;
        private final TextView mButtonPayNow;
        public final TextView textViewRefTitle;
        public final TextView textViewPublishBy;
        public final TextView textViewPublishedTime;
        public final ImageView imageViewRhumbnil;
        public final WebView webviewShortBody;
        public final ImageView imageViewTag;
        public final ImageView imageViewLike;
        public final ImageView imageViewComment;
        public final TextView textViewTag;
        public final TextView textViewLike;
        public final TextView textViewComment;
        private final TextView textViewDate;
        private final TextView textViewPaymentValue;
        private final Button btnViewDetails;
        private final LinearLayout noticeboard_row_result_row_holder;
        private final TextView textview_result_subject;
        private final TextView textview_result_grade;
        private final TextView textview_result_value;

        public MyViewHolder(View itemView) {
            super(itemView);
            //For News-----------------------------------------------------------------------
            noticeboard_row_news_holder = (LinearLayout) itemView.findViewById(R.id.lin_noticeboard_news_row_holder);
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
            //For Fee----------------------------------------------------------------------
            noticeboard_row_fee_holder = (LinearLayout) itemView.findViewById(R.id.lin_noticeboard_row_fee_holder);
            mTextViewRPValue = (TextView) itemView.findViewById(R.id.textview_fee_rp_value);
            mTextViewDueDate = (TextView) itemView.findViewById(R.id.textview_duedate_value);
            mButtonViewDetails = (Button) itemView.findViewById(R.id.btn_download_details);
            mButtonPayNow = (TextView) itemView.findViewById(R.id.btn_view_pay_now);
            //For Fee Details--------------------------------------------------------------
            noticeboard_row_activity_fee_row_holder = (LinearLayout) itemView.findViewById(R.id.lin_noticeboard_row_activity_fee_row_holder);
            textViewDate = (TextView) itemView.findViewById(R.id.textview_fee_row_date_vaule);
            textViewPaymentValue = (TextView) itemView.findViewById(R.id.textview_fee_row_part_payment_value);
            btnViewDetails = (Button) itemView.findViewById(R.id.btn_download_details);
            //For Result-------------------------------------------------------------------
            noticeboard_row_result_row_holder = (LinearLayout) itemView.findViewById(R.id.lin_noticeboard_row_result_row_holder);
            textview_result_subject = (TextView) itemView.findViewById(R.id.textview_result_details_credits);
            textview_result_grade = (TextView) itemView.findViewById(R.id.textview_result_row_grade_value);
            textview_result_value = (TextView) itemView.findViewById(R.id.textview_result_row_result_value);

        }
    }
}

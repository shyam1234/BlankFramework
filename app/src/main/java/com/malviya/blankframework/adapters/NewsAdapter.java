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
import com.malviya.blankframework.models.TableNewsMasterDataModel;
import com.malviya.blankframework.utils.GetPicassoImage;

import java.util.ArrayList;

/**
 * Created by Admin on 05-02-2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<TableNewsMasterDataModel> mList;
    private View.OnClickListener mListener;

    public NewsAdapter(Context context, ArrayList<TableNewsMasterDataModel> pList, View.OnClickListener pListener) {
        mContext = context;
        mList = pList;
        mListener = pListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View viewHolder = inflater.inflate(R.layout.fragment_news_row, parent, false);
        return new MyViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GetPicassoImage.getImage(mContext,mList.get(position).getThumbNailPath(),holder.imageViewRhumbnil);
        holder.textViewPublishBy.setText(mList.get(position).getPublishedBy());
        holder.textViewRefTitle.setText(mList.get(position).getReferenceTitle());
        holder.textViewPublishedTime.setText(mList.get(position).getPublishedOn());
        holder.webviewShortBody.loadData(mList.get(position).getShortBody(), "text/html", "UTF-8");
        holder.textViewTag.setText(mList.get(position).getMenuCode());
        holder.textViewLike.setText(mList.get(position).getTotalLikes());
        holder.textViewComment.setText(mList.get(position).getTotalComments());
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

            imageViewTag = (ImageView) itemView.findViewById(R.id.imageview_bottom_tag);
            imageViewLike = (ImageView) itemView.findViewById(R.id.imageview_bottom_like);
            imageViewComment = (ImageView) itemView.findViewById(R.id.imageview_bottom_comment);
            textViewTag = (TextView) itemView.findViewById(R.id.textview_inc_bottom_tag);
            textViewLike = (TextView) itemView.findViewById(R.id.textview_inc_bottom_like);
            textViewComment = (TextView) itemView.findViewById(R.id.textview_inc_bottom_comment);
        }
    }
}

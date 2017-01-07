package com.malviya.blankframework.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.models.WardChildDataHolder;

import java.util.ArrayList;

/**
 * Created by Admin on 07-01-2017.
 */

public class WardChildRowAdapter extends RecyclerView.Adapter<WardChildRowAdapter.MyViewHolder> {

    private final Context mContext;
    private ArrayList<WardChildDataHolder> mListHolder;


    public WardChildRowAdapter(Context context, ArrayList<WardChildDataHolder> pListChildInfoHolder) {
        mContext = context;
        mListHolder = pListChildInfoHolder;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.ward_row, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;//mListHolder.size();
    }


    //create own view holder to club all the UI requirement
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewChildIcon;
        private TextView mTextViewName;
        private TextView mTextViewAddress;

        public MyViewHolder(View v) {
            super(v);
            mImageViewChildIcon = (ImageView) v.findViewById(R.id.imageview_ward_row_icon);
            mTextViewName = (TextView) v.findViewById(R.id.textview_ward_row_name);
            mTextViewAddress = (TextView) v.findViewById(R.id.textview_ward_row_address);
        }
    }

}

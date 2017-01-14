package com.malviya.blankframework.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.malviya.blankframework.R;
import com.malviya.blankframework.models.DashboardCellDataHolder;

import java.util.ArrayList;

/**
 * Created by Admin on 11-12-2016.
 */

public class DashboardCellAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<DashboardCellDataHolder> mCellList;

    public DashboardCellAdapter(Context pContext, ArrayList<DashboardCellDataHolder> pCellList) {
        mContext = pContext;
        mCellList = pCellList;
    }

    @Override
    public int getCount() {
        return mCellList.size();
    }

    @Override
    public DashboardCellDataHolder getItem(int i) {
        return mCellList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (view == null) {
            view = inflater.inflate(R.layout.dashboard_cell, viewGroup, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }
        holder.setImage(mCellList.get(position).getImage());
        holder.setTextView(mCellList.get(position).getText());
        holder.setNotificationCounter(mCellList.get(position).getNotification());
        //holder.setColor(mCellList.get(position).getColor());
        return holder.getView();
    }


    private class ViewHolder {
        private View view;
        private ImageView image;
        private TextView textView;
        private TextView notificationCounter;
        private RelativeLayout cellLayout;

        public ViewHolder(View view) {
            this.view = view;
            image = (ImageView) view.findViewById(R.id.imageview_dashboard_holder);
            textView = (TextView) view.findViewById(R.id.textview_dashboard_cell_name);
            notificationCounter = (TextView) view.findViewById(R.id.textview_dashboard_notifcation_count);
            cellLayout = (RelativeLayout) view.findViewById(R.id.rel_cell_holder);


        }

        public View getView() {
            return view;
        }

        public ImageView getImage() {
            return image;
        }

        public void setImage(int id) {
            image.setImageResource(id);
        }

        public void setColor(int color) {
            cellLayout.setBackgroundResource(color);
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(String str) {
            this.textView.setText(str);
        }

        public TextView getNotificationCounter() {
            return notificationCounter;
        }

        public void setNotificationCounter(String number) {
            this.notificationCounter.setText(number);
        }

        public RelativeLayout getCellLayout() {
            return cellLayout;
        }

        public void setCellLayout(int color) {
            this.cellLayout.setBackgroundColor(color);
        }
    }
}
